package tern.tree.script;

import static tern.core.scope.extract.ScopePolicy.EXECUTE_SCRIPT;

import tern.core.function.Invocation;
import tern.core.function.InvocationBuilder;
import tern.core.function.Signature;
import tern.core.scope.Scope;
import tern.core.scope.extract.ScopePolicyExtractor;

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