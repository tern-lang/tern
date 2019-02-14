package org.ternlang.compile;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.ternlang.compile.Compiler;
import org.ternlang.compile.Executable;

import junit.framework.TestCase;

public class AnnotationTest extends TestCase {

   private static final String SOURCE_1=
   "var map = {x: @Blah(a: '/path', b: 11)};\n"+
   "println(map);\n";

   private static final String SOURCE_2=
   "import org.ternlang.compile.AnnotationTest$TypeExample;\n"+
   "AnnotationTest$TypeExample.class.getFunctions().stream().forEach(f -> println(f.getAnnotations()));\n"+
   "AnnotationTest$TypeExample.class.getFunctions().stream().forEach(f -> {\n"+
   "  f.getSignature().getParameters().stream().forEach(param -> {\n"+
   "     println(param.getAnnotations());"+
   "  });\n"+
   "});";
   
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
   
   @Retention(RUNTIME) 
   @Target({PARAMETER})
   public static @interface Param{
      String name();
   }
   
   @Root(name="blah", list={
         @Attribute(name="a"),
         @Attribute(name="b")
   })
   public static class TypeExample {
      
      @Attribute(name="func")
      public void func(@Param(name="x") String x, @Param(name="y") int y){
         
      }
   }

   public void testAnnotation() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_1);
      executable.execute();
   }
   
   public void testNativeAnnotations() throws Exception {;
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(SOURCE_2);
      executable.execute();
   } 
}
