package org.ternlang.core.convert;

import static org.ternlang.core.convert.Score.EXACT;

import org.ternlang.core.ModifierType;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.error.InternalArgumentException;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;

public class FunctionConverter extends ConstraintConverter {
   
   private final CastChecker checker;
   private final TypeExtractor extractor;
   private final ProxyWrapper wrapper;
   private final Type constraint;
   
   public FunctionConverter(TypeExtractor extractor, CastChecker checker, ProxyWrapper wrapper, Type constraint) {
      this.constraint = constraint;
      this.extractor = extractor;
      this.wrapper = wrapper;
      this.checker = checker;
   }
   
   @Override
   public Score score(Type actual) throws Exception {
      if(actual != null) {
         return checker.toFunction(actual, constraint);
      }
      return EXACT;
   }

   @Override
   public Score score(Object object) throws Exception { // argument type
      if(object != null) {
         Type match = extractor.getType(object);
         int modifiers = match.getModifiers();
         
         if(ModifierType.isProxy(modifiers)) {
            Object real = wrapper.fromProxy(object);
            
            if(real != object) {
               return score(real);
            }
         }
         return checker.toFunction(match, constraint, object);
      }
      return EXACT;
   }
   
   @Override
   public Object assign(Object object) throws Exception {
      if(object != null) {
         Type match = extractor.getType(object);
         int modifiers = match.getModifiers();
         
         if(ModifierType.isProxy(modifiers)) {
            Object real = wrapper.fromProxy(object);
            
            if(real != object) {
               return assign(real);
            }
         }
         Score score = checker.toFunction(match, constraint, object);
         
         if(score.isInvalid()) {
            throw new InternalArgumentException("Conversion from " + match + " to '" + constraint + "' is not possible");
         }
      }
      return object;
   }

   @Override
   public Object convert(Object object) throws Exception {
      if(object != null) {
         Type match = extractor.getType(object);
         int modifiers = match.getModifiers();
         
         if(ModifierType.isProxy(modifiers)) {
            Object real = wrapper.fromProxy(object);
            
            if(real != object) {
               return assign(real);
            }
         }
         Score score = checker.toFunction(match, constraint, object);
         
         if(score.isInvalid()) {
            throw new InternalArgumentException("Conversion from " + match + " to '" + constraint + "' is not possible");
         }
      }
      return object;
   }
}