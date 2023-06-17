package trumid.poc.common

sealed trait Primitive {
  def size(): Int
  def name(): String
  def isByte(): Boolean = false
  def isShort(): Boolean = false
  def isInt(): Boolean = false
  def isLong(): Boolean = false
  def isFloat(): Boolean = false
  def isDouble(): Boolean = false
  def isChar(): Boolean = false
  def isBoolean(): Boolean = false
  def isString(): Boolean = false
}

object Primitive {

  object BYTE extends Primitive {
    override def size(): Int = 1
    override def name(): String = "Byte"
    override def isByte(): Boolean = true
  }

  object SHORT extends Primitive {
    override def size(): Int = 2
    override def name(): String = "Short"
    override def isShort(): Boolean = true
  }

  object INT extends Primitive {
    override def size(): Int = 4
    override def name(): String = "Int"
    override def isInt(): Boolean = true
  }

  object LONG extends Primitive {
    override def size(): Int = 8
    override def name(): String = "Long"
    override def isLong(): Boolean = true
  }

  object FLOAT extends Primitive {
    override def size(): Int = 4
    override def name(): String = "Float"
    override def isFloat(): Boolean = true
  }

  object DOUBLE extends Primitive {
    override def size(): Int = 8
    override def name(): String = "Double"
    override def isDouble(): Boolean = true
  }

  object BOOLEAN extends Primitive {
    override def size(): Int = 1
    override def name(): String = "Boolean"
    override def isBoolean(): Boolean = true
  }

  object CHAR extends Primitive {
    override def size(): Int = 2
    override def name(): String = "Char"
    override def isChar(): Boolean = true
  }

  object STRING extends Primitive {
    override def size(): Int = 2
    override def name(): String = "String"
    override def isString(): Boolean = true
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
