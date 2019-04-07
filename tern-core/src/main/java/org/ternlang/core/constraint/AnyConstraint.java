package org.ternlang.core.constraint;

import static org.ternlang.core.Reserved.ANY_TYPE;
import static org.ternlang.core.Reserved.DEFAULT_MODULE;

import java.util.concurrent.atomic.AtomicReference;

import org.ternlang.core.Context;
import org.ternlang.core.NameFormatter;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeLoader;

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
         String name = formatter.formatFullName(DEFAULT_MODULE, ANY_TYPE);
         Type base = loader.loadType(name);
         
         reference.set(base); // any is very last
         
         return base;
      }
      return type;
   }   
   
   @Override
   public String toString() {
      return String.format("%s.%s",  DEFAULT_MODULE, ANY_TYPE);
   }
}
