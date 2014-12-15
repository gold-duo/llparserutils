package com.droidwolf.LLParserUtils;

/** AbsPackratParser简化基类。简化分支测试、分支成功匹配记录 */
public abstract class AbsPackratParserEx extends AbsPackratParser {
	/**
	 * 分组尝试测试规则相似的分支，如果没一个分支匹配则抛出NoneAltMatchException
	 * 
	 * @param alts
	 * @throws MatchException
	 */
	protected final void groupAltMatch(MatchAlternative[] alts)
			throws MatchException {
		for (MatchAlternative ma : alts) {
			if (callTestMatch(ma)) {
				ma.normalMatch();
				return;
			}
		}
		final TokenBase tok = LT(1);
		final String desc = "unknow alternative,start with token type "+ getTokenName(tok.getType()) + "。" + tok.toString();
		throw new NoneAltMatchException(desc);
	}

	/**
	 * testMatch回溯匹配(测试在匹配)一个规则
	 * 
	 * @param matchAlternative
	 * @return
	 * @throws MatchException
	 */
	protected final boolean callTestMatch(MatchAlternative matchAlternative)throws MatchException {
		boolean success = true;
		pushTokenPointer();
		try {
			matchAlternative.testMatch();
		} catch (MatchException e) {
			success = false;
		}
		popTokenPointer();
		return success;
	}

	/**
	 * 记录测试分支中重复尝试规则的匹配结果
	 * 
	 * @param matchAlternative
	 * @throws MatchException
	 */
	protected final void callMatchRecord(MatchAndRecord matchAndRecord)throws MatchException {
		if (isTestAltMatch() && skipParsedSuccessRule()) {
			return;
		}
		boolean success = true;
		final int startTkp = getCurrentTokenPointer();
		try {
			matchAndRecord.matchRecord();
		} catch (MatchFailedException re) {
			success = false;
			throw re;
		}
		if (isTestAltMatch()) {
			saveParsedRule(startTkp, success);
		}
	}

	/** 分支匹配 */
	public interface MatchAlternative {
		/**
		 * 测试分支时(自动保存、恢复堆栈)调用该方法。勿直接调用，用callTestMatch代理调用该方法
		 * 
		 * @return 实现返回无意义
		 * @throws MatchException
		 */
		boolean testMatch() throws MatchException;

		/** 正常匹配时调用 */
		void normalMatch() throws MatchException;
	}

	/** 匹配并记录匹配结果 */
	public interface MatchAndRecord {
		/** 匹配并记录匹配结果。勿直接调用，用callMatchRecord代理调用该方法 */
		void matchRecord() throws MatchException;
	}
}// end class AbsPackratParserEx
