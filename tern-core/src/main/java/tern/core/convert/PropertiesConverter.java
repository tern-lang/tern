package tern.core.convert;

import static tern.core.convert.Score.EXACT;
import static tern.core.convert.Score.POSSIBLE;

import java.util.Map;
import java.util.Properties;

import tern.core.convert.proxy.ProxyWrapper;
import tern.core.type.Type;
import tern.core.type.TypeExtractor;

public class PropertiesConverter extends ConstraintConverter {
   
   private final ObjectConverter converter;
   
   public PropertiesConverter(TypeExtractor extractor, CastChecker checker, ProxyWrapper wrapper, Type constraint) {
      this.converter = new ObjectConverter(extractor, checker, wrapper, constraint);
   }
   
   @Override
   public Score score(Type actual) throws Exception {
      if(actual != null) {
         Class type = actual.getType();
         
         if(type != Properties.class) {
            if(Map.class.isAssignableFrom(type)) {
               return POSSIBLE;
            }  
            return converter.score(actual);
         }
      }
      return EXACT;
   }

   @Override
   public Score score(Object object) throws Exception { // argument type
      if(object != null) {
         Class type = object.getClass();
         
         if(type != Properties.class) {
            if(Map.class.isAssignableFrom(type)) {
               return POSSIBLE;
            }  
            return converter.score(object);
         }
      }
      return EXACT;
   }

   @Override
   public Object convert(Object object) throws Exception {
      if(object != null) {
         Class type = object.getClass();
         
         if(type != Properties.class) {
            if(Map.class.isAssignableFrom(type)) {
               return PropertiesBuilder.create((Map)object);
            }
            return converter.convert(object);
         }
      }
      return object;
   }
}