package org.ternlang.tru.cluster

trait ResultCode {
  def getCode(): String
  def getDescription(): String
}
