package com.droidwolf.LLParserUtils;

/** 无测试分支可匹配 alternative */
public class NoneAltMatchException extends MatchException {
	private static final long serialVersionUID = 3240048217640870116L;
	public NoneAltMatchException() {
	}
	public NoneAltMatchException(String msg) {
		super(msg);
	}
}// end class NoneAlternativeException
