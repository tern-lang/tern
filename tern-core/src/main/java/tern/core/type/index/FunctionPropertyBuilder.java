package tern.core.type.index;

import static tern.core.ModifierType.ABSTRACT;
import static tern.core.ModifierType.OVERRIDE;

import java.util.List;

import tern.core.Context;
import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.function.AccessorProperty;
import tern.core.function.Function;
import tern.core.function.Parameter;
import tern.core.function.Signature;
import tern.core.function.bind.FunctionBinder;
import tern.core.function.bind.FunctionMatcher;
import tern.core.module.Module;
import tern.core.property.Property;
import tern.core.type.Type;

public class FunctionPropertyBuilder {

   private static final int MODIFIERS = OVERRIDE.mask | ABSTRACT.mask;

   public FunctionPropertyBuilder() {
      super();
   }
   
   public Property create(Function function, String name) throws Exception {
      String identifier = function.getName();
      Type type = function.getSource();
      Module module = type.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      FunctionMatcher matcher = binder.bind(identifier);
      Signature signature = function.getSignature();
      List<Parameter> names = signature.getParameters();
      Constraint constraint = function.getConstraint();
      int modifiers = function.getModifiers(); 
      int count = names.size();
      
      if(count > 0) {
         throw new InternalStateException("Function '" + function + "' is not a valid property");
      }
      FunctionAccessor accessor = new FunctionAccessor(matcher, module, identifier);
      AccessorProperty property = new AccessorProperty(name, name, type, constraint, accessor, modifiers & ~MODIFIERS);

      return property;
   }
}