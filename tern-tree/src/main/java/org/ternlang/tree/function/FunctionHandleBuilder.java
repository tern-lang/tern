package org.ternlang.tree.function;

import static java.util.Collections.EMPTY_LIST;
import static org.ternlang.core.ModifierType.PUBLIC;
import static org.ternlang.core.Reserved.DEFAULT_PARAMETER;
import static org.ternlang.core.constraint.Constraint.NONE;
import static org.ternlang.core.function.Origin.SYSTEM;

import java.util.ArrayList;
import java.util.List;

import org.ternlang.core.function.Function;
import org.ternlang.core.function.FunctionSignature;
import org.ternlang.core.function.FunctionType;
import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.InvocationFunction;
import org.ternlang.core.function.Parameter;
import org.ternlang.core.function.Signature;
import org.ternlang.core.function.bind.FunctionMatcher;
import org.ternlang.core.module.Module;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

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