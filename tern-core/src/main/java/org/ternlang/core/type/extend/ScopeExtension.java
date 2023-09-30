package org.ternlang.core.type.extend;

import org.ternlang.common.command.CommandBuilder;
import org.ternlang.common.command.Console;
import org.ternlang.core.Context;
import org.ternlang.core.Execution;
import org.ternlang.core.ExpressionEvaluator;
import org.ternlang.core.Statement;
import org.ternlang.core.convert.StringBuilder;
import org.ternlang.core.link.Package;
import org.ternlang.core.link.PackageDefinition;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.ModuleRegistry;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.TypeLoader;

import java.util.Map;

public class ScopeExtension {

   private final CommandBuilder builder;
   
   public ScopeExtension() {
      this.builder = new CommandBuilder();
   }
   
   public <T> T eval(Scope scope, String source) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ExpressionEvaluator evaluator = context.getEvaluator();
      String name = module.getName();
      
      return evaluator.evaluate(scope, source, name);
   }
   
   public <T> T eval(Scope scope, String source, String name) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ExpressionEvaluator evaluator = context.getEvaluator();
      ModuleRegistry registry = context.getRegistry();
      Module parent = registry.addModule(name);
      Scope inner = parent.getScope();
      
      return evaluator.evaluate(inner, source, name);
   }
   
   public Module load(Scope scope, String name) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ModuleRegistry registry = context.getRegistry();
      TypeLoader loader = context.getLoader();
      Package builder = loader.importPackage(name);
      PackageDefinition definition = builder.create(scope);
      Statement statement = definition.define(scope, null);
      Execution execution = statement.compile(scope, null);
      
      execution.execute(scope);
      
      return registry.getModule(name);
   }
   
   public Console exec(Scope scope, String command) throws Exception {
      return builder.create(command).call();
   }
   
   public Console exec(Scope scope, String command, String directory) throws Exception {
      return builder.create(command, directory).call();
   }
   
   public Console exec(Scope scope, String command, Map<String, String> environment) throws Exception {
      return builder.create(command, environment).call();
   }
   
   public Console exec(Scope scope, String command, String directory, Map<String, String> environment) throws Exception {
      return builder.create(command, directory, environment).call();
   }
   
   public void printf(Scope scope, Object value, Object... values)  throws Exception{
      String text = StringBuilder.create(scope, value);
      String result = String.format(text, values);
      
      System.out.print(result);
   }   
   
   public void print(Scope scope, Object value)  throws Exception{
      String text = StringBuilder.create(scope, value);
      
      System.out.print(text);
   }
   
   public void println(Scope scope, Object value) throws Exception{
      String text = StringBuilder.create(scope, value);
      
      System.out.println(text);
   }
   
   public void println(Scope scope) throws Exception{
      System.out.println();
   }
   
   public void sleep(Scope scope, long time) throws Exception {
      Thread.sleep(time);
   }

   public long time(Scope scope) throws Exception {
      return System.currentTimeMillis();
   }
}