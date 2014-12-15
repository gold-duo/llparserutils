package com.droidwolf.test;

import com.droidwolf.LLParserUtils.AbsPackratParserEx;
import com.droidwolf.LLParserUtils.MatchException;
import com.droidwolf.LLParserUtils.NoneAltMatchException;

public class TestPackratParserEx extends AbsPackratParserEx {
	final Lexer mLexer;
	final MatchAndRecord mMatchAndRecordList;
	final MatchAlternative mMatchAltListEOF, mMatchAltAssign;
	public TestPackratParserEx(Lexer input) {
		mLexer = input;
		mMatchAndRecordList = new ListMatchAndRecord();
		mMatchAltListEOF = new ListEOFMatchAlternative();
		mMatchAltAssign = new AssignMatchAlternative();
	}

	public void stat() throws MatchException {
		MatchAlternative[] ma = {mMatchAltListEOF, mMatchAltAssign};
		groupAltMatch(ma);
	}

	/** elements : element (',' element)* ; // match comma-separated list */
	private void elements() throws MatchException {
		element();
		while (LA(1) == BacktrackLexer.COMMA) {
			match(BacktrackLexer.COMMA);
			element();
		}
	}

	/** element : name '=' NAME | NAME | list ; // assignment, name or list */
	private void element() throws MatchException {
		if (LA(1) == BacktrackLexer.NAME && LA(2) == BacktrackLexer.EQUALS) {
			match(BacktrackLexer.NAME);
			match(BacktrackLexer.EQUALS);
			match(BacktrackLexer.NAME);
		} else if (LA(1) == BacktrackLexer.NAME) {
			match(BacktrackLexer.NAME);
		} else if (LA(1) == BacktrackLexer.LBRACK) {
			listMatchRecord();
		} else {
			final Token tok = (Token) LT(1);
			final String desc = "unknow token type "
					+ getTokenName(tok.getType()) + "。" + tok.toString();
			throw new NoneAltMatchException(desc);
		}
	}

	private void listMatchRecord() throws MatchException {
		super.callMatchRecord(mMatchAndRecordList);
	}

	@Override
	public Token nextToken() {
		return mLexer.nextToken();
	}

	@Override
	public String getTokenName(short type) {
		return mLexer.getTokenName(type);
	}

	private class ListEOFMatchAlternative implements MatchAlternative {
		@Override
		public boolean testMatch() throws MatchException {
			normalMatch();// 直接用match尝试匹配
			return false;
		}

		@Override
		public void normalMatch() throws MatchException {
			listMatchRecord();
			TestPackratParserEx.this.match(Lexer.EOF_TYPE);
		}
	};

	private class AssignMatchAlternative extends ListEOFMatchAlternative {
		@Override
		public boolean testMatch() throws MatchException {
			normalMatch();// 直接用match尝试匹配
			return false;
		}
		@Override
		public void normalMatch() throws MatchException {
			listMatchRecord();
			TestPackratParserEx.this.match(BacktrackLexer.EQUALS);
			listMatchRecord();
			TestPackratParserEx.this.match(Lexer.EOF_TYPE);
		}
	};

	private class ListMatchAndRecord implements MatchAndRecord {
		@Override
		public void matchRecord() throws MatchException {
			TestPackratParserEx.this.match(BacktrackLexer.LBRACK);
			TestPackratParserEx.this.elements();
			TestPackratParserEx.this.match(BacktrackLexer.RBRACK);
		}
	};
}
