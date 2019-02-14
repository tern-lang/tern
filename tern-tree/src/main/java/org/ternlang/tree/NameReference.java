package org.ternlang.tree;

import org.ternlang.core.Evaluation;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

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