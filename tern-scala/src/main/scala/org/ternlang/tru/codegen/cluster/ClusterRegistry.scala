package org.ternlang.tru.codegen.cluster

import org.ternlang.tru.codegen.cluster.struct.StructInterface
import org.ternlang.tru.codegen.common.{Template, TemplateRegistry}
import org.ternlang.tru.model.{Domain, Entity, Mode}

import java.util

class ClusterRegistry(domain: Domain, mode: Mode) extends TemplateRegistry {

  override def resolve(entity: Entity): util.List[Template] = {
    val templates = new util.ArrayList[Template]

    if(entity.getCategory.isStruct()) {
      addStructs(entity, templates)
    }
    templates
  }

  private def addStructs(entity: Entity, templates: util.List[Template]) = {
    templates.add(new StructInterface(domain, entity, mode))
  }
}
