package org.ternlang.tree.function;

import java.util.List;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Parameter;
import org.ternlang.core.function.Signature;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.index.Address;
import org.ternlang.core.scope.index.ScopeIndex;

public class ParameterExtractor {

   private ParameterCollector collector;
   private Signature signature;
   
   public ParameterExtractor(Signature signature) {
      this(signature, 0);
   }
   
   public ParameterExtractor(Signature signature, int modifiers) {
      this.collector = new ParameterCollector(signature, modifiers);
      this.signature = signature;
   }
   
   public void define(Scope scope) throws Exception {
      List<Parameter> parameters = signature.getParameters();
      ScopeIndex index = scope.getIndex();
      int count = parameters.size();
 
      for(int i = 0; i < count; i++) {
         Parameter parameter = parameters.get(i);
         Address address = parameter.getAddress();
         String name = parameter.getName();
         
         if(!index.contains(name)) {
            Address created = index.index(name);
            int actual = created.getOffset();
            int require = address.getOffset();
            
            if(actual != require) {
               throw new InternalStateException("Parameter '" + name + "' has an invalid address");
            }
         }
      }
   }

   public Scope extract(Scope scope, Object[] arguments) throws Exception {
      List<ParameterAppender> parameters = collector.collect();
      Scope inner = scope.getStack();
      int count = parameters.size();

      for(int i = 0; i < count; i++) {
         ParameterAppender appender = parameters.get(i);
         
         if(appender != null) {
            appender.append(inner, arguments);
         }
      }
      return inner;
   }
}