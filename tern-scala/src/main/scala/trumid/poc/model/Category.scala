package trumid.poc.model

sealed trait Category {
  def isEnum(): Boolean = false
  def isInterface(): Boolean = false
  def isUnion(): Boolean = false
  def isStruct(): Boolean = false
  def isPrimitive(): Boolean = false
  def isTable(): Boolean = false
  def isDatabase(): Boolean = false
}

object EnumCategory extends Category {
  override def isEnum(): Boolean = true
}

object StructCategory extends Category {
  override def isStruct(): Boolean = true
}

object InterfaceCategory extends Category {
  override def isInterface(): Boolean = true
}

object UnionCategory extends Category {
  override def isUnion(): Boolean = true
}

object PrimitiveCategory extends Category {
  override def isPrimitive(): Boolean = true
}

object TableCategory extends Category {
  override def isStruct(): Boolean = true
  override def isTable(): Boolean = true
}

object DatabaseCategory extends Category {
  override def isDatabase(): Boolean = true
}
