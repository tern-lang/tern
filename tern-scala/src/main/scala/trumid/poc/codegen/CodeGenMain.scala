package trumid.poc.codegen

import trumid.ats.dsl.codgen.cluster.ClusterRegistry
import trumid.poc.codegen.common.Generator
import trumid.poc.model.{ApiMode, Version}

import java.io.File

object CodeGenMain extends App {

  val out =  new File("C:\\Work\\development\\tru\\cluster-demo-api\\src\\main\\scala")
  val generator = new Generator((domain, mode) => new ClusterRegistry(domain, mode), Seq("/trading_engine.tru"), out)

  generator.generate(Version(1, true), ApiMode)
}
