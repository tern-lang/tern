package org.ternlang.core.type.index;

import static org.ternlang.core.ModifierType.ABSTRACT;
import static org.ternlang.core.ModifierType.OVERRIDE;

import java.util.List;

import org.ternlang.core.Context;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.AccessorProperty;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Parameter;
import org.ternlang.core.function.Signature;
import org.ternlang.core.function.bind.FunctionBinder;
import org.ternlang.core.function.bind.FunctionMatcher;
import org.ternlang.core.module.Module;
import org.ternlang.core.property.Property;
import org.ternlang.core.type.Type;

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