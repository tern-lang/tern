package org.ternlang.tru.codegen.common

import org.ternlang.tru.model.Entity
import java.util
trait TemplateRegistry {
  def resolve(entity: Entity): util.List[Template]
}
