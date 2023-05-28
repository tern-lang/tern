package org.ternlang.tru

import org.ternlang.tru.domain.DomainLoader
import org.ternlang.tru.model.Version

import java.net.URL

object CodeGenMain extends App {

  val loader = new DomainLoader(Version(1, true))
  val domain = loader.load(Seq[URL](
    CodeGenMain.getClass.getResource("/example.tru")))

  domain.getNamespaces().forEach(namespace => {
    namespace.getEntities().forEach(entity => {
      println(entity.getName())
    })
  })
}
