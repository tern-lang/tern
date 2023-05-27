package org.ternlang.tru.model

import org.ternlang.common.CopyOnWriteCache
import org.ternlang.core.scope.Scope

import java.util
import java.util.Collections
import java.util.concurrent.CopyOnWriteArrayList

case class Domain(version: Version, scope: Scope) {
  private val namespaces = new NamespaceSet(this)

  def isPackagePresent(name: String): Boolean = namespaces.isPresent(name)
  def getNamespace(name: String): Namespace = namespaces.getNamespace(name)
  def getNamespaces(): util.List[Namespace] = namespaces.getNamespaces()
  def addNamespace(name: String): Namespace = namespaces.addNamespace(name)

  private class NamespaceSet(domain: Domain) {
    private val namespaces = new CopyOnWriteCache[String, Namespace]
    private val declared = new CopyOnWriteArrayList[Namespace]

    def addNamespace(name: String): Namespace = {
      var namespace = namespaces.fetch(name)

      if (namespace == null) {
        namespace = new Namespace(domain, name)
        namespaces.cache(name, namespace)
        declared.add(namespace)
      }
      namespace
    }

    def getNamespace(name: String): Namespace = namespaces.fetch(name)
    def getNamespaces(): util.List[Namespace] = Collections.unmodifiableList(declared)
    def isPresent(name: String): Boolean = namespaces.contains(name)
  }
}
