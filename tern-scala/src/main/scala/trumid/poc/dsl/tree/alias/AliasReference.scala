package trumid.poc.dsl.tree.alias

import org.ternlang.core.scope.Scope
import org.ternlang.tree.NameReference
import org.ternlang.tree.literal.TextLiteral
import trumid.poc.dsl.tree.Optional
import trumid.poc.model.{Alias, Property}

class AliasReference(constraint: TextLiteral, optional: Optional) {
  private val reference = new NameReference(constraint)

  def resolve(scope: Scope, property: Property): Unit = {
    val constraint = reference.getName(scope)
    val state = scope.getState()
    val value = state.getValue(constraint)

    if (value != null) {
      val resolved = value.getValue()

      if (resolved.isInstanceOf[Alias]) {
        val alias = resolved.asInstanceOf[Alias]

        if (property.isArray && alias.isArray) {
          throw new IllegalStateException("Referencing an array of arrays")
        }
        if (property.isArray) {
          if (alias.isOptional) {
            throw new IllegalStateException("Illegal array of optionals")
          }
          alias.getAnnotations.forEach((name, annotation) => {
            property.getAnnotations.putIfAbsent(name, annotation)
          })
          property.getMask().add(Property.ARRAY)
          property.setConstraint(alias.getConstraint)
        } else {
          alias.getAnnotations.forEach((name, annotation) => {
            property.getAnnotations.putIfAbsent(name, annotation)
          })
          property.getMask().add(if (alias.isOptional || optional == null) Property.BLANK else Property.OPTIONAL)
          property.setConstraint(alias.getConstraint)
        }
      }
    }
  }
}
