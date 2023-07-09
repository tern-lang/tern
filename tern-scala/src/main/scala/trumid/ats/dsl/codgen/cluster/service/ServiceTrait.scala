package trumid.ats.dsl.codgen.cluster.service

import trumid.ats.dsl.codgen.cluster.union.UnionTrait
import trumid.poc.model.{Domain, Entity, Mode}

class ServiceTrait(domain: Domain, entity: Entity, mode: Mode) extends UnionTrait(domain, entity, mode) {

  protected override def generateTopicMethod(): Unit = {
    builder.append(s"   def topic(publisher: Publisher): TopicRoute\n")
    builder.append(s"   def topic(handler: ${getName}Handler): TopicRoute\n")
  }

  protected override def generateConvertMethod(): Unit = {
    builder.append(s"   def complete(scheduler: CompletionScheduler): TopicCompletionHandler\n")
  }
}
