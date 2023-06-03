package org.ternlang.tru.cluster.codegen

import org.ternlang.tru.cluster.codegen.enumeration.EnumTrait
import org.ternlang.tru.cluster.codegen.struct.{StructBuilder, StructTrait}
import org.ternlang.tru.codegen.common.{Template, TemplateRegistry}
import org.ternlang.tru.model.{Domain, Entity, Mode}

import java.util

class ClusterRegistry(domain: Domain, mode: Mode) extends TemplateRegistry {

  override def resolve(entity: Entity): util.List[Template] = {
    val templates = new util.ArrayList[Template]

    if(entity.getCategory.isStruct()) {
      addStructs(entity, templates)
    }
    if(entity.getCategory.isInterface()) {
      addInterfaces(entity, templates)
    }
    if(entity.getCategory.isEnum()) {
      addEnumerations(entity, templates)
    }
    templates
  }

  private def addStructs(entity: Entity, templates: util.List[Template]) = {
    templates.add(new StructTrait(domain, entity, mode))
    templates.add(new StructBuilder(domain, entity, mode))
  }

  private def addInterfaces(entity: Entity, templates: util.List[Template]) = {
    templates.add(new StructTrait(domain, entity, mode))
  }

  private def addEnumerations(entity: Entity, templates: util.List[Template]) = {
    templates.add(new EnumTrait(domain, entity, mode))
  }
}
