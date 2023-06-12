package trumid.poc.codegen.common

import trumid.poc.model.Entity

import java.util
trait TemplateRegistry {
  def resolve(entity: Entity): util.List[Template]
}
