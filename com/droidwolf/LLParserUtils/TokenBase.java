package com.droidwolf.LLParserUtils;
public class TokenBase {
	final protected String mText;
	final protected short mType;

	public TokenBase(String text, short type) {
		this.mText = text;
		this.mType = type;
	}

	public String getText() {
		return mText;
	}

	public short getType() {
		return mType;
	}

	@Override
	public String toString() {
		return mText;
	}
}// end class TokenBase