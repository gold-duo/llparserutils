package com.droidwolf.LLParserUtils;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 改良回溯解析器
 */
public abstract class AbsPackratParser {
	protected final static boolean DBG = true;
	protected final static int PARESED_FAILED = -1;

	/** 向前看词法 */
	protected final List<TokenBase> mLookAhead = new ArrayList<TokenBase>();

	/** 回溯词法指针栈 */
	protected final ArrayDeque<Integer> mBacktraceToken = new ArrayDeque<Integer>();

	/** 开始词法指针，结束词法指针（PARESED_FAILED解析失败） */
	protected final Map<Integer, Integer> mParsedRules = new HashMap<Integer, Integer>();
	protected int mPointer = 0;

	/** 推进下一词法 */
	protected void consume() {
		++mPointer;
		// 不是推演且，词法指针到了结尾
		if (isPointerAfterLast() && !isTestAltMatch()) {
			clearLookAheadToken();
			clearParsedRules();
		}
		ensureToken(1);
	}

	/** 确保读取入i个词法 */
	protected void ensureToken(int i) {
		final int p = mPointer + i /*- 1*/;
		final int tkSize = mLookAhead.size() /*- 1*/;
		if (p > tkSize) {
			final int n = p - tkSize;
			for (int j = 0; j < n; j++) {
				mLookAhead.add(nextToken());
			}
		}
	}

	protected TokenBase LT(int i) {
		ensureToken(i);
		return mLookAhead.get(mPointer + i - 1);
	}

	/** 返回词法 type */
	protected short LA(int i) {
		return LT(i).getType();
	}
	public abstract TokenBase nextToken();

	public abstract String getTokenName(short type);
	protected void match(short type) throws MatchFailedException {
		if (LA(1) != type) {
			final TokenBase tok = LT(1);
			final String desc = String.format("%s mismatch %s。%s",getTokenName(type), getTokenName(tok.getType()),tok.toString());
			throw new MatchFailedException(desc);
		}
		consume();
	}

	/**
	 * 跳过已经解析项
	 * 
	 * @return true 跳过，false当前词法为解析过需解析
	 * @throws PrevAltMatchFailedException
	 */
	protected boolean skipParsedSuccessRule()throws PrevAltMatchFailedException {
		final int ctk = getCurrentTokenPointer();
		Integer ret = mParsedRules.get(ctk);
		if (ret == null)
			return false;
		if (ret.intValue() == PARESED_FAILED) {
			throw new PrevAltMatchFailedException();
		}
		if (DBG) {
			final String toTkp = getTokenString(ret), fromTkp = getTokenString(ctk);
			log("skipParsedSuccessRule", String.format("skip token[%d->%d],\"%s\"->\"%s\"", ctk, ret, fromTkp,toTkp));
		}
		setCurrentTokenPointer(ret);
		return true;
	}

	/***
	 * 保存已经解析词法规则结果
	 * 
	 * @param tkPointer
	 *            词法指针
	 * @param success
	 *            匹配是否成功
	 */
	protected void saveParsedRule(int tkPointer, boolean success) {
		if (isTestAltMatch()) {
			mParsedRules.put(tkPointer, success? getCurrentTokenPointer(): PARESED_FAILED);
			if (DBG) {
				final String tk = getTokenString(tkPointer);
				log("saveParsedRule",String.format("start stk=%d, end stk=%d, success=%b,mParsedRules size=%d, %s",tkPointer, getCurrentTokenPointer(), success,mParsedRules.size(), tk));
			}
		}
	}

	/** 清除已解析规则 */
	protected void clearParsedRules() {
		if (DBG) {
			log("clearParsedRules", "size=" + mParsedRules.size());
		}
		mParsedRules.clear();
	}

	/** 获取当前词法指针 */
	protected int getCurrentTokenPointer() {
		return mPointer;
	}

	/** 设置当前词法指针 */
	protected void setCurrentTokenPointer(int tkp) {
		mPointer = tkp;
	}

	/** 词法指针是否移到到了尾巴 */
	protected boolean isPointerAfterLast() {
		return mPointer == mLookAhead.size();
	}

	/** 是否在尝试分支规则测试中 */
	protected boolean isTestAltMatch() {
		final boolean ret = !mBacktraceToken.isEmpty();
		return ret;
	}

	/** 当前词法指针入栈 */
	protected int pushTokenPointer() {
		final int tkp = getCurrentTokenPointer();
		mBacktraceToken.push(tkp);
		if (DBG) {
			final String tk = getTokenString(tkp);
			log("pushTokenPointer", "" + tkp + ", stack size="+ mBacktraceToken.size() + ", " + tk);
		}
		return tkp;
	}

	/** 弹出栈顶词法指针，并设置为当前词法指针 */
	protected void popTokenPointer() {
		final int tkp = mBacktraceToken.pop();
		if (DBG) {
			final String tk = getTokenString(tkp);
			log("popTokenPointer", tkp + ", stack size=" + mBacktraceToken.size() + ", "+ tk);
		}
		setCurrentTokenPointer(tkp);
	}

	/** 清除向前看词法 */
	protected void clearLookAheadToken() {
		mPointer = 0;
		mLookAhead.clear();
	}

	private String getTokenString(int tkp) {
		return tkp > 0 ? LT(tkp).toString() : "token index=" + tkp;
	}

	protected static void log(String tag, String msg) {
		System.out.println(tag + "--" + msg);
	}
}// end class
