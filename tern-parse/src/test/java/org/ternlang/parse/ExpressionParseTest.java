package org.ternlang.parse;

import junit.framework.TestCase;

public class ExpressionParseTest extends TestCase {
   
   private static final String GRAMMAR_FILE = "grammar.txt";

   public void testParse() throws Exception {
      SyntaxParser tree = LexerBuilder.create(GRAMMAR_FILE);

      assertNotNull(tree);
      analyze(tree, "async (a) -> a.run()", "reference");
      analyze(tree, "async (a) -> a.run()", "assignment-expression");
      analyze(tree, "async (a) -> a.run()", "closure");
      analyze(tree, "let f: String", "await-declaration");
      analyze(tree, "let f = async (a) -> a.run();", "statement");
      analyze(tree, "async (a) -> a.run();", "expression-statement");
      analyze(tree, "0 to 10", "list-entry-data");
      analyze(tree, "0 from 10", "list-entry-data");
      analyze(tree, "0 to 10", "list-entry-range");
      analyze(tree, "0 to 10", "set-entry-range");
      analyze(tree, "0 .. 10", "range");
      analyze(tree, "0 to 10", "range");
      analyze(tree, "0 from 10", "range");
      analyze(tree, "let x: (a) = (a) -> a.run();", "declaration-statement");
      analyze(tree, "let x = <T: Runnable>(a: T) -> a.run();", "declaration-statement");
      analyze(tree, "Foo", "alias-name");
      analyze(tree, "type Foo = Map<String, String>;", "alias-definition");
      analyze(tree, "import org.cef.{CefApp, CefBrowser};", "import-list");
      analyze(tree, "import lang.{Double, Integer, Short};", "import-list");
      analyze(tree, "List<String>[]", "array-reference"); 
      analyze(tree, "(a, b, c)", "assignment-list");  
      analyze(tree, "(a.x, b, c)", "assignment-list");   
      analyze(tree, "(a.x, b, c[1])", "assignment-list");  
      analyze(tree, "(a.x, b, c[1]) = (1, 2, 3);", "assignment-list-statement"); 
      analyze(tree, "(a.x, b, c[1]) = (1, 2, 3);", "statement"); 
      analyze(tree, "(a.x, b, c[1]) = (1, 2, 3);", "script-statement"); 
      analyze(tree, "(a.x, b, c[1]) = (1, null, 3);", "script-statement"); 
      analyze(tree, ">=", "comparison-operator");       
      analyze(tree, "x>=y", "expression");           
      analyze(tree, ">", "comparison-operator"); 
      analyze(tree, "i", "value-operand");
      analyze(tree, "i", "comparison-operand");
      analyze(tree, "i>2", "comparison");
      analyze(tree, "List<List<String>>", "constraint");
      analyze(tree, "List<String>", "constraint");
      analyze(tree, "List<?>", "constraint");
      analyze(tree, "class X<T> extends Y{}", "class-definition");
      analyze(tree, "class X extends Y{}", "class-definition");
      analyze(tree, "class X<T> extends Y<T>{}", "class-definition");
      analyze(tree, "x: Foo<X> = null", "declaration");
      analyze(tree, "Foo<X>", "constraint");
      analyze(tree, "var x = 1;", "declaration-statement");
      analyze(tree, "var x: Foo<X> = null;", "declaration-statement");
      analyze(tree, "yield;", "yield-statement");
      analyze(tree, "yield x;", "yield-statement");
      analyze(tree, "yield x as String;", "yield-statement");
      analyze(tree, "yield x -> true;", "yield-statement");
      analyze(tree, "return;", "return-statement");
      analyze(tree, "return x;", "return-statement");
      analyze(tree, "return x as String;", "return-statement");
      analyze(tree, "return x -> true;", "return-statement");
      analyze(tree, "err", "reference-property");
      analyze(tree, "err::println", "reference-property");
      analyze(tree, "err[0]", "reference-property");
      analyze(tree, "err[0][0]", "reference-property");
      analyze(tree, "err::println", "reference");
      analyze(tree, "in.x", "reference");
      analyze(tree, "in.x", "expression");
      analyze(tree, "0..10", "range");
      analyze(tree, "i", "for-in-declaration");
      analyze(tree, "var i", "for-in-declaration");
      analyze(tree, "var i: Integer", "for-in-declaration");
      analyze(tree, "i: Integer", "for-in-declaration");
      analyze(tree, "for(i in x) println(i);", "for-in-statement");
      analyze(tree, "for(var i in x) println(i);", "for-in-statement");
      analyze(tree, "for(var i: Integer in x) println(i);", "for-in-statement");
      analyze(tree, "for(i: Integer in x) println(i);", "for-in-statement");
      analyze(tree, "for(i: Integer in x.getValues().iterator()) println(i);", "for-in-statement");
      analyze(tree, "for(i in 0..10) println(i);", "for-in-statement");       
      analyze(tree, "util.function.Function", "full-qualifier");
      analyze(tree, "import util.function.Function;", "import");
      analyze(tree, "return i;", "statement");
      analyze(tree, "try{}catch(e){}finally{}", "try-statement");
      analyze(tree, "try{println(1);}catch(e){println(2);}finally{println(3);}", "try-statement");      
      analyze(tree, "try{println(1);}catch(e){println(2);println(3);}finally{println(3);}", "try-statement");  
      analyze(tree, "try {\n}catch(e){\n}finally{\n}", "try-statement");  
      analyze(tree, "try {\n}catch(e){\n}", "try-statement");  
      analyze(tree, "synchronized(x) {x++;}", "synchronized-statement");
      analyze(tree, "trait X{}", "trait-definition");
      analyze(tree, "class X{const x = 11;}", "class-definition");
      analyze(tree, "trait X{const x = 11;}", "trait-definition");
      analyze(tree, "const x = 11;", "trait-constant");
      analyze(tree, "a&&y", "conditional");
      analyze(tree, "a&&y", "combination");
      analyze(tree, "Integer[][]", "constraint");
      analyze(tree, "Integer[][]", "reference");
      analyze(tree, "f = 0;", "assignment-statement");
      analyze(tree, "@Blah", "argument");
      analyze(tree, "@Blah(x: 55)", "argument");
      analyze(tree, "@Blah(x: 55, y: 'blah')", "argument");
      analyze(tree, "blah(){}", "class-function");
      analyze(tree, "@Blah", "annotation-declaration");
      analyze(tree, "@Blah()", "annotation-declaration");
      analyze(tree, "@Blah(x: 1)", "annotation-declaration");
      analyze(tree, "@Blah", "annotation-list");
      analyze(tree, "@Blah()", "annotation-list");
      analyze(tree, "@Blah(x: 1)", "annotation-list");
      analyze(tree, "@Blah@Foo", "annotation-list");
      analyze(tree, "@Blah()@Foo(x:1)", "annotation-list");
      analyze(tree, "@Blah(x: 1)@Foo", "annotation-list");
      analyze(tree, "blah(){}", "class-function");
      analyze(tree, "public blah(){}", "class-function");
      analyze(tree, "@Blah blah(){}", "class-function");
      analyze(tree, "@Blah class Boo{}", "class-definition");
      analyze(tree, "@Blah", "annotation-declaration");
      analyze(tree, "-blah", "value-operand");
      analyze(tree, "-blah", "calculation-operand");
      analyze(tree, "-blah", "assignment-expression");
      analyze(tree, "this.addr = -blah;", "assignment-statement");
      analyze(tree, "addr = \"${host}:${port}\".getBytes();", "assignment-statement");
      analyze(tree, "this.addr = \"${host}:${port}\".getBytes();", "assignment-statement");
      analyze(tree, "{this.addr = \"${host}:${port}\".getBytes();}", "compound-statement");
      analyze(tree, "{this.addr = \"${host}:${port}\".getBytes();}", "group-statement");
      analyze(tree, "new(a,b,c){this.addr = \"${host}:${port}\".getBytes();}", "class-constructor");
      analyze(tree, "class Blah with Runnable{new(a,b,c){this.addr = \"${host}:${port}\".getBytes();}}", "script");
      analyze(tree, "count = source.read(buffer)", "assignment");
      analyze(tree, "(count = source.read(buffer))", "assignment-operand");
      analyze(tree, "(count = source.read(buffer)) != -1", "conditional");
      analyze(tree, "a == b", "expression");
      analyze(tree, "a, b...", "parameter-list");
      analyze(tree, "f1 < f2 ? f1 : f2", "expression");
      analyze(tree, "x=f1 < f2 ? f1 : f2", "expression");
      analyze(tree, "x=(f1 < f2 ? f1 : f2)", "expression");
      //analyze(tree, "x+(f1 < f2 ? f1 : f2)", "expression");
      analyze(tree, "[1,2,3]", "expression"); 
      analyze(tree, "x[0]", "expression");  
      analyze(tree, "x[0][1]", "expression");  
      analyze(tree, "call()", "expression");      
      analyze(tree, "call(12)", "expression");
      analyze(tree, "call(12.0f)", "expression");   
      analyze(tree, "call(\"12\")", "expression");   
      analyze(tree, "call(a[12])", "expression");   
      analyze(tree, "call().another()", "expression");
      analyze(tree, "call().another()", "expression");  
      analyze(tree, "call(12.0f).another(\"text\")", "expression");
      analyze(tree, "document.getElementById(\"demo\")", "expression");
      analyze(tree, "document.innerHTML", "expression"); // too long of a match???? i.e it matches primary           
      //analyze(tree, "document.innerHTML", "indirect-reference"); // too long of a match???? i.e it matches primary            
      analyze(tree, "document.getElementById(\"demo\").innerHTML", "expression");
      analyze(tree, "document.getElementById(\"demo\").innerHTML=z", "expression");
      analyze(tree, "calc.sumValues(1,3,Math.max(2,g.time))", "expression");
      analyze(tree, "a.b(C.D(\" This is a [] trickey index \\\"ok\\\"\"), \"some text\",E.F.G(H.I(\"4\")))", "expression");
      analyze(tree, "method.invoke(i++, \"some text\",  g.doIt())", "expression");
      analyze(tree, "show(x)", "expression");      
      analyze(tree, "show(new Date(1,2,3))", "expression");
      analyze(tree, "x=new StringBuilder()", "expression");
      analyze(tree, "new StringBuilder(1)", "construct");
      analyze(tree, "new StringBuilder()", "construct");      
      analyze(tree, "show(1)", "expression");
      analyze(tree, "show(new Date(1,2,3),1+2, \"some text\")", "expression");
      analyze(tree, "x+=show(new Date(1,2,3),1+2, \"some text\", true, false)", "expression");
      analyze(tree, "new Date(1,2,3).doSomeWork()", "expression");
      analyze(tree, "return i;", "statement");
      analyze(tree, "return new Value(\"blah\");", "statement");        
      analyze(tree, "return new Value(\"blah\").doWork();", "statement");
      analyze(tree, "return i+(x/d);", "statement");
      analyze(tree, "return;", "statement");
      analyze(tree, "return++i;", "statement");
      analyze(tree, "t", "expression");
      analyze(tree, "t!=null", "expression");
      analyze(tree, "i>2", "comparison");
      analyze(tree, "i>2", "expression");
      analyze(tree, "a&&y", "expression");
      analyze(tree, "a&&(y)", "expression");
      analyze(tree, "i", "conditional-operand");
      analyze(tree, "i", "comparison-operand");
      analyze(tree, "32", "comparison-operand");
      analyze(tree, "true", "comparison-operand");       
      analyze(tree, "i>32", "comparison");
      analyze(tree, "i>32", "conditional-operand");       
      analyze(tree, "(i>32)&&true", "expression");       
      analyze(tree, "i>32&&true", "expression"); 
      analyze(tree, "i>2&&t!=null", "expression");
      analyze(tree, "i>2&&(t!=null||i==3)", "expression");
      analyze(tree, "throw e;", "throw-statement");
          
   }

   private void analyze(SyntaxParser analyzer, String source, String grammar) throws Exception {
      analyze(analyzer, source, grammar, true);
   }

   private void analyze(SyntaxParser analyzer, String source, String grammar, boolean success) throws Exception {
      SyntaxNode list = analyzer.parse(null, source, grammar);

      if (list != null) {
         LexerBuilder.print(analyzer, source, grammar);
      }else {
         if(success) {
            assertTrue(false);
         } 
      }
   }

}

