package org.ternlang.core.index;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import org.ternlang.core.annotation.Annotation;
import org.ternlang.core.annotation.AnnotationExtractor;

import junit.framework.TestCase;

public class AnnotationExtractorTest extends TestCase {
   
   @Retention(RUNTIME) 
   @Target({TYPE})
   public static @interface Root{
      String name();
      Attribute[] list() default {};
   }
   
   @Retention(RUNTIME) 
   @Target({FIELD,METHOD})
   public static @interface Attribute{
      String name();
   }
   
   @Root(name="blah", list={
         @Attribute(name="a"),
         @Attribute(name="b")
   })
   public static class TypeExample {
      
   }

   public void testExtractor() throws Exception {
      AnnotationExtractor extractor = new AnnotationExtractor();
      List<Annotation> annotations = extractor.extract(TypeExample.class);
      assertFalse(annotations.isEmpty());
   }
}
