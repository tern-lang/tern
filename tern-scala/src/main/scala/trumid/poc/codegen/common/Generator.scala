package trumid.poc.codegen.common

import trumid.poc.dsl.DomainLoader
import trumid.poc.model.{Domain, Mode, Version}

import java.io._
import java.util

class Generator(factory: (Domain, Mode) => TemplateRegistry, in: Seq[String], out: File) {

  def generate(version: Version, mode: Mode): Domain = {
    val domain: Domain = generateDomain(version)
    val templates: util.List[Template] = findTemplates(domain, mode)

    templates.forEach(template => {
        val result: GeneratedFile = template.generate
        val path: String = result.getPath
        val source: String = result.getSource

        try {
          val file: File = new File(out, path)

          file.getParentFile.mkdirs

          val writer = new PrintWriter(file.getCanonicalPath)

          writer.write(source)
          writer.close()
        } catch {
          case e: Exception =>
            throw new IllegalStateException(s"Could not generate ${path}", e)
        }
    })
    domain
  }

  private def generateDomain(version: Version): Domain = {
    new DomainLoader(version).load(in.map(file => {
      val resource = if(file.startsWith("/")) {
        getClass.getResource(file)
      } else {
        getClass.getResource(s"/${file}")
      }

      if(resource == null) {
        throw new IllegalArgumentException(s"Could not resolve location of ${file}")
      }
      resource
    }))
  }

  private def findTemplates(domain: Domain, mode: Mode): util.List[Template] = {
    val matches: util.List[Template] = new util.ArrayList[Template]
    val registry: TemplateRegistry = factory.apply(domain, mode)

    domain.getEntities().stream().forEach(entity => {
      val templates: util.List[Template] = registry.resolve(entity)

      templates.forEach(template => {
        if (template.isRequired) {
          matches.add(template)
        }
      })
    })
    matches
  }
}
