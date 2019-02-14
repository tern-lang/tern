package org.ternlang.tree.define;

import static org.ternlang.core.Reserved.TYPE_CLASS;
import static org.ternlang.core.variable.Constant.TYPE;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeBody;
import org.ternlang.core.variable.Value;

public class ClassPropertyGenerator {
   
   private final ConstantPropertyBuilder builder;
   
   public ClassPropertyGenerator() {
      this.builder = new ConstantPropertyBuilder();
   }

   public void generate(TypeBody body, Scope scope, Type type) throws Exception {
      Value value = Value.getConstant(type, TYPE);
      Scope outer = type.getScope();
      ScopeState state = outer.getState();
      
      builder.createStaticProperty(body, TYPE_CLASS, type, TYPE);
      state.addValue(TYPE_CLASS, value);
   }
}