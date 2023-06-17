package trumid.poc.dsl.tree.imports

import java.util.function.Predicate
import java.util

trait Qualifier {
  def getPredicate(): Predicate[String]
  def getLocation(): String
  def getEntities(): util.Set[String]
}
