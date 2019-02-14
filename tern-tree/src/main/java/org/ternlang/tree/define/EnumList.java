package org.ternlang.tree.define;

import static org.ternlang.core.type.Category.STATIC;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeBody;
import org.ternlang.core.type.TypePart;
import org.ternlang.core.type.TypeState;

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