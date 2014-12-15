package com.droidwolf.LLParserUtils;

/** 前一分支匹配失败 */
public class PrevAltMatchFailedException extends MatchException {
	private static final long serialVersionUID = -5013732201049947860L;
	public PrevAltMatchFailedException() {
	}
	public PrevAltMatchFailedException(String msg) {
		super(msg);
	}
}// end class PrevAlternativeMatchFailedException