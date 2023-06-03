package org.ternlang.tru.dsl.tree.imports

import java.util.function.Predicate

trait Qualifier {
  def getPredicate(): Predicate[String]
  def getLocation(): String
}
