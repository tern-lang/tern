package org.ternlang.tru

import java.util
import org.ternlang.parse.SyntaxCompiler
import org.ternlang.parse.SyntaxNode
import org.ternlang.parse.SyntaxParser

import java.util.List


object LexerBuilder {
  def create(file: String): SyntaxParser = {
    val builder = new SyntaxCompiler(file)
    builder.compile
  }

  @throws[Exception]
  def print(analyzer: SyntaxParser, source: String, name: String): Unit = {
    val node = analyzer.parse(null, source, name)
    print(node, 0, 0)
    System.err.println()
    System.err.println()
  }

  @throws[Exception]
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
    import scala.collection.JavaConversions._
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
