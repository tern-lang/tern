package trumid.poc.dsl.tree.service

import org.ternlang.core.scope.Scope
import org.ternlang.tree.NameReference
import org.ternlang.tree.literal.TextLiteral

sealed trait ServiceResponse {
  def isReturn(): Boolean = false
  def isStream(): Boolean = false
  def getResponse(scope: Scope): String
}

class ServiceReturns(constraint: TextLiteral) extends ServiceResponse {
  private val reference: NameReference = new NameReference(constraint)
  override def isReturn(): Boolean = true
  override def getResponse(scope: Scope): String = reference.getName(scope)

}

class ServiceStreams(constraint: TextLiteral) extends ServiceResponse {
  private val reference: NameReference = new NameReference(constraint)
  override def isStream(): Boolean = true
  override def getResponse(scope: Scope): String = reference.getName(scope)
}
