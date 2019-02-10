package tern.tree.define;

import static tern.core.Reserved.TYPE_CLASS;
import static tern.core.variable.Constant.TYPE;

import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.type.Type;
import tern.core.type.TypeBody;
import tern.core.variable.Value;

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