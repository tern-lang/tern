package org.ternlang.tru

import org.ternlang.parse.{SyntaxCompiler, SyntaxParser}

object CheckSyntax  extends App {


  val builder = new SyntaxCompiler("tru.grammar")
  val parser: SyntaxParser = builder.compile
  LexerBuilder.print(parser, "{File,Foo}", "type-set")
  LexerBuilder.print(parser, "{File,Foo} from 'some/path/foo.tru'", "full-qualifier")
  LexerBuilder.print(parser, "import {File,Foo} from 'some/path/foo.tru';", "import")
}
