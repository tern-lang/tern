package org.ternlang.tru.domain.tree.enumeration

import org.ternlang.common.Array
import org.ternlang.core.Evaluation
import org.ternlang.core.scope.Scope
import org.ternlang.core.variable.Value
import org.ternlang.tru.model.EnumValue

import java.util

class EnumReferenceList(enums: Array[EnumReference]) extends Evaluation {

  override def evaluate(scope: Scope, left: Value): Value = {
    val list: util.List[EnumValue] = new util.ArrayList[EnumValue]

    enums.forEach(reference => {
      val result: Value = reference.evaluate(scope, null)
      val values: util.List[EnumValue] = result.getValue()

      if (values != null) {
        list.addAll(values)
      }
    })
    Value.getTransient(list)
  }
}
