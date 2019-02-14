package org.ternlang.core.convert;

import static org.ternlang.core.convert.Score.EXACT;

import org.ternlang.core.ModifierType;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.error.InternalArgumentException;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;

public class ObjectConverter extends ConstraintConverter {

   private final TypeExtractor extractor;
   private final AliasResolver resolver;
   private final ProxyWrapper wrapper;
   private final CastChecker checker;
   private final Type constraint;
   
   public ObjectConverter(TypeExtractor extractor, CastChecker checker, ProxyWrapper wrapper, Type constraint) {
      this.resolver = new AliasResolver();
      this.constraint = constraint;
      this.extractor = extractor;
      this.wrapper = wrapper;
      this.checker = checker;
   }
   
   @Override
   public Score score(Type actual) throws Exception {
      if(actual != null) {
         Type type = resolver.resolve(actual);

         if(type != constraint) {
            return checker.toType(type, constraint);
         }         
      }
      return EXACT;
   }

   @Override
   public Score score(Object object) throws Exception { // argument type
      if(object != null) {
         Type match = extractor.getType(object);
      
         if(!match.equals(constraint)) {
            return checker.toType(match, constraint, object);           
         }
      }
      return EXACT;
   }
   
   @Override
   public Object assign(Object object) throws Exception {
      if(object != null) {
         Type match = extractor.getType(object);
         
         if(match != constraint) {
            Score score = checker.toType(match, constraint, object);
            int modifiers = match.getModifiers();            

            if(score.isInvalid()) {
               Class require = constraint.getType();
               
               if(require == null) {
                  throw new InternalArgumentException("Conversion from " + match + " to '" + constraint + "' is not possible");
               }
               return convert(object);
            }            
            if(ModifierType.isFunction(modifiers)) {
               return convert(object);
            }
         }
      }
      return object;
   }

   @Override
   public Object convert(Object object) throws Exception {
      if(object != null) {
         Class require = constraint.getType();
         
         if(require != null) {
            return wrapper.toProxy(object, require);
         }
      }
      return object;
   }
}