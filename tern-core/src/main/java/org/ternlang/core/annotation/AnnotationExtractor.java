package org.ternlang.core.annotation;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;

public class AnnotationExtractor {
   
   private final AnnotationConverter converter;
   
   public AnnotationExtractor() {
      this.converter = new AnnotationConverter();
   }

   public List<Annotation> extract(AnnotatedElement element) throws Exception {
      List<Annotation> list = new ArrayList<Annotation>();

      if(element != null) {
         Object[] array = element.getAnnotations();
         
         for(Object entry : array) {
            Object result = converter.convert(entry);
            Annotation annotation = (Annotation)result;
            
            list.add(annotation);
         }
      }
      return list;  
   }
}