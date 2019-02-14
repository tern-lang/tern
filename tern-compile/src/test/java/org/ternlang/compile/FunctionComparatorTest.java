package org.ternlang.compile;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import junit.framework.TestCase;

import org.ternlang.common.store.ClassPathStore;
import org.ternlang.core.convert.ConstraintMatcher;
import org.ternlang.core.convert.FunctionComparator;
import org.ternlang.core.convert.Score;
import org.ternlang.core.function.Function;
import org.ternlang.core.scope.Scope;

public class FunctionComparatorTest extends TestCase {
   
   private static final String SOURCE_1 =
   "import org.ternlang.compile.FunctionComparatorTest.X;\n"+
   "class ExtendX extends X {\n"+
   "   override func(array: Byte[], off: Integer, length: Integer){}\n"+
   "}\n"+
   "println('type='+ExtendX.class);";

   private static final String SOURCE_2 =
   "import org.ternlang.compile.FunctionComparatorTest.Y;\n"+
   "import java.awt.Graphics;\n"+
   "class ExtendY extends Y {\n"+
   "   override func(g: Graphics){}\n"+
   "}\n"+
   "println('type='+ExtendY.class);";

   private static final String SOURCE_3 =
   "import org.ternlang.compile.FunctionComparatorTest.Z;\n"+
   "class ExtendZ extends Z {\n"+
   "   override func(s: String, i: Integer){}\n"+
   "}\n"+
   "println('type='+ExtendZ.class);";
   
   public static class X {
      public void func(byte[] array, int off, int length) {}
   }
   
   public static class Y {
      public void func(Graphics g) {}
   }
   
   public static class Z {
      public void func(String string, Integer value) {}
   }
   
   public void testPrimitiveTypeFunctions() throws Exception {
      ClassPathStore store = new ClassPathStore();
      StoreContext context = new StoreContext(store);
      ConstraintMatcher matcher = context.getMatcher();
      FunctionComparator comparator = new FunctionComparator(matcher);
      Compiler compiler = new StringCompiler(context);
      
      compiler.compile(SOURCE_1).execute();
      
      Function extendX = context.getLoader().loadType("default.ExtendX").getFunctions().get(0);
      Function x = context.getLoader().loadType("org.ternlang.compile.FunctionComparatorTest$X").getFunctions().get(0);
      
      Scope scope = x.getSource().getScope();
      Score score = comparator.compare(scope, extendX, x);
      Score scoreExtendX = extendX.getSignature().getConverter().score(new byte[10], 1, 1);
      Score scoreX = extendX.getSignature().getConverter().score(new byte[10], 1, 1);
      
      assertEquals(scoreExtendX.getScore(), scoreX.getScore());
      
      System.err.println(extendX);
      System.err.println(x);
      System.err.println(score);
      System.err.println(scoreExtendX);
      System.err.println(scoreX);
   }
   

   public void testSingleArgument() throws Exception {
      ClassPathStore store = new ClassPathStore();
      StoreContext context = new StoreContext(store);
      ConstraintMatcher matcher = context.getMatcher();
      FunctionComparator comparator = new FunctionComparator(matcher);
      Compiler compiler = new StringCompiler(context);
      
      compiler.compile(SOURCE_2).execute();
      
      Function extendY = context.getLoader().loadType("default.ExtendY").getFunctions().get(0);
      Function y = context.getLoader().loadType("org.ternlang.compile.FunctionComparatorTest$Y").getFunctions().get(0);
      
      BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
      Graphics2D graphics = image.createGraphics();
      
      Scope scope = y.getSource().getScope();
      Score score = comparator.compare(scope, extendY, y);
      Score scoreExtendY = extendY.getSignature().getConverter().score(graphics);
      Score scoreY = extendY.getSignature().getConverter().score(graphics);
      
      assertEquals(scoreExtendY.getScore(), scoreY.getScore());
      
      System.err.println(extendY);
      System.err.println(y);
      System.err.println(score);
      System.err.println(scoreExtendY);
      System.err.println(scoreY);
   }
   
   public void testMultipleArgument() throws Exception {
      ClassPathStore store = new ClassPathStore();
      StoreContext context = new StoreContext(store);
      ConstraintMatcher matcher = context.getMatcher();
      FunctionComparator comparator = new FunctionComparator(matcher);
      Compiler compiler = new StringCompiler(context);
      
      compiler.compile(SOURCE_3).execute();
      
      Function extendZ = context.getLoader().loadType("default.ExtendZ").getFunctions().get(0);
      Function z = context.getLoader().loadType("org.ternlang.compile.FunctionComparatorTest$Z").getFunctions().get(0);
      
      Scope scope = z.getSource().getScope();
      Score score = comparator.compare(scope, extendZ, z);
      Score scoreExtendZ = extendZ.getSignature().getConverter().score("text", 1);
      Score scoreZ = extendZ.getSignature().getConverter().score("text", 1);
      
      assertEquals(scoreExtendZ.getScore(), scoreZ.getScore());
      
      System.err.println(extendZ);
      System.err.println(z);
      System.err.println(score);
      System.err.println(scoreExtendZ);
      System.err.println(scoreZ);
   }
}
