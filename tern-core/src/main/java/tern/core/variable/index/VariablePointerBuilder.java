package tern.core.variable.index;

import java.util.Collection;
import java.util.Map;

import tern.core.ModifierType;
import tern.core.constraint.Constraint;
import tern.core.convert.proxy.ProxyWrapper;
import tern.core.function.Function;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.scope.instance.Instance;
import tern.core.type.Type;
import tern.core.variable.bind.VariableFinder;

public class VariablePointerBuilder {

   private final VariableFinder finder;
   private final String name;
   
   public VariablePointerBuilder(ProxyWrapper wrapper, String name) {
      this.finder = new VariableFinder(wrapper);
      this.name = name;
   }

   public VariablePointer create(Scope scope) throws Exception {
      if(Instance.class.isInstance(scope)) {
         return new TypeLocalPointer(finder, name);
      }
      return new LocalPointer(finder, name);
   }
   
   public VariablePointer create(Scope scope, Object left) throws Exception {
      Class type = left.getClass();
      
      if(Module.class.isInstance(left)) {
         return new ModulePointer(finder, name);
      }
      if(Map.class.isInstance(left)) {
         return new MapPointer(finder, name);
      }         
      if(Type.class.isInstance(left)) {
         return new TypeStaticPointer(finder, name); // could be either static or instance
      }
      if(Collection.class.isInstance(left)) {
         return new CollectionPointer(finder, name);
      }
      if(Function.class.isInstance(left)) {
         return new ClosurePointer(finder, name);
      }
      if(type.isArray()) {
         return new ArrayPointer(finder, name);
      }
      return new TypeInstancePointer(finder, name);
   }
   
   public VariablePointer create(Scope scope, Constraint left) throws Exception {
      Type type = left.getType(scope);
      
      if(type != null) {
         Class real = type.getType();
         int modifiers = type.getModifiers();
         
         if(left.isModule()) {
            return new ModulePointer(finder, name);
         }
         if(left.isClass()) {
            return new TypeStaticPointer(finder, name);
         }
         if(ModifierType.isFunction(modifiers)) {
            return new ClosurePointer(finder, name);
         }
         if(ModifierType.isArray(modifiers)) {
            return new ArrayPointer(finder, name);
         }
         if(real != null) {
            if(Map.class.isAssignableFrom(real)) {
               return new MapPointer(finder, name);
            }         
            if(Collection.class.isAssignableFrom(real)) {
               return new CollectionPointer(finder, name);
            }
         }
      }
      return new TypeInstancePointer(finder, name);
   }
}