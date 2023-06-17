package trumid.poc.cluster.codegen

import trumid.poc.cluster.codegen.enumeration.EnumTrait
import trumid.poc.cluster.codegen.service.{ServiceBuilder, ServiceHandler, ServiceTrait}
import trumid.poc.cluster.codegen.struct.{StructArray, StructArrayCodec, StructBuilder, StructCodec, StructTrait, StructValidator}
import trumid.poc.codegen.common.{Template, TemplateRegistry}
import trumid.poc.model.{Domain, Entity, Mode}

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
    if(entity.getCategory.isService()) {
      addServices(entity, templates)
    }
    templates
  }

  private def addStructs(entity: Entity, templates: util.List[Template]) = {
    templates.add(new StructTrait(domain, entity, mode))
    templates.add(new StructBuilder(domain, entity, mode))
    templates.add(new StructCodec(domain, entity, mode))
    templates.add(new StructArray(domain, entity, mode))
    templates.add(new StructArrayCodec(domain, entity, mode))
    templates.add(new StructValidator(domain, entity, mode))
  }

  private def addInterfaces(entity: Entity, templates: util.List[Template]) = {
    templates.add(new StructTrait(domain, entity, mode))
  }

  private def addEnumerations(entity: Entity, templates: util.List[Template]) = {
    templates.add(new EnumTrait(domain, entity, mode))
  }

  private def addServices(entity: Entity, templates: util.List[Template]) = {
    templates.add(new ServiceHandler(domain, entity, mode))
    templates.add(new ServiceBuilder(domain, entity, mode))
    templates.add(new ServiceTrait(domain, entity, mode))
  }
}
