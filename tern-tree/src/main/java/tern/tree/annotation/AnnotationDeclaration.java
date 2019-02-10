package tern.tree.annotation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import tern.core.Evaluation;
import tern.core.annotation.Annotation;
import tern.core.annotation.MapAnnotation;
import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.tree.construct.MapEntryData;

public class AnnotationDeclaration extends Evaluation {

   private AnnotationName name;
   private MapEntryData entries;
   private Value value;
   
   public AnnotationDeclaration(AnnotationName name) {
      this(name, null);
   }
   
   public AnnotationDeclaration(AnnotationName name, MapEntryData entries) {
      this.entries = entries;
      this.name = name;
   }

   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      if(value == null) {
         Annotation annotation = create(scope, left);
         
         if(annotation == null) {
            throw new InternalStateException("Could not create annotation");
         }
         value = Value.getTransient(annotation);
      }
      return value;
   }
   
   private Annotation create(Scope scope, Value left) throws Exception {
      Map<String, Object> attributes = new LinkedHashMap<String, Object>();
      
      if(entries != null) {
         Value value = entries.evaluate(scope, left);
         Map<Object, Object> map = value.getValue();
         Set<Object> keys = map.keySet();
         
         for(Object key : keys) {
            String name = String.valueOf(key);
            Object attribute = map.get(name);
            
            attributes.put(name, attribute);
         }
      }
      Value value = name.evaluate(scope, left);
      String name = value.getString();
      
      return new MapAnnotation(name, attributes);
   }
}