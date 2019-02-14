package org.ternlang.core.link;

import java.util.List;

import org.ternlang.common.BitSet;
import org.ternlang.core.NameChecker;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public class ImplicitImportLoader {
   
   private final NameChecker checker;

   public ImplicitImportLoader() {
      this.checker = new NameChecker(true);
   }
   
   public boolean loadImports(Scope scope, String name) throws Exception {
      if(checker.isEntity(name)) {
         Module module = scope.getModule();
         ImportManager manager = module.getManager();
         ScopeState state = scope.getState();
         Value value = state.getValue(name);         
         
         if(value == null) {
            return manager.getImport(name) != null;
         }                   
         return true;
      }
      return false;
   }

   public boolean loadImports(Scope scope, List<String> names) throws Exception {
      Type type = scope.getType();
      int count = names.size();
      
      if(count > 0) {
         BitSet done = new BitSet(count);
         
         while(type != null) {
            Scope inner = type.getScope();            
            
            if(loadImports(inner, names, done)) {
               return true;
            }            
            type = type.getOuter();
         }
         return loadImports(scope, names, done);               
      }
      return true;
   }
   
   private boolean loadImports(Scope scope, List<String> names, BitSet done) throws Exception {
      int count = names.size();      
      int remain = count;
      
      for(int i = 0; i < count; i++) {         
         if(!done.get(i)) {
            String name = names.get(i);
            
            if(loadImports(scope, name)) {
               done.set(i);
               remain--;
            }
         } else {
            remain--;
         }
      }
      return remain <= 0;
   }
}
