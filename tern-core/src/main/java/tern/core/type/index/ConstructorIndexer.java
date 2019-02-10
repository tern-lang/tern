package tern.core.type.index;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tern.core.type.Type;
import tern.core.annotation.Annotation;
import tern.core.annotation.AnnotationExtractor;
import tern.core.function.Function;
import tern.core.platform.PlatformProvider;

public class ConstructorIndexer {

   private final AnnotationExtractor extractor;
   private final ConstructorGenerator generator;
   private final ModifierConverter converter;
   
   public ConstructorIndexer(PlatformProvider provider) {
      this.generator = new ConstructorGenerator(provider);
      this.extractor = new AnnotationExtractor();
      this.converter = new ModifierConverter();
   }

   public List<Function> index(Type type) throws Exception {
      Class source = type.getType();
      
      if(source != Class.class) {
         Constructor[] constructors = source.getDeclaredConstructors();
         
         if(constructors.length > 0) {
            List<Function> functions = new ArrayList<Function>();
      
            for(Constructor constructor : constructors){
               int modifiers = converter.convert(constructor); // accept all consructors public/private
               Class[] parameters = constructor.getParameterTypes();
               Function function = generator.generate(type, constructor, parameters, modifiers);
               List<Annotation> extracted = extractor.extract(constructor);
               List<Annotation> actual = function.getAnnotations();
               
               functions.add(function);
               actual.addAll(extracted);
            }
            return functions;
         }
      }
      return Collections.emptyList();
   }
}