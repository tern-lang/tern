package org.ternlang.compile.staticanalysis;

import static org.ternlang.core.Reserved.DEFAULT_MODULE;

import java.util.List;
import java.util.Map;

import org.ternlang.common.store.ClassPathStore;
import org.ternlang.common.store.Store;
import org.ternlang.compile.Compiler;
import org.ternlang.compile.StoreContext;
import org.ternlang.compile.StringCompiler;
import org.ternlang.core.Context;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.EmptyModel;
import org.ternlang.core.scope.Model;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeLoader;

import junit.framework.TestCase;

public class GenericTransformerTest extends TestCase {   
   
   private static final String SOURCE_1 =
   "class One<A>{\n"+
   "   func():A{\n"+
   "   }\n"+
   "}\n"+
   "class Two<A, B> extends One<B>{\n"+
   "   blah():A{\n"+
   "   }\n"+
   "}\n"+
   "class Three extends Two<String, Integer>{\n"+
   "}\n"+
   "class Four<A: Boolean> extends Two<String, A>{\n"+
   "}\n";   
       
   
   private static final String SOURCE_2 =
   "class One<A>{\n"+
   "   func():A{\n"+
   "   }\n"+
   "   boo():Map<String, A>{\n"+
   "   }\n"+
   "   basic():String{\n"+
   "   }\n"+
   "}\n"+
   "class Two<A, B> extends One<List<B>>{\n"+
   "   blah():A{\n"+
   "   }\n"+
   "}\n"+
   "class Three extends Two<Integer, List<Integer>>{\n"+
   "}\n";
   
   private static final String SOURCE_3 =
   "class Duh<K,V>{\n"+
   "   duh():K{\n"+
   "   }\n"+
   "}\n"+
   "class Blah extends Duh<String,Integer>{\n"+
   "}\n"+
   "class Foo<A,B> extends Blah{\n"+
   "}\n";
   
   public void testRedundantTransformation() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store, null);
      Compiler compiler = new StringCompiler(context);
      Model model = new EmptyModel();
      
      System.err.println(SOURCE_3);
      
      compiler.compile(SOURCE_3).execute(model, true);
      
      TypeLoader loader = context.getLoader();
      Type fromType = loader.loadType(DEFAULT_MODULE, "Foo");      
      Type toType = loader.loadType(DEFAULT_MODULE, "Duh");    
      
      Scope scope = fromType.getScope();
      Constraint constraint = Constraint.getConstraint(fromType);
      Constraint duhResult = context.getResolver().resolveInstance(scope, toType, "duh").check(scope, constraint, new Constraint[]{});
      
      assertNotNull(duhResult);
      assertNotNull(duhResult.getType(scope));
   }
   
   public void testGenericTransformation() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store, null);
      Compiler compiler = new StringCompiler(context);
      Model model = new EmptyModel();
      
      System.err.println(SOURCE_1);
      
      compiler.compile(SOURCE_1).execute(model, true);
      
      TypeLoader loader = context.getLoader();
      Type type3 = loader.loadType(DEFAULT_MODULE, "Three");
      Type type4 = loader.loadType(DEFAULT_MODULE, "Four");        
      Type type1 = loader.loadType(DEFAULT_MODULE, "One");
      Type typeInteger = loader.loadType(Integer.class);
      Type typeBoolean = loader.loadType(Boolean.class);

      Scope scope3 = type3.getScope();
      Constraint constraint3 = Constraint.getConstraint(type3);
      Constraint result3 = context.getResolver().resolveInstance(scope3, type1, "func").check(scope3, constraint3, new Constraint[]{});
      
      assertNotNull(result3);
      assertNotNull(result3.getType(scope3));
      assertEquals(result3.getType(scope3), typeInteger);   
      
      Scope scope4 = type4.getScope();
      Constraint constraint4 = Constraint.getConstraint(type4);
      Constraint result4 = context.getResolver().resolveInstance(scope4, type1, "func").check(scope4, constraint4, new Constraint[]{});
      
      assertNotNull(result4);
      assertNotNull(result4.getType(scope4));
      assertEquals(result4.getType(scope3), typeBoolean);     
   }
   
   public void testNestedGenericTransformation() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store, null);
      Compiler compiler = new StringCompiler(context);
      Model model = new EmptyModel();
      
      System.err.println(SOURCE_2);
      
      compiler.compile(SOURCE_2).execute(model, true);
      
      TypeLoader loader = context.getLoader();
      Type type3 = loader.loadType(DEFAULT_MODULE, "Three");      
      Type type1 = loader.loadType(DEFAULT_MODULE, "One");
      Type typeList = loader.loadType(List.class);
      Type typeMap = loader.loadType(Map.class);      
      Type typeInteger = loader.loadType(Integer.class);
      Type typeString = loader.loadType(String.class);      
      
      Scope scope = type3.getScope();
      Constraint constraint = Constraint.getConstraint(type3);
      Constraint funcResult = context.getResolver().resolveInstance(scope, type1, "func").check(scope, constraint, new Constraint[]{});
      
      assertNotNull(funcResult);
      assertNotNull(funcResult.getType(scope));
      assertEquals(funcResult.getType(scope), typeList);
      assertEquals(funcResult.getGenerics(scope).size(), 1);
      assertEquals(funcResult.getGenerics(scope).get(0).getType(scope), typeList);
      assertEquals(funcResult.getGenerics(scope).get(0).getGenerics(scope).size(), 1);
      assertEquals(funcResult.getGenerics(scope).get(0).getGenerics(scope).get(0).getType(scope), typeInteger);

      Constraint booResult = context.getResolver().resolveInstance(scope, type1, "boo").check(scope, constraint, new Constraint[]{});
      
      assertNotNull(booResult);
      assertNotNull(booResult.getType(scope));
      assertEquals(booResult.getType(scope), typeMap);
      assertEquals(booResult.getGenerics(scope).size(), 2);
      assertEquals(booResult.getGenerics(scope).get(0).getType(scope), typeString);
      assertEquals(booResult.getGenerics(scope).get(1).getType(scope), typeList);
      assertEquals(booResult.getGenerics(scope).get(1).getGenerics(scope).size(), 1);
      assertEquals(booResult.getGenerics(scope).get(1).getGenerics(scope).get(0).getType(scope), typeList);
      assertEquals(booResult.getGenerics(scope).get(1).getGenerics(scope).get(0).getGenerics(scope).size(), 1);         
      assertEquals(booResult.getGenerics(scope).get(1).getGenerics(scope).get(0).getGenerics(scope).get(0).getType(scope), typeInteger);    
      
      Constraint basicResult = context.getResolver().resolveInstance(scope, type1, "basic").check(scope, constraint, new Constraint[]{});
      
      assertNotNull(basicResult);
      assertNotNull(basicResult.getType(scope));
      assertEquals(basicResult.getType(scope), typeString);
   }


}
