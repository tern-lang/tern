package trumid.poc.cluster.codegen.service

import trumid.poc.model.{Domain, Entity, Mode}
import trumid.poc.cluster.codegen.union._

class ServiceHandler(domain: Domain, entity: Entity, mode: Mode) extends UnionHandler(domain, entity, mode) {

}
