package org.ternlang.compile.perf;

import java.math.BigDecimal;

import junit.framework.TestCase;

import org.ternlang.compile.ClassPathCompilerBuilder;
import org.ternlang.compile.Compiler;
import org.ternlang.compile.Executable;
import org.ternlang.compile.Timer;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.math.NumberOperator;

public class FibTest extends TestCase {

   private static final String SOURCE_1=
   "function fib(n) {\n"+
   "   if (n<2) {\n"+
   "      return n;\n"+
   "   }\n"+
   "   return fib(n-1) + fib(n-2);\n"+
   "}\n"+
   "println(fib(30));\n";
   
   private static final String SOURCE_2=
   "class Fibonacci {\n"+
   "   calc(n) {\n"+
   "      if (n<2) {\n"+
   "         return n;\n"+
   "      }\n"+
   "      return calc(n-1) + calc(n-2);\n"+
   "   }\n"+
   "}\n"+
   "println(Fibonacci().calc(30));\n";

   //time=1498 memory=1,933,564,016
   //time=1514 memory=1,933,196,408
   //time=1498 memory=1,933,298,408
   //time=1497 memory=1,933,550,080
   //time=1502 memory=1,933,420,704
   //time=1517 memory=1,933,574,016
   
   //time=1619 memory=1,769,799,896
   //time=1572 memory=1,769,659,520
   
   //time=1263 memory=1,586,663,120
   //time=1279 memory=1,586,655,976
   //time=1299 memory=1,586,667,584
   //time=1310 memory=1,586,589,152
   
   //time=921 memory=1,529,425,864
   //time=772 memory=1,314,500,176
   //time=769 memory=1,314,022,936
   //time=703 memory=1,550,965,720 --> cache function connections
   //time=672 memory=1,550,965,720
   //time=608 memory=1,550,965,744 --> cache result of closure search
   //time=569 memory=925,192,344 --> Create new local scope only if needed
   public void testFib() throws Exception {
      runFib("script", SOURCE_1);
      runFib("class", SOURCE_2);
   }
   
   private static void runFib(String name, String source) throws Exception {
      System.err.println(source);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      Executable executable = compiler.compile(source);

      for(int i = 0; i < 10; i++) {
         Timer.timeExecution(name, executable);
      }
   }
   
   
   public void testNumericConverter() throws Exception {
      Timer.timeExecution("testNumericConverter", new Runnable() {
         @Override
         public void run() {
            for(int i = 0; i < 2692537; i++) {
               NumberOperator.MULTIPLY.operate(Value.getTransient(i), Value.getTransient(i));
            }
         }
      });
   }
   
   public void testMemoryAllocate() throws Exception {
      Timer.timeExecution("testMemoryAllocate", new Runnable() {
         @Override
         public void run() {
            double i, maxim = 1933564016/2; // 2 billion
            for(i = 0; i < maxim; i++) {
               Object b = new byte[12];
               b.getClass();
               new Double(i).hashCode();
               //String.valueOf(i).hashCode();
            }
         }
      });
   }
   
//   public void testBigDecimal() throws Exception {
//      Timer.timeExecution(new Runnable() {
//         @Override
//         public void run() {
//            System.err.println(fib(BigDecimal.valueOf(30)));
//         }
//      });
//   }

   public static void main(String[] list) throws Exception {
      new FibTest().testNumericConverter();
      new FibTest().testFib();
      new FibTest().testMemoryAllocate();
   }
   
   private static final BigDecimal ONE = BigDecimal.valueOf(1);
   private static final BigDecimal TWO = BigDecimal.valueOf(2);
   
   private static final BigDecimal fib(BigDecimal dec) {
      if(dec.compareTo(TWO) < 0){
         return ONE;
      }
      BigDecimal left = fib(dec.subtract(ONE));
      BigDecimal right = fib(dec.subtract(TWO));
      return left.add(right);
   }
   
   static class SomeRandomClass {
//      @Override
//      public int hashCode(){
//         return 1;
//      }
   }
}
