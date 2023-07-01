package trumid.poc.impl.server.demo.book

import org.scalatest._

class PriceScaleTest extends FlatSpec with Matchers {

  it should "scale price according to exponent" in {
    PriceScale.DEFAULT.toPrice(100.1).value shouldBe 10010l
    PriceScale.DEFAULT.toPrice(100.1).toDouble() shouldBe 100.1d
  }
}
