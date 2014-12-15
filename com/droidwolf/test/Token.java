package com.droidwolf.test;
import com.droidwolf.LLParserUtils.TokenBase;

/***
 * Excerpted from "Language Implementation Patterns", published by The Pragmatic
 * Bookshelf. Copyrights apply to this code. It may not be used to create
 * training material, courses, books, articles, and the like. Contact us if you
 * are in doubt. We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book
 * information.
 ***/
public class Token extends TokenBase {

	public Token(int type, String text) {
		super(text, (short) type);
	}

	public String toString() {
		String tname = BacktrackLexer.tokenNames[mType];
		return mText;// "<'"+text+"',"+tname+">";
	}
}
