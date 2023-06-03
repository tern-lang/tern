package org.ternlang.tru.codegen

import org.ternlang.tru.codegen.cluster.ClusterRegistry
import org.ternlang.tru.codegen.common.Generator
import org.ternlang.tru.model.{ApiMode, Version}

import java.io.File

object CodeGenMain extends App {

  val out =  new File("C:\\Work\\development\\tern-lang\\tern\\tern-scala\\target\\generated-sources")
  val generator = new Generator((domain, mode) => new ClusterRegistry(domain, mode), Seq("/example.tru"), out)

  generator.generate(Version(1, true), ApiMode)
}
