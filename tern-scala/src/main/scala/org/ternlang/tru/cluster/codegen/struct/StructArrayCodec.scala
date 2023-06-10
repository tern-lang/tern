package org.ternlang.tru.cluster.codegen.struct

import org.ternlang.tru.codegen.common.Template
import org.ternlang.tru.model.{Domain, Entity, Mode}

class StructArrayCodec(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

  override protected def getName(): String = entity.getName(mode) + "ArrayCodec"
  override protected def getCategory() = "final class"

  override protected def generateExtraImports(): Unit = {
    builder.append("import org.ternlang.tru.common._\n")
    builder.append("import org.ternlang.tru.common.array._\n")
    builder.append("import org.ternlang.tru.common.message._\n")
  }

  override protected def generateEntity(): Unit = {
    generateBody()
  }

  override protected def generateBody(): Unit = {
    val name = entity.getName(mode)
    val category = getCategory()

    builder.append(s"""${category} ${name}ArrayCodec
    extends GenericArrayCodec[${name}, ${name}Builder](() => new ${name}Codec, value => value, ${name}Codec.REQUIRED_SIZE)
    with ${name}ArrayBuilder
    with Flyweight[${name}ArrayCodec] {}""")
  }
}
