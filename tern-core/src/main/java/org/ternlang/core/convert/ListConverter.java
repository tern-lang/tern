package org.ternlang.core.convert;

import static org.ternlang.core.convert.Score.EXACT;
import static org.ternlang.core.convert.Score.POSSIBLE;

import org.ternlang.core.array.ArrayBuilder;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;

public class ListConverter extends ConstraintConverter {
   
   private final ObjectConverter converter;
   private final ArrayBuilder builder;
   
   public ListConverter(TypeExtractor extractor, CastChecker checker, ProxyWrapper wrapper, Type constraint) {
      this.converter = new ObjectConverter(extractor, checker, wrapper, constraint);
      this.builder = new ArrayBuilder();  
   }
   
   @Override
   public Score score(Type actual) throws Exception {
      if(actual != null) {
         Type entry = actual.getEntry();
         
         if(entry == null) {
            return converter.score(actual);
         }
         return POSSIBLE;                  
      }
      return EXACT;
   }

   @Override
   public Score score(Object object) throws Exception { // argument type
      if(object != null) {
         Class type = object.getClass();
         
         if(!type.isArray()) {
            return converter.score(object);
         }
         return POSSIBLE;            
      }
      return EXACT;
   }
   
   @Override
   public Object assign(Object object) throws Exception {
      if(object != null) {
         Class type = object.getClass();
         
         if(!type.isArray()) {
            return converter.assign(object);            
         }
         return builder.convert(object);
      }
      return object;
   }

   @Override
   public Object convert(Object object) throws Exception {
      if(object != null) {
         Class type = object.getClass();
         
         if(!type.isArray()) {
            return converter.assign(object);            
         }
         return builder.convert(object);
      }
      return object;
   }
}