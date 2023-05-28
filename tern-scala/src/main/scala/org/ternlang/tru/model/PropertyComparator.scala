package org.ternlang.tru.model

import java.lang.Boolean.{compare => compareBoolean}
import java.util.Comparator

class PropertyComparator extends Comparator[Property] {

  override def compare(left: Property, right: Property): Int = {
    val leftName = left.toString
    val rightName = right.toString

    if (left.isDynamic == left.isDynamic) {
      return leftName.compareTo(rightName)
    }
    compareBoolean(left.isDynamic, right.isDynamic)
  }
}

