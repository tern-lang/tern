package tern.tree.define;

import static tern.core.ModifierType.STATIC;
import static tern.core.result.Result.NORMAL;

import java.util.List;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Execution;
import tern.core.ModifierValidator;
import tern.core.Statement;
import tern.core.constraint.Constraint;
import tern.core.error.ErrorHandler;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.property.Property;
import tern.core.result.Result;
import tern.core.scope.Scope;
import tern.core.trace.Trace;
import tern.core.trace.TraceInterceptor;
import tern.core.trace.TraceStatement;
import tern.tree.ModifierChecker;
import tern.tree.ModifierData;
import tern.tree.ModifierList;

public class ModuleDeclaration implements Compilation {
   
   private final ModuleProperty[] properties;
   private final ModifierData modifiers;
   
   public ModuleDeclaration(ModifierList modifiers, ModuleProperty... properties) {
      this.modifiers = new ModifierChecker(modifiers);
      this.properties = properties;    
   }
   
   @Override
   public ModulePart compile(Module module, Path path, int line) throws Exception {
      return new CompileResult(modifiers, properties, module, path, line);
   }
   
   private static class CompileResult implements ModulePart {

      private final ModuleProperty[] properties;
      private final ModifierData modifiers;
      private final Module module;
      private final Path path;
      private final int line;
      
      public CompileResult(ModifierData modifiers, ModuleProperty[] properties, Module module, Path path, int line) {
         this.properties = properties;
         this.modifiers = modifiers;
         this.module = module;
         this.path = path;
         this.line = line;
      }  
      
      @Override
      public Statement define(ModuleBody body, Module module) throws Exception {
         Context context = module.getContext();
         ErrorHandler handler = context.getHandler();
         TraceInterceptor interceptor = context.getInterceptor();
         Trace trace = Trace.getNormal(module, path, line);
         Statement statement = create(body);
         
         return new TraceStatement(interceptor, handler, statement, trace);
      }
      
      private Statement create(ModuleBody body) throws Exception {
         return new CompileStatement(modifiers, properties, body);
      }
   }
   
   private static class CompileStatement extends Statement {
   
      private final ModuleProperty[] properties;
      private final ModifierValidator validator;
      private final ModifierData modifiers;
      private final ModuleBody body;
      
      public CompileStatement(ModifierData modifiers, ModuleProperty[] properties, ModuleBody body) {
         this.validator = new ModifierValidator();
         this.properties = properties;
         this.modifiers = modifiers;
         this.body = body;
      }  
      
      @Override
      public boolean define(Scope scope) throws Exception {
         Module module = scope.getModule();
         List<Property> list = module.getProperties();
         int mask = modifiers.getModifiers();
         
         for(ModuleProperty declaration : properties) {
            Property property = declaration.define(body, scope, mask | STATIC.mask);
            
            validator.validate(module, property, mask | STATIC.mask);
            list.add(property);
         }
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         int mask = modifiers.getModifiers();
         
         for(ModuleProperty declaration : properties) {
            declaration.compile(body, scope, mask | STATIC.mask); 
         }
         return new CompileExecution(modifiers, properties, body);
      }
   }
   
   private static class CompileExecution extends Execution {
      
      private final ModuleProperty[] properties;
      private final ModifierData modifiers;
      private final ModuleBody body;
      
      public CompileExecution(ModifierData modifiers, ModuleProperty[] properties, ModuleBody body) {
         this.properties = properties;
         this.modifiers = modifiers;
         this.body = body;
      }  
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Module module = scope.getModule();
         Scope outer = module.getScope(); // use the module scope
         int mask = modifiers.getModifiers();
         
         for(ModuleProperty declaration : properties) {
            declaration.execute(body, outer, mask | STATIC.mask); 
         }
         return NORMAL;
      }
   }
}