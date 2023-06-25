package trumid.poc.cluster.codegen.struct

import trumid.poc.codegen.common.Template
import trumid.poc.model.{Domain, Entity, Mode}

class StructArrayCodec(domain: Domain, entity: Entity, mode: Mode) extends Template(domain, entity, mode) {

  override protected def getName(): String = entity.getName(mode) + "ArrayCodec"
  override protected def getCategory() = "final class"

  override protected def generateExtraImports(): Unit = {
    builder.append("import trumid.poc.common._\n")
    builder.append("import trumid.poc.common.array._\n")
    builder.append("import trumid.poc.common.message._\n")
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
    with Flyweight[${name}ArrayCodec] {

   override def reset(): ${name}ArrayCodec = {
      chain.reset()
      this
   }

   override def clear(): ${name}ArrayCodec = {
      chain.clear()
      this
   }
}""")
  }
}
