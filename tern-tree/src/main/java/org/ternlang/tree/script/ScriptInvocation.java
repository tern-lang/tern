package org.ternlang.tree.script;

import static org.ternlang.core.scope.extract.ScopePolicy.EXECUTE_SCRIPT;

import org.ternlang.core.function.Invocation;
import org.ternlang.core.function.InvocationBuilder;
import org.ternlang.core.function.Signature;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.extract.ScopePolicyExtractor;

public class ScriptInvocation implements Invocation<Object> {

   private final ScopePolicyExtractor extractor;
   private final InvocationBuilder builder;
   
   public ScriptInvocation(InvocationBuilder builder, Signature signature) {
      this.extractor = new ScopePolicyExtractor(EXECUTE_SCRIPT);
      this.builder = builder;
   }
   
   @Override
   public Object invoke(Scope scope, Object object, Object... list) throws Exception {
      Scope capture = extractor.extract(scope);
      Invocation invocation = builder.create(capture);

      return invocation.invoke(capture, object, list);
   }
}