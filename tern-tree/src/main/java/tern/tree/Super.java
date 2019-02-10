package tern.tree;

import static tern.core.ModifierType.CONSTANT;

import tern.core.Context;
import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.function.Function;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.scope.ScopeBinder;
import tern.core.scope.instance.Instance;
import tern.core.stack.ThreadStack;
import tern.core.type.Type;
import tern.core.variable.Value;
import tern.parse.StringToken;

public class Super extends Evaluation {

   private final ScopeBinder binder;
   
   public Super(StringToken token) {
      this.binder = new ScopeBinder();
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Scope instance = binder.bind(scope, scope);
      Type type = instance.getType();
      
      return Constraint.getConstraint(type, CONSTANT.mask);
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ThreadStack stack = context.getStack();
      Function function = stack.current(); // we can determine the function type
      
      if(function == null) {
         throw new InternalStateException("No enclosing function for 'super' reference");
      }
      Value value = scope.getThis();
      
      if(value == null) {
         throw new InternalStateException("No enclosing type for 'super' reference");
      }
      Instance instance = value.getValue();
      Instance base = resolve(instance, function);
      
      if(base == null) {
         throw new InternalStateException("Illegal reference to 'super'"); // closure?
      }
      return Value.getTransient(base);
   }  
   
   private Instance resolve(Instance instance, Function function) {
      Type source = function.getSource();
      Instance next = instance;
      
      while(next != null) {
         Type actual = next.getHandle();
         
         if(source == actual){
            return next.getSuper(); // return the object instance for super
         }
         next = next.getSuper(); 
      }
      return null;
   }

}