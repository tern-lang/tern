package tern.tree.annotation;

import java.util.List;

import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.variable.Value;
import tern.core.annotation.Annotation;
import tern.core.function.Function;
import tern.core.function.Parameter;
import tern.core.module.Module;
import tern.core.property.Property;

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