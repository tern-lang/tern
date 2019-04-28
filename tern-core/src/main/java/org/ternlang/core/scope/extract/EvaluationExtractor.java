package org.ternlang.core.scope.extract;

import static org.ternlang.core.scope.extract.ScopePolicy.COMPILE_EVALUATE;

import java.util.List;

import org.ternlang.core.Context;
import org.ternlang.core.ModifierType;
import org.ternlang.core.function.Function;
import org.ternlang.core.module.Module;
import org.ternlang.core.property.Property;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.stack.StackTrace;
import org.ternlang.core.stack.ThreadStack;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;

public class EvaluationExtractor implements ScopeExtractor{
   
   private final ScopePolicyExtractor extractor;
   
   public EvaluationExtractor() {
      this.extractor = new ScopePolicyExtractor(COMPILE_EVALUATE);
   }
   
   @Override
   public Scope extract(Scope original) {
      Module module = original.getModule();
      Context context = module.getContext();
      ThreadStack stack = context.getStack();
      StackTrace trace = stack.trace();
      Function function = trace.current(); // we can determine the function type    
      
      return extract(original, function);
   }
   
   public Scope extract(Scope original, Function function) {
      Scope extracted = extractor.extract(original);      
      
      if(function != null) {
         Type source = function.getSource();
         ScopeState state = original.getState();
         ScopeState update = extracted.getState();
         List<Property> properties = source.getProperties();
         
         for(Property property : properties) {
            int modifiers = property.getModifiers();
            
            if(ModifierType.isPrivate(modifiers)) {
               String name = property.getName();
               String alias = property.getAlias();
               Value value = state.getValue(alias);
               Value current = update.getValue(name);
               
               if(current == null) {
                  update.addValue(name, value);
               }
            }
         }
      }
      return extracted;
   } 
}
