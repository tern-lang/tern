package org.ternlang.tree.define;

import static org.ternlang.core.Reserved.ENUM_NAME;
import static org.ternlang.core.Reserved.ENUM_ORDINAL;

import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.scope.instance.Instance;
import org.ternlang.core.type.TypeState;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public class EnumConstantGenerator extends TypeState {
   
   private final String name;
   private final int index;
   
   public EnumConstantGenerator(String name, int index) {
      this.index = index;
      this.name = name;
   }

   public void generate(Instance instance, Type type) throws Exception {
      ScopeState state = instance.getState();
      Value key = Value.getConstant(name);
      Value ordinal = Value.getConstant(index);
      
      state.addValue(ENUM_NAME, key);
      state.addValue(ENUM_ORDINAL, ordinal);
   }
}