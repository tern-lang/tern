package org.ternlang.tru.codegen.common

import org.ternlang.tru.model._

import java.util
import java.util.Date

abstract class Template(domain: Domain, entity: Entity, mode: Mode, sorted: Boolean = true) {
  protected val imports = new util.HashSet[String]
  protected val builder = new SourceBuilder
  protected val date = new Date

  def isRequired: Boolean = {
    if (mode.isApiMode()) isLatestVersion else true
  }

  def isVersioned: Boolean = mode.isVersionedMode()

  def isLatestVersion: Boolean = domain.getVersion().isLatestVersion

  def getEntity: Entity = entity

  def getNamespace: Namespace = entity.getNamespace()

  def getPropertyOrder: PropertyOrder = if (sorted) SortedOrder else DeclarationOrder

  def generate: GeneratedFile = {
    generateNamespace()
    generateImports()
    generateEntity()
    generateFile
  }

  protected def generateFile(): GeneratedFile = {
    val origin = getNamespace

    if (origin == null) {
      throw new IllegalStateException("Entity has no namespace")
    }
    val name = getName
    val source = builder.toString
    val version = domain.getVersion
    val namespace = origin.getName
    val parent = namespace.replace(".", "/")
    val path = parent + "/" + name + ".scala"

    new GeneratedFile(path, source, version, mode)
  }

  protected def generateEntity(): Unit = {
    val name = getName
    val category = getCategory

    builder.append(s"${category} ${name} {\n")
    generateBody()
    builder.append("}\n")
  }

  protected def generateImports(): Unit = {
    val origin = getNamespace

    if (origin == null) {
      throw new IllegalStateException("Entity has no namespace")
    }
    generateImports(origin)
    builder.append("import blah.blah.fo.*\n")
    generateExtraImports()
    builder.append("\n")
  }

  protected def generateImports(namespace: Namespace): Unit = {
    val name = namespace.getName
    val scope = namespace.getScope

    if (scope == null) {
      throw new IllegalStateException(s"Namespace '${name}' has not been defined properly")
    }
    generatePropertyImports(namespace)
    generateSuperImports(namespace)
  }

  private def generatePropertyImports(namespace: Namespace): Unit = {
    val name = namespace.getName
    val version = namespace.getVersion
    val scope = namespace.getScope
    val state = scope.getState

    entity.getProperties().forEach(property => {
      if (!property.isPrimitive) {
        val constraint = property.getConstraint
        val value = state.getValue(constraint)

        if (value != null) {
          val entity: Entity = value.getValue.asInstanceOf[Entity]
          val origin: Namespace = entity.getNamespace
          val namespace: String = origin.getName

          if (imports.add(namespace)) {
            builder.append(s"import ${mode.getPackage(namespace, version)}.*\n")
          }
        }
      }
    })
  }

  private def generateSuperImports(namespace: Namespace): Unit = {
    val extend = entity.getExtends
    val version = namespace.getVersion

    if (extend != null) {
      val base = namespace.getVisibleEntity(extend)

      if (base == null) {
        throw new IllegalStateException(s"Could not resolve base type ${extend} from ${entity}")
      }
      if (mode.isVersionedMode) {
        builder.append(s"import ${base.getNamespace}.v${version}.${extend}V${version}\n")
      } else {
        builder.append(s"import ${base.getNamespace}.${extend}\n")
      }
    }
  }

  protected def generateExtraImports(): Unit = {
  }

  protected def generateNamespace(): Unit = {
    val namespace = getNamespace.getName
    val version = domain.getVersion
    val name = mode.getPackage(namespace, version)
    val origin = getClass.getSimpleName

    builder.append(s"// Generated at ${date} (${origin})\n")
    builder.append("package ")
    builder.append(name)
    builder.append(";\n\n")
  }

  protected def getName(): String

  protected def getCategory(): String

  protected def generateBody(): Unit

}
