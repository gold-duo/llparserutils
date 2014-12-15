package com.droidwolf.test;
import com.droidwolf.LLParserUtils.MatchException;
import com.droidwolf.LLParserUtils.MatchFailedException;


/***
 * Excerpted from "Language Implementation Patterns",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book information.
***/
public class Test {
    public static void main(String[] args)  {
        BacktrackLexer lexer = new BacktrackLexer("[a,b]=[c,d]"/*args[0]*/); // parse arg
//        MyPackratParser parser = new MyPackratParser(lexer);
        TestPackratParserEx parser = new TestPackratParserEx(lexer);
        
        try {
	    parser.stat();
	} catch (MatchException e) {
	    System.out.println(e.getMessage());
	} 
    }
}

//public class Test {
//    public static void main(String[] args)  {
//        LookaheadLexer lexer = new LookaheadLexer("[Hello=]" /*args[0]*/); // parse arg
//        MyLLkParser parser = new MyLLkParser(lexer,2);//LookaheadParser(lexer, 2);
//        try{
//            parser.list(); // begin parsing at rule list
//        }catch(MatchException e){
//            System.out.println(e.getMessage());
//        }
//    }
//}
