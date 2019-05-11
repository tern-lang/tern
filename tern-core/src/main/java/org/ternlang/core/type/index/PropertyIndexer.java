package org.ternlang.core.type.index;

import static org.ternlang.core.ModifierType.CLASS;
import static org.ternlang.core.ModifierType.CONSTANT;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ternlang.core.ModifierType;
import org.ternlang.core.annotation.Annotation;
import org.ternlang.core.annotation.AnnotationExtractor;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Function;
import org.ternlang.core.property.Property;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.extend.ClassExtender;

public class PropertyIndexer {
   
   private final GenericConstraintExtractor generics;
   private final FunctionPropertyCollector collector;
   private final AnnotationExtractor extractor;
   private final ClassPropertyBuilder builder;
   private final ModifierConverter converter;
   private final PropertyGenerator generator;
   private final ClassExtender extender;
   private final MethodMatcher matcher;
   private final TypeIndexer indexer;
   
   public PropertyIndexer(TypeIndexer indexer, ClassExtender extender){
      this.builder = new ClassPropertyBuilder(indexer);
      this.generics = new GenericConstraintExtractor(); 
      this.collector = new FunctionPropertyCollector();
      this.extractor = new AnnotationExtractor();
      this.converter = new ModifierConverter();
      this.generator = new PropertyGenerator();
      this.matcher = new MethodMatcher();
      this.extender = extender;
      this.indexer = indexer;
   }

   public List<Property> index(Class source) throws Exception {
      List<Property> properties = builder.create(source);
      List<Function> extensions = extender.extend(source);
      Method[] methods = source.getDeclaredMethods();
      Field[] fields = source.getDeclaredFields();
      Class[] types = source.getDeclaredClasses();
      Type type = indexer.loadType(source);
      int count = extensions.size();

      if(fields.length + methods.length + types.length +count > 0) {
         Set<String> done = new HashSet<String>();
         
         if(fields.length > 0) {
            List<Property> indexed = index(type, fields);
            
            for(Property property : indexed) {
               String name = property.getName();
               
               if(done.add(name)) {
                  properties.add(property);
               }
            }
         }
         if(methods.length > 0) {
            List<Property> indexed = index(type, methods);
            
            for(Property property : indexed) {
               String name = property.getName();
               
               if(done.add(name)) {
                  properties.add(property);
               }
            }
         }
         if(types.length > 0) {
            List<Property> indexed = index(type, types);
            
            for(Property property : indexed) {
               String name = property.getName();
               
               if(done.add(name)) {
                  properties.add(property);
               }
            }
         }
         if(count > 0) {
            List<Property> extended = collector.collect(extensions, done);
            
            for(Property property : extended) {
               String name = property.getName();
               
               if(done.add(name)) {
                  properties.add(property);
               }
            }
         }
      }
      return properties;
   }
   
   private List<Property> index(Type type, Class[] types) throws Exception {
      List<Property> properties = new ArrayList<Property>();
      
      for(Class entry : types) {
         int modifiers = converter.convert(entry);
         
         if(ModifierType.isPublic(modifiers) || ModifierType.isProtected(modifiers)) {
            String name = entry.getSimpleName();
            Type element = indexer.loadType(entry);
            Constraint constraint = Constraint.getConstraint(element, CLASS.mask);
            Property property = generator.generate(type, constraint, element, name, modifiers | CONSTANT.mask); 
            List<Annotation> extracted = extractor.extract(entry);
            List<Annotation> actual = property.getAnnotations();
            
            properties.add(property);
            actual.addAll(extracted);
         }
      }
      return properties;
   }
   
   private List<Property> index(Type type, Field[] fields) throws Exception {
      List<Property> properties = new ArrayList<Property>();
      
      for(Field field : fields) {
         int modifiers = converter.convert(field);
         
         if(ModifierType.isPublic(modifiers) || ModifierType.isProtected(modifiers)) {
            String name = field.getName();           
            Constraint constraint = generics.extractField(field, modifiers);
            Property property = generator.generate(field, type, constraint, name, modifiers); 
            List<Annotation> extracted = extractor.extract(field);
            List<Annotation> actual = property.getAnnotations();
            
            properties.add(property);
            actual.addAll(extracted);
         }
      }
      return properties;
   }

   private List<Property> index(Type type, Method[] methods) throws Exception {
      List<Property> properties = new ArrayList<Property>();
      
      for(Method method : methods){
         int modifiers = converter.convert(method);
         
         if(ModifierType.isPublic(modifiers) && !ModifierType.isStatic(modifiers)) {
            Class[] parameters = method.getParameterTypes();
            Class returns = method.getReturnType();
            
            if(parameters.length == 0 && returns != void.class) {
               String name = PropertyNameExtractor.getProperty(method);
               Class declaration = method.getReturnType();
               Method write = matcher.match(methods, declaration, name);
               
               if(write == null) {
                  modifiers |= CONSTANT.mask;
               }
               Constraint constraint = generics.extractReturn(method, modifiers);
               Property property = generator.generate(method, write, type, constraint, name, modifiers);                
               List<Annotation> extracted = extractor.extract(method);
               List<Annotation> actual = property.getAnnotations();
               
               if(write != null){
                  write.setAccessible(true);
               }
               method.setAccessible(true);
               properties.add(property);
               actual.addAll(extracted);
            }
         }
      }
      return properties;
   }

}