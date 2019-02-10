package tern.tree.function;

import static java.util.Collections.EMPTY_LIST;
import static tern.core.ModifierType.PUBLIC;
import static tern.core.Reserved.DEFAULT_PARAMETER;
import static tern.core.constraint.Constraint.NONE;
import static tern.core.function.Origin.SYSTEM;

import java.util.ArrayList;
import java.util.List;

import tern.core.function.Function;
import tern.core.function.FunctionSignature;
import tern.core.function.FunctionType;
import tern.core.function.Invocation;
import tern.core.function.InvocationFunction;
import tern.core.function.Parameter;
import tern.core.function.Signature;
import tern.core.function.bind.FunctionMatcher;
import tern.core.module.Module;
import tern.core.type.Type;
import tern.core.variable.Value;

public class FunctionHandleBuilder {
   
   private final FunctionMatcher matcher;
   private final Parameter parameter;
   private final boolean constructor;
   
   public FunctionHandleBuilder(FunctionMatcher matcher, boolean constructor) {
      this.parameter = new Parameter(DEFAULT_PARAMETER, NONE, 0, false, true);
      this.constructor = constructor;
      this.matcher = matcher;
   }
   
   public Function create(Module module, Value value, String method) throws Exception {
      List<Parameter> parameters = new ArrayList<Parameter>();
      Signature signature = new FunctionSignature(parameters, EMPTY_LIST, module, null, SYSTEM, true, true);
      Invocation invocation = new FunctionHandleInvocation(matcher, module, value, constructor);
      Type type = new FunctionType(signature, module, null);
      
      parameters.add(parameter);
      
      return new InvocationFunction(signature, invocation, type, null, method, PUBLIC.mask);
   }
}