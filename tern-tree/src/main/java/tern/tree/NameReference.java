package tern.tree;

import tern.core.Evaluation;
import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.core.variable.Value;

public class NameReference {

   private Evaluation identifier;
   private String name;
   
   public NameReference(Evaluation identifier) {
      this.identifier = identifier;
   }
   
   public String getName(Scope scope) throws Exception {
      if(name == null) {
         Value value = identifier.evaluate(scope, null);
         String identifier = value.getString();
         
         if(identifier == null) {
            throw new InternalStateException("Name evaluated to null");
         }
         name = identifier;
      }
      return name;
      
   }
}