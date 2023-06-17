package trumid.poc.cluster

trait OptionBuilder[S] {
  def some(): S
  def none(): Unit
}

object OptionBuilder {

  def apply[S, N](some: () => S, none: () => Unit): OptionBuilder[S] = {
    new OptionHandler(some, none)
  }

  private class OptionHandler[S](some: () => S, none: () => Unit) extends OptionBuilder[S] {
    def some(): S = some.apply()
    def none(): Unit = none.apply()
  }
}
