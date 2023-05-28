package org.ternlang.tru.domain

import org.ternlang.tru.domain.Documentation._

object AnnotationType {
  val PositiveOrZero = appply(false, "PositiveOrZero")
  val Positive = appply(false, "Positive")
  val NegativeOrZero = appply(false, "NegativeOrZero")
  val Negative = appply(false, "Negative")
  val NotZero = appply(false, "NotZero")
  val NotBlank = appply(false, "NotBlank")
  val Max = appply(false, "Max", List("value"))
  val Min = appply(false, "Min", List("value"))
  val MaxLength = appply(false, "MaxLength", List("length"))
  val MinLength = appply(false, "MinLength", List("length"))
  val Charset = appply(false, "Charset", List("name"))
  val Index = appply(false, "Index")
  val PrimaryKey = appply(false, "PrimaryKey")
  val Constant = appply(false, "Constant")
  val Resource = appply(true, "Resource", List("response"))
  val Subscription = appply(true, "Subscription", List("event"))
  val Internal = appply(false, "Internal")
  val Documentation = appply(false, "Documentation", List(description, summary, example))
  val Publish = appply(false, "Publish", List("event"))
  val AccessControl = appply(true, "AccessControl", List("permission"))
  val Response = appply(true, "Response", List("200", "201", "400", "401", "403", "404", "405", "406", "409", "500"))
  val Path = appply(false, "Path", List("path", "query"))
  val GET = appply(false, "GET")
  val POST = appply(false, "POST")
  val DELETE = appply(false, "DELETE")
  val PUT = appply(false, "PUT");

  def appply(reference: Boolean, name: String, attributes: List[String] = List.empty) = {
    AnnotationType(reference, name, None, attributes)
  }

  def resolve(name: String): Option[AnnotationType] = {
    name match {
      case "PositiveOrZero" => Some(PositiveOrZero)
      case "NotBlank" => Some(NotBlank)
    }
  }
}

case class AnnotationType(reference: Boolean, name: String, attribute: Option[String], attributes: List[String] = List.empty)
