package trumid.poc.impl.server.demo.book

object PriceScale {
  val DEFAULT = PriceScale(2)

  def apply(scale: Int) = {
    new PriceScale(Math.pow(10, scale).longValue())
  }
}

class PriceScale(exponent: Long) {

  def toDouble(price: Price): Double = {
    price.value.doubleValue() / exponent
  }

  def toPrice(value: Long): Price = {
    Price((value * exponent).longValue(), this)
  }

  def toPrice(value: Double): Price = {
    Price((value * exponent).longValue(), this)
  }
}

object Price {

  def apply(value: Int): Price = {
    PriceScale.DEFAULT.toPrice(value)
  }

  def apply(value: Long): Price = {
    PriceScale.DEFAULT.toPrice(value)
  }

  def apply(value: Float): Price = {
    PriceScale.DEFAULT.toPrice(value)
  }

  def apply(value: Double): Price = {
    PriceScale.DEFAULT.toPrice(value)
  }
}

case class Price(value: Long, scale: PriceScale) {

  def toDouble(): Double = {
    scale.toDouble(this)
  }

  override def toString(): String = {
    toDouble().toString()
  }
}
