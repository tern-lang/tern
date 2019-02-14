package org.ternlang.tree.define;

import static org.ternlang.core.Reserved.ENUM_NAME;
import static org.ternlang.core.Reserved.ENUM_ORDINAL;
import static org.ternlang.core.Reserved.ENUM_VALUES;
import static org.ternlang.core.Reserved.TYPE_CLASS;
import static org.ternlang.core.variable.Constant.INTEGER;
import static org.ternlang.core.variable.Constant.LIST;
import static org.ternlang.core.variable.Constant.STRING;
import static org.ternlang.core.variable.Constant.TYPE;

import java.util.List;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeBody;
import org.ternlang.core.variable.Value;

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