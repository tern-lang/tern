package trumid.poc.dsl.tree.enumeration

import org.ternlang.core.Evaluation
import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope
import org.ternlang.core.variable.Value
import org.ternlang.tree.NameReference
import org.ternlang.tree.construct.MapEntryData
import org.ternlang.tree.literal.TextLiteral
import EnumProperty.EnumNameReference
import trumid.poc.common.SnakeCaseStyle
import trumid.poc.model.{Entity, Property}

import java.util
import java.util.Collections

class EnumProperty(identifier: TextLiteral, entries: MapEntryData) {
  private val reference: EnumProperty.EnumNameReference = new EnumNameReference(identifier)

  def this(identifier: TextLiteral) {
    this(identifier, null)
  }

  def include(scope: Scope, entity: Entity, path: Path): Unit = {
    val name: String = reference.getName(scope)
    val property: Property = entity.addProperty(name)
    val constraint: String = entity.getName()
    val attributes: util.Map[String, Any] = create(scope)

    property.getMask().add(Property.ENUM)
    property.setConstraint(constraint)
    property.getAttributes().putAll(attributes)
  }

  def process(scope: Scope, entity: Entity, path: Path): Unit = {
    val name: String = reference.getName(scope)
    val property: Property = entity.getProperty(name)

    property.getMask().add(Property.ENUM)
  }

  private def create(scope: Scope): util.Map[String, Any] = {
    val attributes: util.Map[String, Any] = new util.LinkedHashMap[String, Any]

    if (entries != null) {
      val value: Value = entries.evaluate(scope, null)
      val map: util.Map[Any, Any] = value.getValue()

      map.keySet().forEach(key => {
        val name: String = String.valueOf(key)
        val attribute: Any = map.get(name)

        attributes.put(name, attribute)
      })
    }
    Collections.unmodifiableMap(attributes)
  }
}

object EnumProperty {

  private class EnumNameReference(val identifier: Evaluation) extends NameReference(identifier) {

    override def getName(scope: Scope): String = {
      val name: String = super.getName(scope)
      SnakeCaseStyle.toCase(name)
    }
  }
}
