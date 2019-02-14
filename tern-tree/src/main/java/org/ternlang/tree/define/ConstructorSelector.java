package org.ternlang.tree.define;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.instance.SuperExtractor;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeState;
import org.ternlang.core.type.TypeBody;
import org.ternlang.core.type.TypePart;

public class ConstructorSelector {

   private final SuperConstructor constructor;
   private final SuperExtractor extractor;
   private final TypePart part;

   public ConstructorSelector(TypePart part){  
      this.constructor = new SuperConstructor();
      this.extractor = new SuperExtractor();
      this.part = part;
   } 

   public TypeState define(TypeBody body, Type type, Scope scope) throws Exception {
      Type base = extractor.extractor(type);
      
      if(part != null){
         return part.define(body, type, scope);              
      }
      if(base != null) {
         return constructor.define(body, type, scope);
      }
      return new PrimitiveState(); 
   }
}