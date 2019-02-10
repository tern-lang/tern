package tern.tree.construct;

import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.tree.ArgumentList;

public class ConstructArgumentList {

   private final ArgumentList list;
   private final Object[] objects;
   private final Type[] types;
   
   public ConstructArgumentList(ArgumentList list) {
      this.objects = new Object[]{};
      this.types = new Type[]{};
      this.list = list;
   }

   public void define(Scope scope) throws Exception {
      if(list != null) {
         list.define(scope);
      }
   }
   
   public Type[] compile(Scope scope, Type type) throws Exception {
      Class real = type.getType();

      if(list != null) {
         if(real == null) {
            return list.compile(scope, type);
         }
         return list.compile(scope);
      }
      if(real == null) {
         return new Type[]{type};
      }
      return types;
   }
   
   public Object[] create(Scope scope, Type type) throws Exception {
      Class real = type.getType();
      
      if(list != null) {
         if(real == null) {
            return list.create(scope, type);
         }
         return list.create(scope);
      }
      if(real == null) {
         return new Object[]{type};
      }
      return objects;
   }
   
}
