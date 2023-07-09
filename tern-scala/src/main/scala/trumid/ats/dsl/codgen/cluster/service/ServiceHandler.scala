package trumid.ats.dsl.codgen.cluster.service

import trumid.ats.dsl.codgen.cluster.union.UnionHandler
import trumid.poc.model.{Domain, Entity, Mode}

class ServiceHandler(domain: Domain, entity: Entity, mode: Mode) extends UnionHandler(domain, entity, mode) {
}
