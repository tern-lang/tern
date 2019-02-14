package org.ternlang.compile.validate;

import static org.ternlang.core.ModifierType.ABSTRACT;
import static org.ternlang.core.ModifierType.PUBLIC;
import static org.ternlang.core.Reserved.ANY_TYPE;
import static org.ternlang.core.Reserved.TYPE_CLASS;

import java.util.List;
import java.util.Set;

import org.ternlang.core.ModifierType;
import org.ternlang.core.constraint.transform.ConstraintTransformer;
import org.ternlang.core.convert.AliasResolver;
import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.index.FunctionIndexer;
import org.ternlang.core.function.index.FunctionPointer;
import org.ternlang.core.module.Module;
import org.ternlang.core.property.Property;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;

public class TypeValidator {
   
   private static final String[] PROPERTIES = { TYPE_CLASS };
   private static final String[] TYPES = { ANY_TYPE };
   
   private final PropertyValidator properties;
   private final FunctionValidator functions;
   private final TypeExtractor extractor;
   private final FunctionIndexer indexer;
   private final AliasResolver resolver;
   
   public TypeValidator(ConstraintMatcher matcher, ConstraintTransformer transformer, TypeExtractor extractor, FunctionIndexer indexer) {
      this.functions = new FunctionValidator(matcher, transformer, extractor, indexer);
      this.properties = new PropertyValidator(matcher);
      this.resolver = new AliasResolver();
      this.extractor = extractor;
      this.indexer = indexer;
   }
   
   public void validate(Type type) throws Exception {
      Type actual = resolver.resolve(type);
      Class real = actual.getType();
      
      if(real == null) {
         validateModule(actual);
         validateHierarchy(actual);
         validateFunctions(actual);
         validateProperties(actual);
      }
   }
   
   private void validateModule(Type type) throws Exception {
      Module module = type.getModule();
      
      if(module == null) {
         throw new ValidateException("Type '" + type + "' has no module");
      }
   }
   
   private void validateHierarchy(Type type) throws Exception {
      Set<Type> types = extractor.getTypes(type);
      
      for(int i = 0; i < TYPES.length; i++) {
         String require = TYPES[i];
         int matches = 0;
         
         for(Type base : types) {
            String name = base.getName();
            
            if(name.equals(require)) {
               matches++;
            }
         }
         if(matches == 0) {
            throw new ValidateException("Type '" + type + "' has an invalid hierarchy");
         }
      }
   }

   private void validateProperties(Type type) throws Exception {
      List<Property> list = type.getProperties();
      
      for(int i = 0; i < PROPERTIES.length; i++) {
         String require = PROPERTIES[i];
         int matches = 0;
         
         for(Property property : list) {
            String name = property.getName();
            
            if(name.equals(require)) {
               matches++;
            }
            properties.validate(property);
         }
         if(matches == 0) {
            throw new ValidateException("Type '" + type + "' has no property '" + require + "'");
         }
      }
   }

   private void validateFunctions(Type type) throws Exception {
      int modifiers = type.getModifiers();
      List<Function> list = type.getFunctions();
      
      if(!ModifierType.isAbstract(modifiers)) {
         List<FunctionPointer> pointers = indexer.index(type, PUBLIC.mask | ABSTRACT.mask);
         
         for(FunctionPointer pointer : pointers) {
            Function function = pointer.getFunction();
            functions.validate(function, type); // ensure abstract methods are implemted
         }
      }
      for(Function function : list) {
         functions.validate(function);
      }
   }
}