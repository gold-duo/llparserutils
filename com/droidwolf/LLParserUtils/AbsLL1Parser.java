package com.droidwolf.LLParserUtils;

public abstract class AbsLL1Parser {
	protected TokenBase mToken;

	protected void consume() {
		mToken = nextToken();
	}

	protected TokenBase LT() {
		return mToken;
	}

	protected short LA() {
		return LT().getType();
	}

	protected void match(short type) throws MatchFailedException {
		if (LA() != type) {
			final TokenBase tok = LT();
			final String desc = String.format("%s mismatch %s¡£%s",getTokenName(type), getTokenName(tok.getType()),tok.toString());
			throw new MatchFailedException(desc);
		}
		consume();
	}

	public abstract TokenBase nextToken();

	public abstract String getTokenName(short type);
}
