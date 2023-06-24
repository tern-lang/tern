package trumid.poc.dsl

sealed trait AnnotationType {
  def name(): String
  def attributes(): Seq[String] = Seq.empty
}

object AnnotationType {

  def resolve(name: String): Option[AnnotationType] = {
    name match {
      case "Version" => Some(Version)
      case "Positive" => Some(Positive)
      case "PositiveOrZero" => Some(PositiveOrZero)
      case "Negative" => Some(Negative)
      case "NegativeOrZero" => Some(NegativeOrZero)
      case "NotZero" => Some(NotZero)
      case "NotBlank" => Some(NotBlank)
    }
  }
}

object Version extends AnnotationType {
  override def name(): String = "Version"
  override def attributes(): Seq[String] = Seq("version")
}

object Positive extends AnnotationType {
  override def name(): String = "Positive"
}

object PositiveOrZero extends AnnotationType {
  override def name(): String = "PositiveOrZero"
}

object Negative extends AnnotationType {
  override def name(): String = "Negative"
}

object NegativeOrZero extends AnnotationType {
  override def name(): String = "NegativeOrZero"
}

object NotZero extends AnnotationType {
  override def name(): String = "NotZero"
}

object NotBlank extends AnnotationType {
  override def name(): String = "NotBlank"
}

