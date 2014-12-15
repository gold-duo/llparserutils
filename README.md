#简介
主要是自己在学习《编程语言实现模式》对语法解析一块的总结，在Tenrence Parr的示例代码基础上做了一些修改方便方便复用。包含四个parser基类：

- LL(1)：[AbsLL1Parser](https://github.com/droidwolf/llparserutils/blob/master/com/droidwolf/LLParserUtils/AbsLL1Parser.java "AbsLL1Parser") 。
- LL(K):[AbsLLkParser](https://github.com/droidwolf/llparserutils/blob/master/com/droidwolf/LLParserUtils/AbsLLkParser.java "AbsLLkParser")。[示例](https://github.com/droidwolf/llparserutils/blob/master/com/droidwolf/test/TestLLkParser.java)
- [AbsPackratParser](https://github.com/droidwolf/llparserutils/blob/master/com/droidwolf/LLParserUtils/AbsPackratParser.java)：LL(*)回溯优化解析器。[示例](https://github.com/droidwolf/llparserutils/blob/master/com/droidwolf/test/TestPackratParser.java)
- [AbsPackratParserEx](https://github.com/droidwolf/llparserutils/blob/master/com/droidwolf/LLParserUtils/AbsPackratParserEx.java)：继承自AbsPackratParser的简化子类。将分支测试和结果记录尝试用类来封装，简化AbsPackratParser的测试、记录、匹配流程。[示例](https://github.com/droidwolf/llparserutils/blob/master/com/droidwolf/LLParserUtils/AbsPackratParserEx.java)

btw:由于Tenrence Parr有版权声明，以上代码仅供学习使用。

----------

#introduction
Mainly their own learning "programming language model" for parsing a summary, in Tenrence Parr sample code to do some modifications on the basis of convenience to facilitate reuse. Parser base contains four categories:

- LL(1)：[AbsLL1Parser](https://github.com/droidwolf/llparserutils/blob/master/com/droidwolf/LLParserUtils/AbsLL1Parser.java "AbsLL1Parser") 。
- LL(K):[AbsLLkParser](https://github.com/droidwolf/llparserutils/blob/master/com/droidwolf/LLParserUtils/AbsLLkParser.java "AbsLLkParser")。[example](https://github.com/droidwolf/llparserutils/blob/master/com/droidwolf/test/TestLLkParser.java)
- [AbsPackratParser](https://github.com/droidwolf/llparserutils/blob/master/com/droidwolf/LLParserUtils/AbsPackratParser.java)：LL(*)Backtracking parser optimization。[example](https://github.com/droidwolf/llparserutils/blob/master/com/droidwolf/test/TestPackratParser.java)
- [AbsPackratParserEx](https://github.com/droidwolf/llparserutils/blob/master/com/droidwolf/LLParserUtils/AbsPackratParserEx.java)：Simplify subclass inherits the self AbsPackratParser. The branch of the test results are recorded and try to encapsulate classes simplify AbsPackratParser test, record, matching processes.[example](https://github.com/droidwolf/llparserutils/blob/master/com/droidwolf/LLParserUtils/AbsPackratParserEx.java)

btw: Because Tenrence Parr copyright statement, only learning to use the above code.