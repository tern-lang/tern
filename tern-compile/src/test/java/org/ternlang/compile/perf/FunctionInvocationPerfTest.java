package org.ternlang.compile.perf;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ternlang.common.store.ClassPathStore;
import org.ternlang.common.store.Store;
import org.ternlang.compile.StoreContext;
import org.ternlang.compile.StringCompiler;
import org.ternlang.core.Context;
import org.ternlang.core.Evaluation;
import org.ternlang.core.Reserved;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.MapModel;
import org.ternlang.core.scope.Model;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.index.Address;
import org.ternlang.core.scope.index.AddressType;
import org.ternlang.core.scope.index.ScopeTable;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.Argument;
import org.ternlang.tree.ArgumentList;
import org.ternlang.tree.constraint.GenericList;
import org.ternlang.tree.function.FunctionInvocation;
import org.ternlang.tree.literal.TextLiteral;
import org.ternlang.tree.variable.Variable;

import junit.framework.TestCase;

public class FunctionInvocationPerfTest extends TestCase {
   
   private static final int ITERATIONS = 10000000; // 10 million
   
   private static final String SOURCE_1 =
   "func fun(n) {\n"+
   "   return 1;\n"+
   "}\n";

   private static final String SOURCE_2 =
   "func fun() {\n"+
   "   return 1;\n"+
   "}\n";
   
   private static final String SOURCE_3 =
   "func fun(n) {\n"+
   "   return 1;\n"+
   "}\n"+         
   "func fun(a, b, c) {\n"+
   "   return 1;\n"+
   "}\n";
 
   public void testInvocationCache() throws Exception {
      resolve(SOURCE_1, "fun", "n").timeInvocation(ITERATIONS, 20);
      resolve(SOURCE_2, "fun").timeInvocation(ITERATIONS);
      resolve(SOURCE_3, "fun", "a", "b", "c").timeInvocation(ITERATIONS, 20, "ok", 1233L);
   }
   
   private static InvocationContext resolve(String source, String function, String... args) throws Exception {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Model model = new MapModel(map);
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      StringCompiler compiler = new StringCompiler(context);
      compiler.compile(source).execute(model);
      Module module = context.getRegistry().addModule(Reserved.DEFAULT_MODULE);
      Scope scope = module.getScope();
      Scope stack = scope.getStack();
      
      GenericList list = new GenericList() {

         @Override
         public List<Constraint> getGenerics(Scope scope) throws Exception {
            return Collections.emptyList();
         }
      };
      Path path = new Path("/script.tern");
      StringToken token = new StringToken(function);
      TextLiteral literal = new TextLiteral(token);
      Argument[] values = new Argument[args.length];
  
      for(int i = 0; i < values.length; i++) {
         final TextLiteral name = new TextLiteral(new StringToken(args[i]));
         final Variable variable = new Variable(name);
         final Evaluation evaluation = variable.compile(module, path, 1);
         final Address address = stack.getIndex().index(args[i]);
         
         stack.getTable().addValue(address, Value.NULL);
         evaluation.define(stack);
         evaluation.compile(stack, Constraint.NONE);
         
         values[i] = new Argument(evaluation);
      }
      ArgumentList arguments = new ArgumentList(values);
      FunctionInvocation invocation = new FunctionInvocation(literal, list, arguments);
      Evaluation evaluation = invocation.compile(module, path, 1);
      
      return new InvocationContext(evaluation, context, scope, args);
   }
   
   private static class InvocationContext {
      
      private final DecimalFormat format;
      private final Evaluation evaluation;
      private final Context context;
      private final Scope scope;
      private final String[] names;
 
      public InvocationContext(Evaluation evaluation, Context context, Scope scope, String... names) {
         this.format = new DecimalFormat("###,###,###,###,###,###,###");
         this.evaluation = evaluation;
         this.context = context;
         this.scope = scope;
         this.names = names;
      }
      
      public void timeInvocation(int count, Object... arguments) {
         try {
            if(arguments.length != names.length) {
               throw new IllegalArgumentException("Arguments don't match signature");
            }
            Scope stack = scope.getScope();
            ScopeTable table = stack.getTable();
            
            for(int i = 0; i < names.length; i++) {
               Address address = new Address(AddressType.LOCAL, names[i], i);
               Value value = Value.getTransient(arguments[i]);
               
               table.addValue(address, value);
            }
            long start = System.currentTimeMillis();
            
            for(int i = 0; i < count; i++) {
               evaluation.evaluate(stack, Value.NULL);
            }
            long finish = System.currentTimeMillis();
            double duration = finish - start;
            double perMillis = count / duration;
            long perSecond = Math.round(perMillis * 1000);
            
            System.err.println(duration);
            System.err.println(format.format(perSecond) + " invocations per second -> " + Arrays.toString(arguments));
         } catch(Exception e) {
            e.printStackTrace();
         }
      }
   }
}