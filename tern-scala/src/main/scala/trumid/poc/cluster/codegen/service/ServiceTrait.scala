package trumid.poc.cluster.codegen.service

import trumid.poc.cluster.codegen.union._
import trumid.poc.model.{Domain, Entity, Mode}

class ServiceTrait(domain: Domain, entity: Entity, mode: Mode) extends UnionTrait(domain, entity, mode) {

  protected override def generateTopicMethod(): Unit = {
    builder.append(s"   def topic(handler: ${getName}Handler): TopicRoute\n")
  }
}
