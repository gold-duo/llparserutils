package com.droidwolf.LLParserUtils;

/** LL(K)解析器 */
public abstract class AbsLLkParser {
	protected final CircularArray mLookAhead;

	/**
	 * 构造函数(在调用该构造函数后须调用fillToken初始填充词法)
	 * 
	 * @param k
	 *            向前看k个词法
	 */
	protected AbsLLkParser(int k) {
		mLookAhead = new CircularArray(k);
	}

	/**
	 * 向前推进k个词法
	 * 
	 * @param k
	 */
	protected void fillToken(int k) {
		for (int i = 0; i < k; i++) {
			consume();
		}
	}

	protected void consume() {
		mLookAhead.add(nextToken());
	}

	protected TokenBase LT(int i) {
		return mLookAhead.get(i);
	}

	protected short LA(int i) {
		return LT(i).getType();
	}

	protected void match(short type) throws MatchFailedException {
		if (LA(1) != type) {
			final TokenBase tok = LT(1);
			final String desc = String.format("%s mismatch %s。%s",getTokenName(type), getTokenName(tok.getType()),tok.toString());
			throw new MatchFailedException(desc);
		}
		consume();
	}

	public abstract TokenBase nextToken();

	public abstract String getTokenName(short type);

	private static class CircularArray {
		private final TokenBase[] mData;
		private int mPointer = 0;

		public CircularArray(int size) {
			mData = new TokenBase[size];
		}

		/** 当数组中元素个数>size,则pointer 回到数组头 */
		public void add(TokenBase val) {
			mData[mPointer] = val;
			mPointer = (mPointer + 1) % mData.length;
		}

		/** 获取第i个元素，i>=1 */
		public TokenBase get(int i) {
			final int realP = (mPointer + i - 1) % mData.length;
			final TokenBase val = mData[realP];
			return val;
		}

		/** 获取当前指针 */
		public int getPointer() {
			return mPointer;
		}
	}// end class CircularArray
}// end class AbsLLkParser
