package org.ternlang.tree;

import java.util.Set;

import org.ternlang.core.Context;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;

public class ModifierAccessVerifier {
   
   public ModifierAccessVerifier() {
      super();
   }

   public boolean isAccessible(Scope scope, Type owner) {
      Type caller = scope.getType();
      
      if(caller != null) {
         return isAccessible(caller, owner);
      }
      return false;
   }
   
   public boolean isAccessible(Type caller, Type owner) {
      if(caller != null && owner != null) {
         if(isSuper(caller, owner)) {
            return true;
         }
         if(isCompatible(caller, owner)) {
            if(isEnclosing(caller, owner)) {
               return true;
            }
            if(isEnclosing(owner, caller)) {
               return true;
            }
         }
      }
      return false;
   }
   
   private boolean isSuper(Type caller, Type owner) {
      if(caller != owner) {
         Module module = caller.getModule();
         Context context = module.getContext();
         TypeExtractor extractor = context.getExtractor();
         Set<Type> types = extractor.getTypes(caller); // what is this scope
         
         return types.contains(owner);
      }
      return true;
   }
   
   private boolean isCompatible(Type caller, Type owner) { // same module
      Module actual = caller.getModule();
      Module require = owner.getModule(); 
      
      return actual == require;
   }
   
   private boolean isEnclosing(Type parent, Type child) {
      String outer = parent.getName();
      String inner = child.getName();
      
      if(inner.startsWith(outer)) {
         int length = outer.length();
         int index = inner.indexOf('$', length);
         
         return index == length;
      }
      return false;
   }
}
