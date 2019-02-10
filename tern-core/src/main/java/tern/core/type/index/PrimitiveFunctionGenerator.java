package tern.core.type.index;

import static java.util.Collections.EMPTY_LIST;
import static tern.core.ModifierType.PUBLIC;
import static tern.core.ModifierType.STATIC;
import static tern.core.Reserved.TYPE_CONSTRUCTOR;
import static tern.core.constraint.Constraint.NONE;
import static tern.core.function.Origin.DEFAULT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.function.Function;
import tern.core.function.FunctionSignature;
import tern.core.function.Invocation;
import tern.core.function.InvocationFunction;
import tern.core.function.Parameter;
import tern.core.function.ParameterBuilder;
import tern.core.function.Signature;
import tern.core.module.Module;
import tern.core.type.Type;

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