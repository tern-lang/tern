package trumid.poc.model

class Mask(private var mask: Int) {

  def value(): Int = {
    mask
  }

  def add(value: Int): Mask = {
    mask = mask | value
    this
  }

  def add(value: Mask): Mask = {
    mask = mask | value.mask
    this
  }

  def remove(value: Int): Mask = {
    mask = mask & ~value
    this
  }

  def remove(value: Mask): Mask = {
    mask = mask & ~value.mask
    this
  }
}
