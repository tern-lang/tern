package org.ternlang.compile.validate;

import java.util.List;
import java.util.Set;

import org.ternlang.core.ModifierType;
import org.ternlang.core.constraint.transform.ConstraintTransformer;
import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.convert.FunctionOverrideMatcher;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.Origin;
import org.ternlang.core.function.Signature;
import org.ternlang.core.function.index.FunctionIndexer;
import org.ternlang.core.function.index.FunctionPointer;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;

public class FunctionValidator {
   
   private final FunctionOverrideMatcher matcher;
   private final FunctionIndexer indexer;
   private final TypeExtractor extractor;
   
   public FunctionValidator(ConstraintMatcher matcher, ConstraintTransformer transformer, TypeExtractor extractor, FunctionIndexer indexer) {
      this.matcher = new FunctionOverrideMatcher(matcher, transformer);
      this.extractor = extractor;
      this.indexer = indexer;
   }
   
   public void validate(Function function) throws Exception {
      Type source = function.getSource();
      
      if(source == null) {
         throw new ValidateException("Function '" + function + "' does not have a declaring type");
      }
      validateModifiers(function);
      validateDuplicates(function);
   }
   
   public void validate(Function function, Type type) throws Exception {
      Type source = function.getSource();
      
      if(source == type) {
         throw new ValidateException("Function '" + function + "' is abstract but '" + type + "' is not");
      }
      if(source == null) {
         throw new ValidateException("Function '" + function + "' does not have a declaring type");
      }
      validateImplemented(function, type);      
   }
   
   private void validateImplemented(Function function, Type type) throws Exception {
      Scope scope = type.getScope();
      int modifiers = function.getModifiers();
      
      if(ModifierType.isAbstract(modifiers)) {
         Signature signature = function.getSignature();
         Origin origin = signature.getOrigin();
         String name = function.getName();
         
         if(!origin.isSystem()) {
            Type[] parameters = matcher.matchTypes(scope, function, type);
            FunctionPointer resolved = indexer.index(type, name, parameters);
            
            if(resolved == null) {
               throw new ValidateException("Type '" + type + "' must implement '" + function + "'");
            }
            Function match = resolved.getFunction();
            int mask = match.getModifiers();
            
            if(ModifierType.isAbstract(mask)) {
               throw new ValidateException("Type '" + type + "' must implement '" + function + "'");
            }
         }
      }
   }
   
   private void validateModifiers(Function function) throws Exception {
      Type source = function.getSource();
      Scope scope = source.getScope();
      int modifiers = function.getModifiers();
      
      if(ModifierType.isOverride(modifiers)) {
         Set<Type> types = extractor.getTypes(source);
         String name = function.getName();
         int matches = 0;
         
         for(Type type : types) {
            if(type != source) {
               List<Function> functions = type.getFunctions();
               
               for(Function available : functions) {
                  String match = available.getName();
                  
                  if(name.equals(match)) {
                     Type[] parameters = matcher.matchTypes(scope, function, available);
                     
                     if(parameters != null) {
                        validateModifiers(function, parameters);
                        matches++;
                     }
                  }
               }
            }
         }
         if(matches == 0) {
            throw new ValidateException("Function '" + function + "' is not an override");
         }
      }
   }
   
   private void validateModifiers(Function override, Type[] parameters) throws Exception {
      Signature signature = override.getSignature();
      Origin origin = signature.getOrigin();
      Type source = override.getSource();
      String name = override.getName();
      
      if(!origin.isSystem()) {
         FunctionPointer match = indexer.index(source, name, parameters);
         
         if(match == null) {
            throw new ValidateException("Function '" + override +"' is not an override");
         }
         Function function = match.getFunction();
         
         if(function != override) {
            throw new ValidateException("Function '" + override +"' is not an override");
         }
      }
   }

   private void validateDuplicates(Function actual) throws Exception {
      Type source = actual.getSource();
      int modifiers = actual.getModifiers();
      
      if(!ModifierType.isAbstract(modifiers)) {
         Signature signature = actual.getSignature();
         Origin origin = signature.getOrigin();
         Scope scope = source.getScope();
         String name = actual.getName();
         
         if(!origin.isSystem()) {
            Type[] parameters = matcher.matchTypes(scope, actual, source);
            FunctionPointer resolved = indexer.index(source, name, parameters);
            
            if(resolved == actual) {
               throw new ValidateException("Function '" + actual +"' has a duplicate '" + resolved + "'");
            }
            Function function = resolved.getFunction();
            
            if(function != actual) {
               throw new ValidateException("Function '" + actual +"' has a duplicate '" + resolved + "'");
            }
         }
      }
   }
}