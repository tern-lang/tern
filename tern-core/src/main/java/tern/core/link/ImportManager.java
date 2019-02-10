package tern.core.link;

import java.util.concurrent.Future;

import tern.core.Entity;
import tern.core.module.Module;
import tern.core.type.Type;

public interface ImportManager {
   void addImport(String prefix);
   void addImport(String type, String alias);
   void addImport(Type type, String alias);
   void addImports(Module module);
   Future<Entity> getImport(String name);
}