package org.ternlang.core.type.index;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.ternlang.core.ModifierType;
import org.ternlang.core.type.Type;
import org.ternlang.core.annotation.Annotation;
import org.ternlang.core.annotation.AnnotationExtractor;
import org.ternlang.core.function.Function;
import org.ternlang.core.platform.PlatformProvider;
import org.ternlang.core.type.extend.ClassExtender;

public class MethodIndexer {

   private final AnnotationExtractor extractor;
   private final FunctionGenerator generator;
   private final ConstructorIndexer indexer;
   private final ModifierConverter converter;
   private final ClassExtender extender;

   public MethodIndexer(ClassExtender extender, PlatformProvider provider){
      this.indexer = new ConstructorIndexer(provider);
      this.generator = new FunctionGenerator(provider);
      this.extractor = new AnnotationExtractor();
      this.converter = new ModifierConverter();
      this.extender = extender;
   }

   public List<Function> index(Type type) throws Exception {
      List<Function> functions = new ArrayList<Function>();

      if(type != null) {
         Class source = type.getType();
         List<Function> extensions = extender.extend(source);
         List<Function> constructors = indexer.index(type);
         Method[] methods = source.getDeclaredMethods();

         functions.addAll(constructors);
         functions.addAll(extensions);

         for(Method method : methods){
            int modifiers = converter.convert(method);

            if(ModifierType.isPublic(modifiers) || ModifierType.isProtected(modifiers)) {
               String name = method.getName();
               Function function = generator.generate(type, method, name, modifiers);
               List<Annotation> extracted = extractor.extract(method);
               List<Annotation> actual = function.getAnnotations();

               functions.add(function);
               actual.addAll(extracted);
            }
         }
      }
      return functions;
   }
}