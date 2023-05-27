package org.ternlang.tru.model
import org.ternlang.core.annotation.Annotation

class Entity(namespace: Namespace, name: String) extends Importable with Annotated {

  def getName(): String = name;
  def getNamespace(): Namespace = namespace
  def getProperties(): List[Property] = List.empty
  override def getAnnotations(): Map[String, Annotation] = Map.empty
}
