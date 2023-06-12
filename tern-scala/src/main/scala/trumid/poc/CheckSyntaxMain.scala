package trumid.poc

import org.ternlang.parse.{SyntaxCompiler, SyntaxNode, SyntaxParser}

object CheckSyntaxMain  extends App {


  val builder = new SyntaxCompiler("tru.grammar")
  val parser: SyntaxParser = builder.compile
//  LexerBuilder.print(parser, "{File,Foo}", "type-set")
//  LexerBuilder.print(parser, "{File,Foo} from 'some/path/foo.tru'", "full-qualifier")
//  LexerBuilder.print(parser, "import {File,Foo} from 'some/path/foo.tru';", "import")
  LexerBuilder.print(parser, "union PlaceOrder requires Order {x: X; y: Y;}", "union-definition")

  object LexerBuilder {
    def create(file: String): SyntaxParser = {
      val builder = new SyntaxCompiler(file)
      builder.compile
    }

    def print(analyzer: SyntaxParser, source: String, name: String): Unit = {
      val node = analyzer.parse(null, source, name)
      print(node, 0, 0)
      System.err.println()
      System.err.println()
    }

    def print(token: SyntaxNode, parent: Int, depth: Int): Unit = {
      val grammar = token.getGrammar
      val children = token.getNodes
      for (i <- 0 until depth) {
        System.err.print(" ")
      }
      System.err.printf("%s --> (", grammar)
      var count = 0
      import scala.collection.JavaConversions._
      for (next <- children) {
        val child = next.getGrammar
        if ( {
          count += 1; count - 1
        } != 0) System.err.print(", ")
        System.err.printf(child)
      }
      System.err.print(")")
      System.err.print(" = <")
      System.err.print(token.getLine.getSource.trim)
      System.err.print("> at line ")
      System.err.print(token.getLine.getNumber)
      System.err.println()
      System.err.flush()
      for (next <- children) {
        val child = next.getGrammar
        //if(top.contains(child)) {
        if (child == grammar) print(next, System.identityHashCode(token), depth) // no depth change with no grammar change
        else print(next, System.identityHashCode(token), depth + 2)
        //}
        // }else {
        //System.err.println(next + " "+child.hasNext() + " "+iterator.ready());
        //  printStructure(child, top, depth); // stay at same depth
      }
    }
  }
}
