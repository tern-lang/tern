package org.ternlang.core.type.index;

import static java.util.Collections.EMPTY_LIST;
import static org.ternlang.core.ModifierType.PUBLIC;
import static org.ternlang.core.ModifierType.STATIC;
import static org.ternlang.core.Reserved.TYPE_CONSTRUCTOR;
import static org.ternlang.core.constraint.Constraint.NONE;
import static org.ternlang.core.function.Origin.DEFAULT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.FunctionSignature;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.InvocationFunction;
import org.ternlang.core.function.Parameter;
import org.ternlang.core.function.ParameterBuilder;
import org.ternlang.core.function.Signature;
import org.ternlang.core.module.Module;
import org.ternlang.core.type.Type;

public class PrimitiveFunctionGenerator {
   
   private final PrimitiveFunctionAccessor accessor;
   private final ParameterBuilder builder;
   
   public PrimitiveFunctionGenerator() {
      this.accessor = new PrimitiveFunctionAccessor();
      this.builder = new ParameterBuilder();
   }

   public Function generate(Type type, Constraint returns, String name, Class invoke, Class... types) {
      Module module = type.getModule();
      Invocation invocation = accessor.create(invoke);
      
      if(invocation == null) {
         throw new InternalStateException("Could not create invocation for " + invoke);
      }
      List<Parameter> parameters = new ArrayList<Parameter>();
      Signature signature = new FunctionSignature(parameters, EMPTY_LIST, module, null, DEFAULT, true);
      
      for(int i = 0; i < types.length; i++){
         Class require = types[i];
         Constraint constraint = Constraint.getConstraint(require);
         Parameter parameter = null;
         
         if(require == Object.class) { // avoid proxy wrapping
            parameter = builder.create(NONE, i);
         } else {
            parameter = builder.create(constraint, i);
         }
         parameters.add(parameter);
      }            
      if(name.equals(TYPE_CONSTRUCTOR)) {
         return new InvocationFunction(signature, invocation, type, returns, name, STATIC.mask | PUBLIC.mask);
      }
      return new InvocationFunction(signature, invocation, type, returns, name, PUBLIC.mask);
   }
}