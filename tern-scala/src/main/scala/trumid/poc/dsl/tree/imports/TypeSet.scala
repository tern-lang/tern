package trumid.poc.dsl.tree.imports

import org.ternlang.common.Array
import org.ternlang.core.Compilation
import org.ternlang.core.module.{Module, Path}
import org.ternlang.tree.NameReference
import org.ternlang.tree.literal.TextLiteral

import java.util.{HashSet, Set}

class TypeSet(types: Array[TextLiteral]) extends Compilation {

  override def compile(module: Module, path: Path, line: Int): Set[String] = {
    val set = new HashSet[String]()
    types.forEach(entry =>
      set.add(new NameReference(entry).getName(module.getScope)))
    set
  }

}
