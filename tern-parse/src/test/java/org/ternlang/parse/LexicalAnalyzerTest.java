package org.ternlang.parse;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

public class LexicalAnalyzerTest extends TestCase {
   
   private static final String GRAMMAR_FILE = "grammar.txt";
   
   /*
    * for(int i = 0; i < 0xff; i++){ if(!Character.isWhitespace((char)i) &&
    * !Character.isISOControl((char)i) && !Character.isDigit((char)i) &&
    * !Character.isAlphabetic((char)i)) { if(i>0){ System.err.print("|"); }
    * System.err.print("'"); System.err.print((char)i); System.err.print("'"); }
    * } System.err.println(); for(int i = 0; i < 0xff; i++){
    * if(Character.isAlphabetic((char)i)&&Character.isLowerCase((char)i)) {
    * if(i>0){ System.err.print("|"); } System.err.print("'");
    * System.err.print((char)i); System.err.print("'"); } }
    */
   public void testGrammarTree() throws Exception {
      SyntaxParser tree = LexerBuilder.create(GRAMMAR_FILE); 

      assertNotNull(tree);

      analyze(tree, "(leftIndex < right) && (a[leftIndex] < partionElement)", "conditional");
      analyze(tree, "Foo", "type-reference-part");
      analyze(tree, "Foo.Blah", "type-reference");
      analyze(tree, "a.b.c", "reference");
      analyze(tree, "a.func().c", "reference");
      analyze(tree, "a", "reference");
      analyze(tree, "a?.b", "reference");
      analyze(tree, "assert true;", "assert-statement");
      analyze(tree, "assert 1==2;", "assert-statement");
      analyze(tree, "assert 2**8 == 256;", "assert-statement");
      analyze(tree, "assert (2!==3);", "assert-statement");
      analyze(tree, "assert false==false;", "assert-statement");
      analyze(tree, "assert false==(false);", "assert-statement");
      analyze(tree, "assert (false)==(false);", "assert-statement");
      //analyze(tree, "1==2", "comparison-operand");
      analyze(tree, "(1==2)", "comparison-operand");
      analyze(tree, "assert (1==2)==(false);", "assert-statement");
      analyze(tree, "x", "reference");
      analyze(tree, "map", "reference");
      analyze(tree, "map.get()", "reference");
      analyze(tree, "map.get(\"x\")", "reference");
      analyze(tree, "map.get(\"x\").toString()", "reference");
      analyze(tree, "map.get(\"x\").toString().substring(1, 0)", "reference");
      analyze(tree, "v.getKey(1)", "reference");
      analyze(tree, "v.getKey(1,2)", "reference");
      analyze(tree, "v.getKey(1,2)", "argument");
      analyze(tree, "map.get(v.getKey(1,2))", "reference");
      analyze(tree, "expect(p1.doWork()).toBe(\"Scott works for free\")", "reference");
      analyze(tree, "t[1]", "reference");
      analyze(tree, "t[1].blah", "reference");
      analyze(tree, "t[1].blah(1)", "reference");
      analyze(tree, "t[\"key goes here\"].blah", "reference");
      analyze(tree, "t[\"key goes here\"].blah.panel", "reference");
      analyze(tree, "t[\"key goes here\"].blah.panel[\"another index\"]", "reference");
      analyze(tree, "t[\"key goes here\"].blah.panel[\"another index\"].substring(1,2)", "reference");
      analyze(tree, "document.getElementById(\"demo\")", "reference");
      analyze(tree, "z", "reference");
      analyze(tree, "innerHTML", "reference");
      analyze(tree, "document.getElementById(\"demo\")", "reference");
      analyze(tree, "document.innerHTML", "reference"); // too long of a match???? i.e it matches primary
      //analyze(tree, "document.innerHTML", "indirect-reference"); // too long of a match???? i.e it matches primary
      analyze(tree, "document.getElementById(\"demo\").innerHTML", "reference");
      analyze(tree, "document.getElementById(\"demo\").innerHTML=z", "assignment");


      analyze(tree, "x+y", "calculation");
      analyze(tree, "x+y.getSomething()", "calculation");
      analyze(tree, "x+y[12].getSomething()", "calculation");
      analyze(tree, "(x)+y[12].getSomething()", "calculation");
      analyze(tree, "(x)+y[12]", "calculation");
      analyze(tree, "(x++)+y[12]", "calculation");
      analyze(tree, "(x)+y[12]++", "calculation");
      analyze(tree, "((x)+y++)", "calculation");
      analyze(tree, "x*y+2", "calculation");
      analyze(tree, "x*(y)+2", "calculation");
      analyze(tree, "x*(y+1)", "calculation");
      analyze(tree, "x*(y+1)+tt", "calculation");
      analyze(tree, "(x*(y+1)+tt)", "calculation");
      analyze(tree, "y*=(x*(y+1)+tt)", "assignment");
      analyze(tree, "y*=(x+=1)", "assignment");
      analyze(tree, "y=!x", "assignment");
      analyze(tree, "y=x>>>10", "assignment");

      analyze(tree, "0xff", "number");
      analyze(tree, "0xff", "calculation-operand");
      analyze(tree, "0xff", "literal");

      analyze(tree, "x&0xff", "calculation");
      analyze(tree, "(x+1)&0xff", "calculation");
      analyze(tree, "x+(i)&0xff", "calculation");
      analyze(tree, "x+i+0xff", "calculation");
      analyze(tree, "(x+i+0xff)", "calculation");
      analyze(tree, "(x+(i)&0xff)", "calculation");
      analyze(tree, "(x+(length>>>56)&0xff)", "calculation");

      analyze(tree, "a[\"x\"]", "expression");
      analyze(tree, "array[\"x\"]", "expression");
      analyze(tree, "array[1]", "expression");
      analyze(tree, "array[11]", "expression");
      analyze(tree, "array[10]", "expression");
      analyze(tree, "array[0]", "expression");
      analyze(tree, "1L", "number");
      analyze(tree, "1L", "literal");
      analyze(tree, "1L", "expression");
      analyze(tree, "a[1L]", "expression");
      analyze(tree, "array[101L]", "expression");
      analyze(tree, "array[1.0f]", "expression");
      analyze(tree, "array[a[\"x\"]]", "expression");
      //analyze(tree, "array[]", "expression");
      analyze(tree, "call()", "expression");
      analyze(tree, "call(12)", "expression");
      analyze(tree, "call(12.0f)", "expression");
      analyze(tree, "call(\"12\")", "expression");
      analyze(tree, "call(a[12])", "expression");
      analyze(tree, "call().another()", "expression");
      analyze(tree, "call().another()", "expression");
      analyze(tree, "call(12.0f).another(\"text\")", "expression");
      analyze(tree, "x", "variable");
      analyze(tree, "x", "expression");
      analyze(tree, "y", "expression");
      analyze(tree, "(x)", "expression");
      analyze(tree, "(y+1)/2.0f", "expression");
      analyze(tree, "=", "assignment-operator");
      analyze(tree, "x=y", "assignment");
      analyze(tree, "x+=y", "assignment");
      analyze(tree, "x+=(y+2.0f)", "assignment");
      analyze(tree, "x+=y+y", "assignment");
      analyze(tree, "x+=y.call()", "assignment");
      analyze(tree, "x+=y[1]", "assignment");
      analyze(tree, "++i", "increment");
      analyze(tree, "--i", "decrement");
      analyze(tree, "i++", "increment");
      analyze(tree, "i--", "decrement");
      analyze(tree, "i--", "expression");
      analyze(tree, "++i", "expression");
      analyze(tree, "i*=++i", "expression");
      analyze(tree, "c.call().vv=(++i)/2.0f", "expression");
      analyze(tree, "y*=a[++i]++", "expression");
      // original tests for enum based lexer
      analyze(tree, "y=--x", "expression");
      analyze(tree, "x", "expression");
      analyze(tree, "x=++i+test--", "expression");
      // XXX fix this, it should be legal
      //analyze(tree, "s=++i+test---1/(y++---i)", "expression");
      analyze(tree, "x=d.doIt(++i+test--).getStuff()", "expression");
      analyze(tree, "this.canvas[\"password\"].getInput().setPassword(true)", "expression");
      analyze(tree, "x=new MyFunction(\"John\",\"Doe\")", "expression");
      analyze(tree, "obj = {prop1: 5, prop2: 13, prop3: 8}", "expression");
      analyze(tree, "this.target[this.property] = !this.target[this.property]", "expression");
      analyze(tree, "document.getElementById(\"demo\").innerHTML = z", "expression");
      analyze(tree, "document.getElementById(\"demo\").innerHTML = \"Hello Dolly.\"", "expression");
      analyze(tree, "expect(p1.doWork()).toBe(\"Scott works for free\")", "expression");
      analyze(tree, "a.b(A.get(1),B.get(2),C.D(\"3\"))", "expression");
      analyze(tree, "y=(x*(i/(2+1))+2)", "expression");
      analyze(tree, "x*(i/(2+1))+2", "expression");
      analyze(tree, "x=(y=2+i)", "expression");
      analyze(tree, "x=new Task()", "expression");
      analyze(tree, "new Task()", "expression");
      analyze(tree, "g.run(new Task(\"test\", 1.9f))", "expression");
      analyze(tree, "rty[\"some index\"].panel.blah[\" something bigger \\\"in quote\\\"\"]", "expression");
      analyze(tree, "a.b(C.D(\" This is a [] trickey index \\\"ok\\\"\"), \"some text\",E.F.G(H.I(\"4\")))", "expression");
      analyze(tree, "Math.max()", "reference");
      analyze(tree, "Math.max(2,g.time)", "reference");
      analyze(tree, "Math.max(2,g.time)", "expression");
      //analyze(tree, "Math.max", "postfix-expression", false);
      //analyze(tree, "Math.max(2,g.time)", "postfix-expression",false);
      analyze(tree, "1,3,Math.max(2,g.time)", "argument-list");
      analyze(tree, "calc.sumValues(1,3,Math.max(2,g.time))", "expression");
      analyze(tree, "10.1223+calc.sumValues(1,3,Math.max(2,g.time))", "calculation");
      analyze(tree, "10.1223+calc.sumValues(1,3,Math.max(2,g.time))", "expression");
      analyze(tree, "runIt(10.1223+calc.sumValues(1,3,Math.max(2,g.time))+2, 10, \"hello\"+text)", "expression");
      analyze(tree, "a.doIt(1.0f, 10, \"hello\") * 10.0f-1.2", "expression");
      analyze(tree, "a.doIt().doSomethingElse()", "expression");
      analyze(tree, "a.doIt()", "expression");


      analyze(tree, "this.array[12].execute(\"test\")", "expression");
      analyze(tree, "a.b.c.d.e=12", "expression");
      analyze(tree, "a.b.c[1].d.e=12", "expression");
      analyze(tree, "a.b.c.get(12).d.e=12", "expression");
      analyze(tree, "a.b.c.get(inner.get(1,2,3)).d.e=12", "expression");

      analyze(tree, "(x)", "expression");
      analyze(tree, "((x))", "expression");
      analyze(tree, "(((x)))", "expression");
      analyze(tree, "(((x++)))", "expression");
      analyze(tree, "2+1", "expression");
      analyze(tree, "(2+1)", "expression");
      analyze(tree, "i/(2+1)", "expression");
      analyze(tree, "x*(i/(2+1))", "expression");
      analyze(tree, "x*(i/(2+1))+2", "expression");

      analyze(tree, "y/z", "calculation");
      analyze(tree, "(y/z)", "calculation");
      analyze(tree, "((y/z))", "calculation");

      analyze(tree, "((y/z))", "calculation");
      analyze(tree, "1", "calculation-operand");
      analyze(tree, "1.000f", "calculation-operand");
      analyze(tree, "i++", "calculation-operand");
      analyze(tree, "g.sum()+1", "calculation");
      analyze(tree, "(g.sum()+1)", "calculation");
      analyze(tree, "((g.sum())+(1))", "calculation");
      analyze(tree, "x", "calculation-operand");
      analyze(tree, "(y/z)", "calculation");

      analyze(tree, "x*(y/z)", "calculation");
      analyze(tree, "(x*(y/z))", "calculation");
      analyze(tree, "(x*(y/z))+1", "calculation");
      //analyze(tree, "((x*(y/z))+1)", "calculation");




      analyze(tree, "x+1", "calculation");
      analyze(tree, "x+1+z", "calculation");
      analyze(tree, "(1+z)", "calculation");
      analyze(tree, "a+b+c", "calculation");
      analyze(tree, "x+(1+z)", "calculation");
      analyze(tree, "x+ar[1].getOp(1+2/3)+z", "calculation");

      analyze(tree, "a&&b||c", "conditional");
      analyze(tree, "a==b&&c!=x+(arr[i++]/2)+3", "expression");
      analyze(tree, "a==b&&c!=x+(arr[i++]/2)+3||bool", "expression");
      analyze(tree, "a==b&&(c||d)", "expression");
      analyze(tree, "-1", "expression");
   
   }

   private void analyze(SyntaxParser analyzer, String source, String grammar) throws Exception {
      analyze(analyzer, source, grammar, true);
   }

   private void analyze(SyntaxParser analyzer, String source, String grammar, boolean success) throws Exception {
      Set<String> keep=new HashSet<String>();
      
      keep.add("method");
      keep.add("variable");
      keep.add("index");
      keep.add("literal");
      keep.add("expression"); 
      keep.add("calculation");
      keep.add("calculation-operator");
      keep.add("calculation");
      
      SyntaxNode list = analyzer.parse(null,source, grammar);
      assertNotNull(list);
      if (list != null) {
         //String remaining = iterator.ready();
        // if (remaining != null && remaining.length() > 0) {
        //    System.err.println("###################### FAIL TO COMPLETE #### [" + source + "] -> [" + remaining + "] #####");
        // }
         if (!success) {
            assertTrue(false);
         }
         LexerBuilder.print(analyzer, source, grammar);
      }else {
         if(success){
            assertTrue(false);
         }
      }
   }

}

