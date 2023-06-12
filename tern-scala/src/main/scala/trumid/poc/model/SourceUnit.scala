package trumid.poc.model

import org.ternlang.core.module.Path
import trumid.poc.common.SentenceCaseStyle

import java.util
import java.util.Collections
import java.util.concurrent.{ConcurrentHashMap, CopyOnWriteArrayList}

class SourceUnit(domain: Domain, namespace: Namespace, file: String) extends Importable {
  private val imports = new SourceUnit.ImportSet(this)
  private val constants = new SourceUnit.ConstantSet(this)
  private val entities = new SourceUnit.EntitySet(this)
  private val aliases = new SourceUnit.AliasSet(this)
  private val path = new Path(file)

  def getName(): String = file

  def getPath(): Path = path

  def getNamespace(): Namespace = namespace

  def addConstant(name: String, value: Any): Constant = {
    val constant = constants.addElement(name).setConstant(value)
    namespace.addConstant(constant)
    constant
  }

  def getConstant(name: String): Constant = constants.getElement(name)

  def getConstants(): util.List[Constant] = constants.getElements()

  def addEntity(name: String): Entity = {
    val entity = entities.addElement(name)
    namespace.addEntity(entity)
    entity
  }

  def getEntity(name: String): Entity = entities.getElement(name)

  def getEntities(): util.List[Entity] = entities.getElements()

  def addAlias(name: String): Alias = aliases.addElement(name)

  def getAlias(name: String): Alias = aliases.getElement(name)

  def getAliases(): util.List[Alias] = aliases.getElements()

  def getImports(): util.List[Path] = imports.getElements()

  def getImport(path: String): Path = imports.getElement(path)

  def addImport(path: String): Path = imports.addElement(path)

  override def toString() = file
}

object SourceUnit {

  abstract private class SourceUnitSet[T](unit: SourceUnit) {
    private val elements: util.Map[String, T] = new ConcurrentHashMap[String, T]
    private val declared: util.List[T] = new CopyOnWriteArrayList[T]

    def addElement(name: String): T = {
      var element: T = elements.get(name)

      if (element != null) {
        throw new IllegalStateException(s"${SentenceCaseStyle.toCase(getType)} '${name}' already present in '${unit.getName}'")
      }
      element = newElement(name, unit)
      elements.put(name, element)
      declared.add(element)
      element
    }

    def getElement(name: String): T = elements.get(name)

    def getElements(): util.List[T] = Collections.unmodifiableList(declared)

    protected def getType: String

    protected def newElement(name: String, unit: SourceUnit): T
  }

  private class ConstantSet(unit: SourceUnit) extends SourceUnit.SourceUnitSet[Constant](unit) {
    override protected def newElement(name: String, unit: SourceUnit) = new Constant(name)

    override protected def getType = "constant"
  }

  private class EntitySet(unit: SourceUnit) extends SourceUnit.SourceUnitSet[Entity](unit) {
    override protected def newElement(name: String, unit: SourceUnit) = new Entity(unit, name)

    override protected def getType = "entity"
  }

  private class AliasSet(unit: SourceUnit) extends SourceUnit.SourceUnitSet[Alias](unit) {
    override protected def newElement(name: String, unit: SourceUnit) = new Alias(name)

    override protected def getType = "alias"
  }

  private class ImportSet(unit: SourceUnit) extends SourceUnit.SourceUnitSet[Path](unit) {
    override protected def newElement(name: String, unit: SourceUnit) = new Path(name)

    override protected def getType = "import"
  }
}
