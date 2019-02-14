package org.ternlang.core.link;

import java.util.concurrent.Future;

import org.ternlang.core.Entity;
import org.ternlang.core.module.Module;
import org.ternlang.core.type.Type;

public interface ImportManager {
   void addImport(String prefix);
   void addImport(String type, String alias);
   void addImport(Type type, String alias);
   void addImports(Module module);
   Future<Entity> getImport(String name);
}