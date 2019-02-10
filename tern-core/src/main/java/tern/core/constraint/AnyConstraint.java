package tern.core.constraint;

import static tern.core.Reserved.ANY_TYPE;
import static tern.core.Reserved.DEFAULT_PACKAGE;

import java.util.concurrent.atomic.AtomicReference;

import tern.core.Context;
import tern.core.NameFormatter;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeLoader;

public class AnyConstraint extends Constraint {

   private final AtomicReference<Type> reference;
   private final NameFormatter formatter;
   
   public AnyConstraint(){
      this.reference = new AtomicReference<Type>();
      this.formatter = new NameFormatter();
   }

   @Override
   public Type getType(Scope scope) {
      Type type  = reference.get();
      
      if(type == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeLoader loader = context.getLoader();
         String name = formatter.formatFullName(DEFAULT_PACKAGE, ANY_TYPE);
         Type base = loader.loadType(name);
         
         reference.set(base); // any is very last
         
         return base;
      }
      return type;
   }   
   
   @Override
   public String toString() {
      return String.format("%s.%s",  DEFAULT_PACKAGE, ANY_TYPE);
   }
}
