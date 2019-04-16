package org.ternlang.compile.perf;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.ternlang.common.store.ClassPathStore;
import org.ternlang.common.store.Store;
import org.ternlang.compile.StoreContext;
import org.ternlang.core.Context;
import org.ternlang.core.Evaluation;
import org.ternlang.core.NoStatement;
import org.ternlang.core.Reserved;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.FunctionSignature;
import org.ternlang.core.function.Origin;
import org.ternlang.core.function.Parameter;
import org.ternlang.core.function.Signature;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.index.Address;
import org.ternlang.core.scope.index.AddressType;
import org.ternlang.core.scope.index.ScopeTable;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.function.ScopeCalculator;
import org.ternlang.tree.literal.TextLiteral;
import org.ternlang.tree.variable.Variable;

import junit.framework.TestCase;

public class ScopeCalculatorPerfTest extends TestCase {
   
   private static final int ITERATIONS = 10000000; // 10 million   
 
   public void testScopeCalculator() throws Exception {
      resolve("fun", "a").timeCalculation(ITERATIONS, 20);
      resolve("fun", "a", "b").timeCalculation(ITERATIONS, 20, 1);
      resolve("fun", "a", "b", "c").timeCalculation(ITERATIONS, 20, 32, 3);
      resolve("fun", "a", "b").timeCalculation(ITERATIONS, 20, 1);
   }

   private static ScopeContext resolve(String function, String... args) throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      Module module = context.getRegistry().addModule(Reserved.DEFAULT_MODULE);
      Scope scope = module.getScope();
      Scope stack = scope.getStack();

      Path path = new Path("/script.tern");
      NoStatement noop = new NoStatement();
      List<Parameter> parameters = new ArrayList<Parameter>();
      
      for(int i = 0; i < args.length; i++) {
         final TextLiteral name = new TextLiteral(new StringToken(args[i]));
         final Variable variable = new Variable(name);
         final Evaluation evaluation = variable.compile(module, path, 1);
         final Address address = stack.getIndex().index(args[i]);
         final Parameter parameter = new Parameter(args[i], Constraint.NONE, i, true);
         
         stack.getTable().addValue(address, Value.NULL);
         evaluation.define(stack);
         evaluation.compile(stack, Constraint.NONE);
         parameters.add(parameter);
      }
      Signature signature = new FunctionSignature(parameters, Collections.EMPTY_LIST, module, null, Origin.DEFAULT, true);
      ScopeCalculator calculator = new ScopeCalculator(signature, null, noop);
      
      return new ScopeContext(calculator, context, scope, args);
   }
   
   private static class ScopeContext {
      
      private final ScopeCalculator calculator;
      private final DecimalFormat format;
      private final Context context;
      private final Scope scope;
      private final String[] names;
 
      public ScopeContext(ScopeCalculator calculator, Context context, Scope scope, String... names) {
         this.format = new DecimalFormat("###,###,###,###,###,###,###");
         this.calculator = calculator;
         this.context = context;
         this.scope = scope;
         this.names = names;
      }
      
      public void timeCalculation(int count, Object... arguments) {
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
            calculator.define(stack);
            calculator.compile(stack);
            
            long start = System.currentTimeMillis();
            
            for(int i = 0; i < count; i++) {
               calculator.calculate(stack, arguments);
            }
            long finish = System.currentTimeMillis();
            double duration = finish - start;
            double perMillis = count / duration;
            long perSecond = Math.round(perMillis * 1000);
            
            System.err.println(duration);
            System.err.println(format.format(perSecond) + " calculations per second -> " + Arrays.toString(arguments));
         } catch(Exception e) {
            e.printStackTrace();
         }
      }
   }
}
