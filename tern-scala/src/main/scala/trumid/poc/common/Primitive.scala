package trumid.poc.common

sealed trait Primitive {
  def size(): Int
  def name(): String
}

object Primitive {

  object BYTE extends Primitive {
    override def size(): Int = 1
    override def name(): String = "Byte"
  }

  object SHORT extends Primitive {
    override def size(): Int = 2
    override def name(): String = "Short"
  }

  object INT extends Primitive {
    override def size(): Int = 4
    override def name(): String = "Int"
  }

  object LONG extends Primitive {
    override def size(): Int = 8
    override def name(): String = "Long"
  }

  object FLOAT extends Primitive {
    override def size(): Int = 4
    override def name(): String = "Float"
  }

  object DOUBLE extends Primitive {
    override def size(): Int = 8
    override def name(): String = "Double"
  }

  object BOOLEAN extends Primitive {
    override def size(): Int = 1
    override def name(): String = "Boolean"
  }

  object CHAR extends Primitive {
    override def size(): Int = 2
    override def name(): String = "Char"
  }

  object STRING extends Primitive {
    override def size(): Int = -1
    override def name(): String = "String"
  }

  def resolve(name: String): Option[Primitive] = {
    name match {
      case "Byte" => Some(BYTE)
      case "Short" =>  Some(SHORT)
      case "Int" =>  Some(INT)
      case "Long" =>  Some(LONG)
      case "Float" =>  Some(FLOAT)
      case "Double" =>  Some(DOUBLE)
      case "Boolean" =>  Some(BOOLEAN)
      case "Char" =>  Some(CHAR)
      case "String" =>  Some(STRING)
      case _ => None
    }
  }
}
