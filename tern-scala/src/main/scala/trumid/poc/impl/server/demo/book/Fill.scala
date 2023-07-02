package trumid.poc.impl.server.demo.book

import trumid.poc.example.events.FillType

class Fill(fillType: FillType, order: Order, price: Price, quantity: Long, aggressive: Boolean) {
  def price(): Price = price
  def quantity(): Long = quantity
  def order(): Order = order
  def fillType(): FillType = fillType
  def isAggressive(): Boolean = aggressive
}