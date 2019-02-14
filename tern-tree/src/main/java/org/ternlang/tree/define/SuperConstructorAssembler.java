package org.ternlang.tree.define;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeBody;
import org.ternlang.core.type.TypeState;
import org.ternlang.tree.ArgumentList;

public class SuperConstructorAssembler {

   private final ArgumentList arguments;

   public SuperConstructorAssembler(ArgumentList arguments){  
      this.arguments = arguments;
   } 

   public TypeState assemble(TypeBody body, Type type, Scope scope) throws Exception { 
      return new SuperState(arguments, type);
   }
}
