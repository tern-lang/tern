package tern.tree.define;

import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeBody;
import tern.core.type.TypeState;
import tern.tree.ArgumentList;

public class SuperConstructorAssembler {

   private final ArgumentList arguments;

   public SuperConstructorAssembler(ArgumentList arguments){  
      this.arguments = arguments;
   } 

   public TypeState assemble(TypeBody body, Type type, Scope scope) throws Exception { 
      return new SuperState(arguments, type);
   }
}
