package org.ternlang.core.link;

import static org.ternlang.core.result.Result.NORMAL;

import java.util.ArrayList;
import java.util.List;

import org.ternlang.core.Execution;
import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.module.Path;
import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;

public class PackageDefinitionList implements PackageDefinition {
   
   private final List<PackageDefinition> definitions;
   
   public PackageDefinitionList(List<PackageDefinition> definitions) {
      this.definitions = definitions;
   }

   @Override
   public Statement define(Scope scope, Path from) throws Exception {
      List<Statement> statements = new ArrayList<Statement>();
      
      for(PackageDefinition definition : definitions) {
         Statement statement = definition.define(scope, from);
         
         if(statement != null) {
            statements.add(statement);
         }
      }
      return new StatementList(statements);
   }
   
   private static class StatementList extends Statement {
      
      private final List<Statement> statements;
      
      public StatementList(List<Statement> statements) {
         this.statements = statements;
      }
      
      @Override
      public void create(Scope scope) throws Exception {
         for(Statement statement : statements){
            statement.create(scope);
         }
      }
                     
      @Override
      public boolean define(Scope scope) throws Exception {
         for(Statement statement : statements){
            statement.define(scope);
         }
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         List<Execution> executions = new ArrayList<Execution>();
         
         for(Statement statement : statements){
            Execution next = statement.compile(scope, null);
            executions.add(next);
         }
         return new ExecutionList(executions);
      }
   }
   
   private static class ExecutionList extends Execution {
      
      private final List<Execution> statements;      
      
      public ExecutionList(List<Execution> statements) {         
         this.statements = statements;
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Result result = NORMAL;
         
         for(Execution statement : statements){
            Result next = statement.execute(scope);
         
            if(!next.isNormal()){
               return next;
            }
            result = next;
         }
         return result;
      }
   }

}