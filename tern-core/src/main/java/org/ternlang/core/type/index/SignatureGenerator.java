package org.ternlang.core.type.index;

import static java.util.Collections.EMPTY_LIST;
import static org.ternlang.core.function.Origin.PLATFORM;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.ternlang.core.annotation.Annotation;
import org.ternlang.core.annotation.AnnotationConverter;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.ConstraintMapper;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.FunctionSignature;
import org.ternlang.core.function.Parameter;
import org.ternlang.core.function.ParameterBuilder;
import org.ternlang.core.function.Signature;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;

public class SignatureGenerator {
   
   private final GenericConstraintExtractor extractor;
   private final AnnotationConverter converter;
   private final ParameterBuilder builder;
   private final ConstraintMapper mapper;
   
   public SignatureGenerator() {
      this.extractor = new GenericConstraintExtractor();
      this.converter = new AnnotationConverter();
      this.builder = new ParameterBuilder();
      this.mapper = new ConstraintMapper();
   }

   public Signature generate(Type type, Method method) {
      Constraint[] generics = extractor.extractGenerics(method);
      Constraint[] parameters = extractor.extractParameters(method);
      Object[][] annotations = method.getParameterAnnotations();
      Scope scope = type.getScope();
      Module module = type.getModule();
      boolean variable = method.isVarArgs();      
      
      try {
         List<Parameter> signature = new ArrayList<Parameter>();
         List<Constraint> constraints = new ArrayList<Constraint>();
   
         for(int i = 0; i < generics.length; i++) {
            Constraint constraint = generics[i];
            Constraint match = mapper.map(scope, constraint);
            
            constraints.add(match);
         }
         for(int i = 0; i < parameters.length; i++){
            boolean last = i + 1 == parameters.length;
            Constraint constraint = parameters[i];
            Parameter parameter = builder.create(constraint, i, variable && last);
            Object[] list = annotations[i];
            
            if(list.length > 0) {
               List<Annotation> actual = parameter.getAnnotations();
               
               for(int j = 0; j < list.length; j++) {
                  Object value = list[j];
                  Object result = converter.convert(value);
                  Annotation annotation = (Annotation)result;
                  
                  actual.add(annotation);
               }
            }
            signature.add(parameter);
         }
         return new FunctionSignature(signature, constraints, module, method, PLATFORM, true, variable);
      } catch(Exception e) {
         throw new InternalStateException("Could not create function for " + method, e);
      }
   }
   
   public Signature generate(Type type, Constructor constructor) {
      Constraint[] constraints = extractor.extractParameters(constructor);
      Object[][] annotations = constructor.getParameterAnnotations();
      Module module = type.getModule();
      boolean variable = constructor.isVarArgs();
      
      try {
         List<Parameter> parameters = new ArrayList<Parameter>();
   
         for(int i = 0; i < constraints.length; i++){
            boolean last = i + 1 == constraints.length;
            Constraint constraint = constraints[i];
            Parameter parameter = builder.create(constraint, i, variable && last);
            Object[] list = annotations[i];
            
            if(list.length > 0) {
               List<Annotation> actual = parameter.getAnnotations();
               
               for(int j = 0; j < list.length; j++) {
                  Object value = list[j];
                  Object result = converter.convert(value);
                  Annotation annotation = (Annotation)result;
                  
                  actual.add(annotation);
               }
            }
            parameters.add(parameter);
         }
         return new FunctionSignature(parameters, EMPTY_LIST, module, constructor, PLATFORM, true, variable);
      } catch(Exception e) {
         throw new InternalStateException("Could not create constructor for " + constructor, e);
      }
   }
}