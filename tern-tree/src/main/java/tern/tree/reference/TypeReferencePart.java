package tern.tree.reference;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Entity;
import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.error.InternalStateException;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.scope.Scope;
import tern.core.scope.ScopeState;
import tern.core.type.Type;
import tern.core.type.TypeExtractor;
import tern.core.variable.Value;
import tern.tree.NameReference;

public class TypeReferencePart implements Compilation {

   private final NameReference reference;
   
   public TypeReferencePart(Evaluation type) {
      this.reference = new NameReference(type);
   }

   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      TypeExtractor extractor = context.getExtractor();
      String name = reference.getName(scope);
      
      return new CompileResult(extractor, module, name);
   }
   
   private static class CompileResult extends TypeNavigation {

      private final TypeReferenceWrapper mapper;
      private final TypeExtractor extractor;
      private final Module source;
      private final String name;
   
      public CompileResult(TypeExtractor extractor, Module source, String name) {
         this.mapper = new TypeReferenceWrapper();
         this.extractor = extractor;
         this.source = source;
         this.name = name;
      }   
      
      @Override
      public String qualify(Scope scope, String left) throws Exception {
         if(left != null) {
            return left + '$' +name;
         }
         return name;
      }
      
      @Override
      public Value evaluate(Scope scope, Value left) throws Exception {
         Object object = left.getValue();
         
         if(object != null) {
            if(Module.class.isInstance(object)) {
               return create(scope, (Module)object);
            }
            if(Type.class.isInstance(object)) {
               return create(scope, (Type)object);
            }
            throw new InternalStateException("No type found for '" + name + "' in '" + source + "'"); // class not found
         }
         return create(scope);
      }
      
      private Value create(Scope scope) throws Exception {
         Module parent = scope.getModule();
         Entity result = parent.getType(name);
         Type type = scope.getType();

         if(result == null) {
            result = source.getModule(name); 
         }
         if(result == null && type != null) {
            result = extractor.getType(type, name);
         }
         if(result == null) {
            ScopeState state = scope.getState();
            Constraint constraint = state.getConstraint(name);
            
            if(constraint == null) {                         
               throw new InternalStateException("No type found for '" + name + "' in '" + source + "'"); // class not found
            }
            return mapper.toValue(scope, constraint, name);
         }
         return mapper.toValue(scope, result, name);
      }
      
      private Value create(Scope scope, Module module) throws Exception {
         Entity result = module.getType(name);
         
         if(result == null) {
            throw new InternalStateException("No type found for '" + name + "' in '" + module + "'"); // class not found
         }
         return mapper.toValue(scope, result, name);
      }
      
      private Value create(Scope scope, Type type) throws Exception {
         Module module = type.getModule();
         String parent = type.getName();
         Entity result = module.getType(parent + "$"+name);
         
         if(result == null) {
            throw new InternalStateException("No type found for '" + parent + "." + name + "' in '" + module + "'"); // class not found
         }
         return mapper.toValue(scope, result, parent + "$"+name);
      }
   }
}