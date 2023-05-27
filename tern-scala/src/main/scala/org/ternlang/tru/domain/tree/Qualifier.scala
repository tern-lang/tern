package org.ternlang.tru.domain.tree

import java.util.function.Predicate

trait Qualifier {
  def getPredicate(): Predicate[String]
  def getLocation(): String
}
