package tern.tree.define;

import tern.core.scope.Scope;
import tern.core.scope.instance.SuperExtractor;
import tern.core.type.Type;
import tern.core.type.TypeState;
import tern.core.type.TypeBody;
import tern.core.type.TypePart;

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