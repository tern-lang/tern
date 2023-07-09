package trumid.ats.dsl.codgen.cluster

import trumid.ats.dsl.codgen.cluster.enumeration.EnumTrait
import trumid.ats.dsl.codgen.cluster.service.{ServiceBuilder, ServiceClient, ServiceCodec, ServiceHandler, ServicePublisher, ServiceTrait}
import trumid.ats.dsl.codgen.cluster.struct.{StructArray, StructArrayCodec, StructBuilder, StructCodec, StructTrait, StructValidator}
import trumid.ats.dsl.codgen.cluster.union.{UnionBuilder, UnionCodec, UnionHandler, UnionTrait}
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
    if(entity.getCategory.isUnion()) {
      addUnions(entity, templates)
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
    templates.add(new ServiceCodec(domain, entity, mode))
    templates.add(new ServicePublisher(domain, entity, mode))
    templates.add(new ServiceClient(domain, entity, mode))
  }

  private def addUnions(entity: Entity, templates: util.List[Template]) = {
    templates.add(new UnionHandler(domain, entity, mode))
    templates.add(new UnionBuilder(domain, entity, mode))
    templates.add(new UnionTrait(domain, entity, mode))
    templates.add(new UnionCodec(domain, entity, mode))
  }
}
