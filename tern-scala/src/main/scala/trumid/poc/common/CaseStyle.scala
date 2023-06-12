package trumid.poc.common

import java.util
import java.util.{ArrayList, List}

sealed trait CaseStyle {

  def toCase(value: String): String = {
    val length: Int = value.length

    if (length > 0) {
      val tokens: List[String] = new ArrayList[String]
      val builder: StringBuilder = new StringBuilder

      for (i <- 0 until length) {
        val next: Char = value.charAt(i)
        val separator: Boolean = isSeparator(value, i)
        val caseChange: Boolean = isCaseChange(value, i)

        if (separator || caseChange) {
          val token: String = builder.toString.toLowerCase.trim

          if (!(token.isEmpty)) {
            tokens.add(token)
            builder.setLength(0)
          }
        }
        if (separator) {
          builder.append(" ")
        }
        else {
          builder.append(next)
        }
      }
      val token: String = builder.toString.toLowerCase.trim

      if (!(token.isEmpty)) {
        tokens.add(token)
        builder.setLength(0)
      }
      toCase(tokens, builder)
      builder.toString
    } else {
      ""
    }
  }

  private def isSeparator(value: String, index: Int): Boolean = {
    val curr: Char = value.charAt(index)

    if (curr == '-' || curr == '_') {
      true
    } else {
      Character.isSpaceChar(curr)
    }
  }

  private def isCaseChange(value: String, index: Int): Boolean = {
    if (index > 0) {
      val prev: Char = value.charAt(index - 1)
      val curr: Char = value.charAt(index)

      if (Character.isLetterOrDigit(prev) && Character.isLetterOrDigit(curr)) {
        return Character.isLowerCase(prev) && Character.isUpperCase(curr)
      }
    }
    false
  }

  def toCase(tokens: List[String], builder: StringBuilder): Unit
}

object CamelCaseStyle extends CaseStyle {

  override def toCase(tokens: util.List[String], builder: StringBuilder): Unit = {
    tokens.forEach(token => {
      val length = builder.length
      val first = token.charAt(0)
      val remainder = token.substring(1)

      if (length == 0) {
        builder.append(Character.toLowerCase(first))
      } else {
        builder.append(Character.toUpperCase(first))
      }
      builder.append(remainder)
    })
  }
}

object PascalCaseStyle extends CaseStyle {

  override def toCase(tokens: util.List[String], builder: StringBuilder): Unit = {
    tokens.forEach(token => {
      val first = token.charAt(0)
      val remainder = token.substring(1)

      builder.append(Character.toUpperCase(first))
      builder.append(remainder)
    })
  }
}

object SnakeCaseStyle extends CaseStyle {

  override def toCase(tokens: util.List[String], builder: StringBuilder): Unit = {
    tokens.forEach(token => {
      val value = token.toUpperCase
      val length = builder.length

      if (length > 0) {
        builder.append("_")
      }
      builder.append(value)
    })
  }
}

object SpinalCaseStyle extends CaseStyle {

  override def toCase(tokens: util.List[String], builder: StringBuilder): Unit = {
    tokens.forEach(token => {
      val value = token.toLowerCase
      val length = builder.length

      if (length > 0) {
        builder.append("-")
      }
      builder.append(value)
    })
  }
}

object TitleCaseStyle extends CaseStyle {

  override def toCase(tokens: util.List[String], builder: StringBuilder): Unit = {
    tokens.forEach(token => {
      val first = token.charAt(0)
      val remainder: String = token.substring(1)
      val length = builder.length

      if (length > 0) {
        builder.append(" ")
      }
      builder.append(Character.toUpperCase(first))
      builder.append(remainder)
    })
  }
}

object SentenceCaseStyle extends CaseStyle {

  override def toCase(tokens: util.List[String], builder: StringBuilder): Unit = {
    val count = tokens.size

    if (count > 0) {
      val token = tokens.get(0)
      val first = token.charAt(0)
      val remainder = token.substring(1)

      builder.append(Character.toUpperCase(first))
      builder.append(remainder)
    }
    for (i <- 1 until count) {
      val token = tokens.get(i)

      builder.append(" ")
      builder.append(token.toLowerCase)
    }
  }
}

