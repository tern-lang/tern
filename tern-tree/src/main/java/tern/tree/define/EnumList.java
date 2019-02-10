package tern.tree.define;

import static tern.core.type.Category.STATIC;

import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeBody;
import tern.core.type.TypePart;
import tern.core.type.TypeState;

public class EnumList extends TypePart {
   
   private final EnumValue[] values;
   
   public EnumList(EnumValue... values){
      this.values = values;
   }

   @Override
   public TypeState define(TypeBody body, Type type, Scope scope) throws Exception {
      TypeStateCollector collector = new TypeStateCollector(STATIC);
      int index = 0;
      
      for(EnumValue value : values) {
         TypeState state = value.define(body, type, index++);
         
         if(state != null) {
            collector.update(state);
         }
      }
      return collector;
   }
}