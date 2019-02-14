package org.ternlang.tree.annotation;

import java.util.List;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.Value;
import org.ternlang.core.annotation.Annotation;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Parameter;
import org.ternlang.core.module.Module;
import org.ternlang.core.property.Property;

public class AnnotationList {
   
   private final AnnotationDeclaration[] list;

   public AnnotationList(AnnotationDeclaration... list) {
      this.list = list;
   }

   public void apply(Scope scope, Module module) throws Exception {
      List<Annotation> annotations = module.getAnnotations();
      
      for(AnnotationDeclaration entry : list) {
         Value value = entry.evaluate(scope, null);
         Annotation annotation = value.getValue();
         
         annotations.add(annotation);
      }
   }
   
   public void apply(Scope scope, Type type) throws Exception {
      List<Annotation> annotations = type.getAnnotations();
      
      for(AnnotationDeclaration entry : list) {
         Value value = entry.evaluate(scope, null);
         Annotation annotation = value.getValue();
         
         annotations.add(annotation);
      }
   }
   
   public void apply(Scope scope, Property property) throws Exception {
      List<Annotation> annotations = property.getAnnotations();
      
      for(AnnotationDeclaration entry : list) {
         Value value = entry.evaluate(scope, null);
         Annotation annotation = value.getValue();
         
         annotations.add(annotation);
      }
   }
   
   public void apply(Scope scope, Function function) throws Exception {
      List<Annotation> annotations = function.getAnnotations();
      
      for(AnnotationDeclaration entry : list) {
         Value value = entry.evaluate(scope, null);
         Annotation annotation = value.getValue();
         
         annotations.add(annotation);
      }
   }
   
   public void apply(Scope scope, Parameter parameter) throws Exception {
      List<Annotation> annotations = parameter.getAnnotations();
      
      for(AnnotationDeclaration entry : list) {
         Value value = entry.evaluate(scope, null);
         Annotation annotation = value.getValue();
         
         annotations.add(annotation);
      }
   }
}