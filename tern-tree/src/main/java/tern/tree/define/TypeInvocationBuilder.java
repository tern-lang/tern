package tern.tree.define;

import tern.core.function.Invocation;
import tern.core.function.InvocationBuilder;
import tern.core.function.Signature;
import tern.core.function.SignatureAligner;
import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.scope.instance.Instance;
import tern.core.type.Type;
import tern.core.type.TypeState;
import tern.tree.function.ParameterExtractor;

public class TypeInvocationBuilder implements InvocationBuilder {
   
   private ParameterExtractor extractor;
   private SignatureAligner aligner;
   private Invocation invocation;
   private TypeState state;
   private Type type;

   public TypeInvocationBuilder(TypeState state, Signature signature, Type type) {
      this.extractor = new ParameterExtractor(signature); // this seems wrong!
      this.aligner = new SignatureAligner(signature);
      this.state = state;
      this.type = type;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      extractor.define(scope); // count parameters
      state.define(scope, type); // start counting from here 
   }
   
   @Override
   public void compile(Scope scope) throws Exception {
      state.compile(scope, type);
   }

   @Override
   public Invocation create(Scope scope) throws Exception {
      if(invocation == null) {
         try {
            invocation = new TypeStateInvocation(state);
         } finally {
            state.allocate(scope, type);
         }
      }
      return invocation;
   }

   private class TypeStateInvocation implements Invocation<Instance> {
      
      private final TypeState state;
      
      public TypeStateInvocation(TypeState state) {
         this.state = state;
      }

      @Override
      public Object invoke(Scope scope, Instance object, Object... list) throws Exception {
         Type real = (Type)list[0];
         Object[] arguments = aligner.align(list);
         Scope inner = extractor.extract(object, arguments);
         Result result = state.execute(inner, real);
         
         return result.getValue();
      }
   }
}