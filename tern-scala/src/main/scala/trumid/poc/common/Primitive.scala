package trumid.poc.common

sealed trait Primitive {
  def size(): Int
}

object Primitive {

  object BYTE extends Primitive {
    override def size(): Int = 1
  }

  object SHORT extends Primitive {
    override def size(): Int = 2
  }

  object INT extends Primitive {
    override def size(): Int = 4
  }

  object LONG extends Primitive {
    override def size(): Int = 8
  }

  object FLOAT extends Primitive {
    override def size(): Int = 4
  }

  object DOUBLE extends Primitive {
    override def size(): Int = 8
  }

  object BOOLEAN extends Primitive {
    override def size(): Int = 1
  }

  object CHAR extends Primitive {
    override def size(): Int = 2
  }
}
