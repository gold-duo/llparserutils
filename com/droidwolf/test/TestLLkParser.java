package com.droidwolf.test;
import com.droidwolf.LLParserUtils.AbsLLkParser;
import com.droidwolf.LLParserUtils.MatchFailedException;
import com.droidwolf.LLParserUtils.TokenBase;

public class TestLLkParser extends AbsLLkParser {
	final LookaheadLexer mLexer;
	public TestLLkParser(LookaheadLexer lexer, int k) {
		super(k);
		mLexer = lexer;
		fillToken(k);
	}

	@Override
	public TokenBase nextToken() {
		return mLexer.nextToken();
	}

	@Override
	public String getTokenName(short type) {
		return mLexer.getTokenName(type);
	}

	/**
	 * list : '[' elements ']' ; // match bracketed list
	 * 
	 * @throws MatchFailedException
	 */
	public void list() throws MatchFailedException {
		match(LookaheadLexer.LBRACK);
		elements();
		match(LookaheadLexer.RBRACK);
	}

	/**
	 * elements : element (',' element)* ; // match comma-separated list
	 * 
	 * @throws MatchFailedException
	 */
	void elements() throws MatchFailedException {
		element();
		while (LA(1) == LookaheadLexer.COMMA) {
			match(LookaheadLexer.COMMA);
			element();
		}
	}

	/**
	 * element : NAME '=' NAME | NAME | list ; assignment, NAME or list
	 * 
	 * @throws MatchFailedException
	 */
	void element() throws MatchFailedException {
		if (LA(1) == LookaheadLexer.NAME && LA(2) == LookaheadLexer.EQUALS) {
			match(LookaheadLexer.NAME);
			match(LookaheadLexer.EQUALS);
			match(LookaheadLexer.NAME);
		} else if (LA(1) == LookaheadLexer.NAME)
			match(LookaheadLexer.NAME);
		else if (LA(1) == LookaheadLexer.LBRACK)
			list();
		else
			throw new Error("expecting name or list; found " + LT(1));
	}

}
