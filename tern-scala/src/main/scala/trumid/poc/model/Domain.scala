package trumid.poc.model

import org.ternlang.core.module.Path
import org.ternlang.core.scope.Scope

import java.util
import java.util.Collections
import java.util.concurrent.{ConcurrentHashMap, CopyOnWriteArrayList}
import java.util.stream.Collectors

case class Domain(version: Version, scope: Scope) {
  private val namespaces = new NamespaceSet(this)
  private val sources = new SourceUnitSet(this, namespaces)

  def getVersion(): Version = version

  def getEntities(): util.List[Entity] = getNamespaces().stream()
    .flatMap(namespace => namespace.getEntities().stream()).collect(Collectors.toList())

  def getNamespace(name: String): Namespace = namespaces.getNamespace(name)

  def getNamespaces(): util.List[Namespace] = namespaces.getNamespaces()

  def isNamespacePresent(name: String): Boolean = namespaces.isNamespacePresent(name)

  def getSourceUnit(path: String): SourceUnit = sources.getSourceUnit(path)

  def getSourceUnits(): util.List[SourceUnit] = sources.getSourceUnits()

  def addSourceUnit(path: Path, qualifier: String): SourceUnit = sources.addSourceUnit(path.getPath, qualifier)

  def isSourceUnitPresent(path: String): Boolean = sources.isSourceUnitPresent(path)

  private class SourceUnitSet(domain: Domain, namespaces: NamespaceSet) {
    private val sources = new ConcurrentHashMap[String, SourceUnit]
    private val declared = new CopyOnWriteArrayList[SourceUnit]

    def addSourceUnit(path: String, qualifier: String): SourceUnit = {
      val namespace = namespaces.addNamespace(qualifier)

      if (sources.contains(path)) {
        throw new IllegalStateException(s"Source unit ${path} was already defined")
      }
      val unit = new SourceUnit(domain, namespace, path)

      sources.put(path, unit)
      declared.add(unit)
      unit
    }

    def getSourceUnit(path: String): SourceUnit = sources.get(path)

    def getSourceUnits(): util.List[SourceUnit] = Collections.unmodifiableList(declared)

    def isSourceUnitPresent(path: String): Boolean = sources.contains(path)
  }

  private class NamespaceSet(domain: Domain) {
    private val namespaces = new ConcurrentHashMap[String, Namespace]
    private val declared = new CopyOnWriteArrayList[Namespace]

    def addNamespace(name: String): Namespace = {
      namespaces.computeIfAbsent(name, _ => {
        val namespace = new Namespace(domain, name)

        declared.add(namespace)
        namespace
      })
    }

    def getNamespace(name: String): Namespace = namespaces.get(name)

    def getNamespaces(): util.List[Namespace] = Collections.unmodifiableList(declared)

    def isNamespacePresent(name: String): Boolean = namespaces.contains(name)
  }
}
