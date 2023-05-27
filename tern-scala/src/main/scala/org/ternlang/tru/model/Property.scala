package org.ternlang.tru.model
import org.ternlang.core.annotation.Annotation

class Property(name: String) extends Variable with Annotated {

  override def getName: String = ???

  override def getConstraint: String = ???

  override def getAnnotations(): Map[String, Annotation] = ???
}
