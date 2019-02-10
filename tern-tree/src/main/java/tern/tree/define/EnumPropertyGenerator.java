package tern.tree.define;

import static tern.core.Reserved.ENUM_NAME;
import static tern.core.Reserved.ENUM_ORDINAL;
import static tern.core.Reserved.ENUM_VALUES;
import static tern.core.Reserved.TYPE_CLASS;
import static tern.core.variable.Constant.INTEGER;
import static tern.core.variable.Constant.LIST;
import static tern.core.variable.Constant.STRING;
import static tern.core.variable.Constant.TYPE;

import java.util.List;

import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.type.Type;
import tern.core.type.TypeBody;
import tern.core.variable.Value;

public class EnumPropertyGenerator {
   
   protected final ConstantPropertyBuilder builder;
   
   public EnumPropertyGenerator() {
      this.builder = new ConstantPropertyBuilder();
   }
   
   public void generate(TypeBody body, Scope scope, Type type, List values) throws Exception {
      Value value = Value.getConstant(type, TYPE);
      Value list = Value.getConstant(values, LIST);
      Scope outer = type.getScope();
      ScopeState state = outer.getState();

      builder.createStaticProperty(body, TYPE_CLASS, type, TYPE);
      builder.createStaticProperty(body, ENUM_VALUES, type, LIST);     
      builder.createInstanceProperty(ENUM_NAME, type, STRING); // might declare name as property many times
      builder.createInstanceProperty(ENUM_ORDINAL, type, INTEGER);
      state.addValue(TYPE_CLASS, value);
      state.addValue(ENUM_VALUES, list);
   }
}