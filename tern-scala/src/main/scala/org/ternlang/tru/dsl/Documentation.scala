package org.ternlang.tru.dsl

object Documentation {
  val description = "description"
  val summary = "summary"
  val example = "example"
  var empty = Documentation(null, null, null)
}

case class Documentation(description: String, summary: String, example: String)
