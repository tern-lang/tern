package tern.tree.define;

import static tern.core.Reserved.ENUM_VALUES;

import java.util.List;

import tern.core.Context;
import tern.core.convert.proxy.ProxyWrapper;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.scope.instance.Instance;
import tern.core.type.Type;
import tern.core.variable.Value;
import tern.tree.ArgumentList;

public class EnumInstance extends StaticBlock {
   
   private final EnumConstantGenerator generator;
   private final EnumConstructorBinder binder;
   private final String name;
   
   public EnumInstance(String name, ArgumentList arguments, int index) {
      this.generator = new EnumConstantGenerator(name, index);
      this.binder = new EnumConstructorBinder(arguments);
      this.name = name;
   }

   @Override
   protected void allocate(Scope scope) throws Exception {
      Type type = scope.getType();
      ScopeState state = scope.getState();
      Instance instance = binder.bind(scope, type);
      Module module = scope.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      Object object = wrapper.toProxy(instance);
      Value value = Value.getConstant(instance);      
      Value values = state.getValue(ENUM_VALUES);
      List list = values.getValue();
      
      generator.generate(instance, type);
      state.addValue(name, value);
      list.add(object);
   }
}