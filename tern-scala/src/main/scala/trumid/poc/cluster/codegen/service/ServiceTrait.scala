package trumid.poc.cluster.codegen.service

import trumid.poc.model.{Domain, Entity, Mode}
import trumid.poc.cluster.codegen.union._

class ServiceTrait(domain: Domain, entity: Entity, mode: Mode) extends UnionTrait(domain, entity, mode) {

}
