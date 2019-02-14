package org.ternlang.tree.define;

import static org.ternlang.core.ModifierType.STATIC;
import static org.ternlang.core.result.Result.NORMAL;

import java.util.List;

import org.ternlang.core.Compilation;
import org.ternlang.core.Context;
import org.ternlang.core.Execution;
import org.ternlang.core.ModifierValidator;
import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.ErrorHandler;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.property.Property;
import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceInterceptor;
import org.ternlang.core.trace.TraceStatement;
import org.ternlang.tree.ModifierChecker;
import org.ternlang.tree.ModifierData;
import org.ternlang.tree.ModifierList;

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