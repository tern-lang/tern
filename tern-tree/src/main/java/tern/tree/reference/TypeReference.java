package tern.tree.reference;

import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.core.variable.Value;

public class TypeReference extends TypeNavigation {

   private TypeNavigation[] list;
   private TypeNavigation root;
   private Value type;
   
   public TypeReference(TypeNavigation root, TypeNavigation... list) {
      this.root = root;
      this.list = list;
   }
   
   @Override
   public String qualify(Scope scope, String left) throws Exception {
      String name = root.qualify(scope, left);
      
      for(int i = 0; i < list.length; i++) {
         name = list[i].qualify(scope, name);
      }
      return name;
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      if(type == null) {
         Value result = root.evaluate(scope, left);
         
         for(int i = 0; i < list.length; i++) {
            Object next = result.getValue();
            
            if(next == null) {
               throw new InternalStateException("Could not determine type");
            }
            result = list[i].evaluate(scope, result);
         }
         type = result;
      }
      return type;
   }
}