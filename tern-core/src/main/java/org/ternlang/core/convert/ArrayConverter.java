package org.ternlang.core.convert;

import static org.ternlang.core.convert.Score.EXACT;
import static org.ternlang.core.convert.Score.INVALID;
import static org.ternlang.core.convert.Score.POSSIBLE;
import static org.ternlang.core.convert.Score.SIMILAR;
import static org.ternlang.core.convert.Score.TRANSIENT;

import java.lang.reflect.Array;
import java.util.List;

import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.type.Type;

public class ArrayConverter extends ConstraintConverter {

   private final ConstraintMatcher matcher;
   private final ArrayCastChecker checker;
   private final ProxyWrapper wrapper;
   private final Type type;
   
   public ArrayConverter(ConstraintMatcher matcher, CastChecker checker, ProxyWrapper wrapper, Type type) {
      this.checker = new ArrayCastChecker(checker);
      this.wrapper = wrapper;
      this.matcher = matcher;
      this.type = type;
   }
   
   @Override
   public Score score(Type actual) throws Exception {
      if(actual != null) {
         Class require = type.getType();
         Class real = actual.getType();
      
         if(real != null) {
            if(List.class.isAssignableFrom(real)) {
               return POSSIBLE;
            }
         }
         if(require != real) {
            return checker.toArray(actual, type);
         }
      }
      return EXACT;
   }
   
   @Override
   public Score score(Object object) throws Exception {
      if(object != null) {
         Class require = type.getType();
         Class actual = object.getClass();
         
         if(List.class.isInstance(object)) {
            return score((List)object, type);
         }
         if(actual.isArray()) {
            if(require != actual) {
               Score score = checker.toArray(actual, require);
               
               if(score.isInvalid()) {               
                  return score(object, type);
               }
               return score;
            }
            return EXACT;
         }
         return INVALID;
      }
      return EXACT;
   }
   
   private Score score(Object list, Type type) throws Exception {
      Type entry = type.getEntry();
      
      if(entry != null) {   
         int length = Array.getLength(list);
         ConstraintConverter converter = matcher.match(entry);
         
         if(length > 0) {
            Score total = TRANSIENT; // temporary
            
            for(int i = 0; i < length; i++) {
               Object element = Array.get(list, i);
               Object object = wrapper.fromProxy(element);
               Score score = converter.score(object);
   
               if(score.isInvalid()) {
                  return INVALID;
               }
               total = Score.average(score, total);
            }
            return total;
         }
         return SIMILAR;
      }
      return INVALID;
   }
   
   private Score score(List list, Type type) throws Exception {
      Type entry = type.getEntry();
      
      if(entry != null) {  
         int length = list.size();
         ConstraintConverter converter = matcher.match(entry);
         
         if(length > 0) {
            Score total = TRANSIENT; // temporary
            
            for(int i = 0; i < length; i++) {
               Object element = list.get(i);
               Object object = wrapper.fromProxy(element);
               Score score = converter.score(object);
   
               if(score.isInvalid()) {
                  return INVALID;
               }
               total = Score.average(score, total);
            }
            return total;
         }
         return POSSIBLE; 
      }
      return INVALID;
   }
   
   @Override
   public Object assign(Object object) throws Exception {
      if(object != null) {
         Class require = type.getType();
         Class actual = object.getClass();
         
         if(actual.isArray()) {
            Score score = checker.toArray(actual, require);
             
            if(!score.isExact()) {   
               return convert(object, type);
            }
            return object;
         }
         if(List.class.isInstance(object)) {
            return convert((List)object, type);
         }
         throw new InternalStateException("Array can not be assigned from " + actual);
      }
      return object;
   }
   
   @Override
   public Object convert(Object object) throws Exception {
      if(object != null) {
         Class require = type.getType();
         Class actual = object.getClass();
         
         if(actual.isArray()) {
            if(require != actual) {
               return convert(object, type);
            }
            return object;
         }
         if(List.class.isInstance(object)) {
            return convert((List)object, type);
         }
         throw new InternalStateException("Array can not be converted from " + actual);
      }
      return object;
   }
   
   private Object convert(Object list, Type type) throws Exception {
      Type entry = type.getEntry();
      
      if(entry != null) {   
         int length = Array.getLength(list);
         Class require = type.getType(); 
         Class component = require.getComponentType();
         ConstraintConverter converter = matcher.match(entry);
         Object array = Array.newInstance(component, length);   
         
         for(int i = 0; i < length; i++) {
            Object element = Array.get(list, i);
            Object object = wrapper.fromProxy(element);
            
            if(object != null) {
               Score score = converter.score(object);
   
               if(score.isInvalid()) {
                  throw new InternalStateException("Array element is not '" + require + "'");
               }
               element = converter.convert(object);
            }
            Array.set(array, i, element);
         }
         return array;
      }
      throw new InternalStateException("Array element is not a list");
   }
   
   private Object convert(List list, Type type) throws Exception {
      Type entry = type.getEntry();
      
      if(entry != null) { 
         int length = list.size();
         Class require = type.getType();
         Class component = require.getComponentType();
         ConstraintConverter converter = matcher.match(entry);
         Object array = Array.newInstance(component, length); 
         
         for(int i = 0; i < length; i++) {
            Object element = list.get(i);
            Object object = wrapper.fromProxy(element);
            
            if(object != null) {
               Score score = converter.score(object);
   
               if(score.isInvalid()) {
                  throw new InternalStateException("Array element is not '" + require + "'");
               }
               element = converter.convert(object);
            }
            Array.set(array, i, element);
         }
         return array;
      }
      throw new InternalStateException("Array element is not a list");
   }
}