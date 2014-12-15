package com.droidwolf.test;
import com.droidwolf.LLParserUtils.AbsPackratParser;
import com.droidwolf.LLParserUtils.MatchException;
import com.droidwolf.LLParserUtils.NoneAltMatchException;

public class TestPackratParser extends AbsPackratParser {
	final Lexer mLexer;
	public TestPackratParser(Lexer input) {
		mLexer = input;
	}

	public void stat() throws MatchException {
		if (testAltListEOF()) {
			matchListEOF();
			// 这里可能需要收集信息
		} else if (testAltAssign()) {// 实际只在这里重用了testAltListEOF解析的matchListRecord
			matchAssign();
			// 这里可能需要收集信息
		} else {
			final Token tok = (Token) LT(1);
			final String desc = "unknow alternative,start with token type "
					+ getTokenName(tok.getType()) + "。" + tok.toString();
			throw new NoneAltMatchException(desc);
		}
	}
	private void matchListEOF() throws MatchException {
		matchListRecord();
		match(Lexer.EOF_TYPE);
	}

	// list EOF
	private boolean testAltListEOF() {
		// 测试分支不收集词法信息，所以仅仅用栈来保存回退
		boolean success = true;
		if (DBG) {
			log("testAltListEOF", "--start");
		}
		pushTokenPointer();
		try {
			matchListEOF();
		} catch (MatchException e) {
			success = false;
		}
		popTokenPointer();
		if (DBG) {
			log("testAltListEOF", "--end\n");
		}
		return success;
	}

	private void matchAssign() throws MatchException {
		matchListRecord();
		match(BacktrackLexer.EQUALS);
		matchListRecord();
		match(Lexer.EOF_TYPE);
	}
	// list '=' list EOF
	private boolean testAltAssign() {
		boolean success = true;
		if (DBG) {
			log("testAltAssign", "--start");
		}
		pushTokenPointer();
		try {
			matchAssign();
		} catch (MatchException e) {
			success = false;
		}
		popTokenPointer();
		if (DBG) {
			log("testAltAssign", "--end\n");
		}
		return success;
	}

	private void matchListRecord() throws MatchException {
		if (isTestAltMatch() && skipParsedSuccessRule()) {
			return;
		}
		boolean success = true;
		final int startTkp = getCurrentTokenPointer();
		try {
			match(BacktrackLexer.LBRACK);
			elements();
			match(BacktrackLexer.RBRACK);
		} catch (MatchException re) {
			success = false;
			throw re;
		}
		if (isTestAltMatch()) {
			saveParsedRule(startTkp, success);
		}
	}

	/** elements : element (',' element)* ; // match comma-separated list */
	void elements() throws MatchException {
		element();
		while (LA(1) == BacktrackLexer.COMMA) {
			match(BacktrackLexer.COMMA);
			element();
		}
	}

	/** element : name '=' NAME | NAME | list ; // assignment, name or list */
	void element() throws MatchException {
		if (LA(1) == BacktrackLexer.NAME && LA(2) == BacktrackLexer.EQUALS) {
			match(BacktrackLexer.NAME);
			match(BacktrackLexer.EQUALS);
			match(BacktrackLexer.NAME);
		} else if (LA(1) == BacktrackLexer.NAME) {
			match(BacktrackLexer.NAME);
		} else if (LA(1) == BacktrackLexer.LBRACK) {
			matchListRecord();
		} else {
			final Token tok = (Token) LT(1);
			final String desc = "unknow token type "
					+ getTokenName(tok.getType()) + "。" + tok.toString();
			throw new NoneAltMatchException(desc);
		}
	}

	@Override
	public Token nextToken() {
		return mLexer.nextToken();
	}

	@Override
	public String getTokenName(short type) {
		return mLexer.getTokenName(type);
	}

}
