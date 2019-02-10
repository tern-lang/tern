package tern.core.link;

import java.util.Set;

public class DefaultImport {

   private final Set<String> imports;
   private final Set<String> modules;
   private final String alias;
   private final boolean include;
   
   public DefaultImport(Set<String> imports, Set<String> modules, String alias) {
      this(imports, modules, alias, false);
   }
   
   public DefaultImport(Set<String> imports, Set<String> modules, String alias, boolean include) {
      this.imports = imports;
      this.modules = modules;
      this.alias = alias;
      this.include = include;
   }
   
   public Set<String> getImports() {
      return imports;
   }
   
   public Set<String> getModules(){
      return modules;
   }
   
   public String getAlias(){
      return alias;
   }
   
   public boolean isInclude(){
      return include;
   }
}