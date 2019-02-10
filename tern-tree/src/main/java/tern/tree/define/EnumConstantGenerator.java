package tern.tree.define;

import static tern.core.Reserved.ENUM_NAME;
import static tern.core.Reserved.ENUM_ORDINAL;

import tern.core.scope.ScopeState;
import tern.core.scope.instance.Instance;
import tern.core.type.TypeState;
import tern.core.type.Type;
import tern.core.variable.Value;

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