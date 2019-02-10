package tern.core.constraint.transform;

import static tern.core.scope.index.AddressType.LOCAL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import tern.core.Context;
import tern.core.MockContext;
import tern.core.Reserved;
import tern.core.attribute.Attribute;
import tern.core.constraint.ClassConstraint;
import tern.core.constraint.ClassParameterConstraint;
import tern.core.constraint.Constraint;
import tern.core.function.EmptyFunction;
import tern.core.function.FunctionSignature;
import tern.core.function.Origin;
import tern.core.function.Parameter;
import tern.core.function.Signature;
import tern.core.scope.Scope;
import tern.core.scope.index.Address;
import tern.core.scope.index.Local;
import tern.core.scope.index.ScopeTable;
import tern.core.type.Type;
import tern.core.type.TypeExtractor;

public class ConstraintProjectionTest extends TestCase {
   
   
   public static class GenericFuncBase<T>{
      public <F, A> Map<F, T> getIt(A a){
         return null;
      }
   }
   
   public static class GenericChange<A, B> extends GenericFuncBase<List<B>>{     
      public void doFoo(A a){}
   }
   
   public static class GenericRoot<X, Y> extends GenericChange<String, Y>{
      public void doBlah(X x, Y y){}
   }
   
   private Context context = new MockContext();

   public void testGenericFunctionSignatureProjection() {

      Constraint left = createConstraint(null, Map.class, GenericPair.of("X", String.class), GenericPair.of("Y", Integer.class));
      // public <F> Map<F, Integer> thisMethod()
      Scope scopeMap_FasString_String = context.getRegistry().addModule(Reserved.DEFAULT_PACKAGE).getScope().getStack();
      //Scope scope, GenericPair[] typeParams, GenericPair[] functionParams, Map<String, GenericPair> params
      Map<String, GenericPair> params=new HashMap<String, GenericPair>();
      params.put("a", GenericPair.of("A", Object.class));
      Signature signature = createSignature(
              scopeMap_FasString_String,
              new GenericPair[]{GenericPair.of("F", Object.class), GenericPair.of(null, Integer.class)},
              new GenericPair[]{GenericPair.of("F", String.class), GenericPair.of("A", String.class)},
              params);

      Attribute returnsMap_FasString_String = createAttribute(
              scopeMap_FasString_String,
              GenericFuncBase.class,
              null,
              Map.class,
              new GenericPair[]{GenericPair.of("F", Object.class), GenericPair.of(null, Integer.class)},
              new GenericPair[]{GenericPair.of("F", String.class), GenericPair.of("A", String.class)});

      ConstraintTransform transformMap_FasString_String = createTransform(GenericRoot.class, returnsMap_FasString_String);
      List<Parameter> parametersMap_FasString_String = transformMap_FasString_String.apply(left).getParameters(scopeMap_FasString_String, new EmptyFunction(signature));

      assertEquals(parametersMap_FasString_String.get(0).getName(), "a");
      assertEquals(parametersMap_FasString_String.get(0).getConstraint().getName(scopeMap_FasString_String), "A");
      assertEquals(parametersMap_FasString_String.get(0).getConstraint().getType(scopeMap_FasString_String).getType(), String.class);
   }

   public void testGenericFunctionProjection() {

      Constraint left = createConstraint(null, Map.class, GenericPair.of("X", String.class), GenericPair.of("Y", Integer.class));

      
      // public Map<F, T> thisMethod()
      Scope scopeMap_F_T = context.getRegistry().addModule(Reserved.DEFAULT_PACKAGE).getScope().getStack();
      Attribute returnsMap_F_T = createAttribute(scopeMap_F_T, GenericFuncBase.class, null, Map.class, new GenericPair[]{GenericPair.of("F", Object.class), GenericPair.of("T", Object.class)});
      ConstraintTransform transformMap_F_T = createTransform(GenericRoot.class, returnsMap_F_T);
      Constraint resultMap_F_T = transformMap_F_T.apply(left).getResult(scopeMap_F_T, returnsMap_F_T.getConstraint());      
      assertEquals(resultMap_F_T.getType(scopeMap_F_T).getType(), Map.class);
      assertEquals(resultMap_F_T.getGenerics(scopeMap_F_T).size(), 2);
      assertNull(resultMap_F_T.getGenerics(scopeMap_F_T).get(0).getType(scopeMap_F_T));
      assertEquals(resultMap_F_T.getGenerics(scopeMap_F_T).get(0).getName(scopeMap_F_T), "F");
      assertEquals(resultMap_F_T.getGenerics(scopeMap_F_T).get(1).getType(scopeMap_F_T).getType(), List.class);
      assertEquals(resultMap_F_T.getGenerics(scopeMap_F_T).get(1).getGenerics(scopeMap_F_T).get(0).getType(scopeMap_F_T).getType(), Integer.class);
      
      
      // public Map<F, String> thisMethod()
      Scope scopeMap_F_String = context.getRegistry().addModule(Reserved.DEFAULT_PACKAGE).getScope().getStack();
      Attribute returnsMap_F_String = createAttribute(scopeMap_F_String, GenericFuncBase.class, null, Map.class, new GenericPair[]{GenericPair.of("F", Object.class), GenericPair.of(null, String.class)});
      ConstraintTransform transformMap_F_String = createTransform(GenericRoot.class, returnsMap_F_String);
      Constraint resultMap_F_String = transformMap_F_String.apply(left).getResult(scopeMap_F_String, returnsMap_F_String.getConstraint());      
      assertEquals(resultMap_F_String.getType(scopeMap_F_String).getType(), Map.class);
      assertEquals(resultMap_F_String.getGenerics(scopeMap_F_String).size(), 2);      
      assertNull(resultMap_F_String.getGenerics(scopeMap_F_String).get(0).getType(scopeMap_F_String));
      assertEquals(resultMap_F_String.getGenerics(scopeMap_F_String).get(0).getName(scopeMap_F_String), "F");      
      assertEquals(resultMap_F_String.getGenerics(scopeMap_F_String).get(1).getType(scopeMap_F_String).getType(), String.class);

      
      // public <F> Map<F, Integer> thisMethod()
      Scope scopeMap_FasString_String = context.getRegistry().addModule(Reserved.DEFAULT_PACKAGE).getScope().getStack();
      Attribute returnsMap_FasString_String = createAttribute(scopeMap_FasString_String, GenericFuncBase.class, null, Map.class, new GenericPair[]{GenericPair.of("F", Object.class), GenericPair.of(null, Integer.class)}, GenericPair.of("F", String.class));
      ConstraintTransform transformMap_FasString_String = createTransform(GenericRoot.class, returnsMap_FasString_String);
      Constraint resultMap_FasString_String = transformMap_FasString_String.apply(left).getResult(scopeMap_FasString_String, returnsMap_FasString_String.getConstraint());      
      assertEquals(resultMap_FasString_String.getType(scopeMap_FasString_String).getType(), Map.class);
      assertEquals(resultMap_FasString_String.getGenerics(scopeMap_FasString_String).size(), 2);      
      assertEquals(resultMap_FasString_String.getGenerics(scopeMap_FasString_String).get(0).getType(scopeMap_FasString_String).getType(), String.class);
      assertEquals(resultMap_FasString_String.getGenerics(scopeMap_FasString_String).get(0).getName(scopeMap_FasString_String), "F");      
      assertEquals(resultMap_FasString_String.getGenerics(scopeMap_FasString_String).get(1).getType(scopeMap_FasString_String).getType(), Integer.class);
      
      System.err.println(resultMap_F_T);
      System.err.println(resultMap_F_String);
      System.err.println(resultMap_FasString_String);
   }
   
   public void testHashMapProjection() {
      // Map<String, Integer>
      Constraint left = createConstraint(null, Map.class, new GenericPair[]{GenericPair.of("K", String.class), GenericPair.of("V", Integer.class)});

      // public List<V> getMap()
      Scope scopeV = context.getRegistry().addModule(Reserved.DEFAULT_PACKAGE).getScope().getStack();
      Attribute returnsV = createAttribute(scopeV, Map.class, null, List.class,  new GenericPair[]{GenericPair.of("V", Object.class)});
      ConstraintTransform transformV = createTransform(HashMap.class, returnsV);
      ConstraintRule ruleV = transformV.apply(left);      
      Constraint resultV = ruleV.getResult(scopeV, returnsV.getConstraint());
      
      assertEquals(resultV.getType(scopeV).getType(), List.class);
      assertEquals(resultV.getGenerics(scopeV).size(), 1);
      assertEquals(resultV.getGenerics(scopeV).get(0).getType(scopeV).getType(), Integer.class);
      
      
      // public List<K> getMap()
      Scope scopeK = context.getRegistry().addModule(Reserved.DEFAULT_PACKAGE).getScope().getStack();
      Attribute returnsK = createAttribute(scopeK, Map.class, null, List.class,  new GenericPair[]{GenericPair.of("K", Object.class)});
      ConstraintTransform transformK = createTransform(HashMap.class, returnsK);
      ConstraintRule ruleK = transformK.apply(left); 
      Constraint resultK = ruleK.getResult(scopeK, returnsK.getConstraint());
      
      assertEquals(resultK.getType(scopeK).getType(), List.class);
      assertEquals(resultK.getGenerics(scopeK).size(), 1);
      assertEquals(resultK.getGenerics(scopeK).get(0).getType(scopeK).getType(), String.class);
      System.err.println(resultV);
      System.err.println(resultK);
   }
   
   private ConstraintTransform createTransform(Class from, Attribute attribute) {
      TypeExtractor extractor = context.getExtractor();
      ConstraintTransformer transformer = new ConstraintTransformer(extractor);
      
      return transformer.transform(   
            context.getLoader().loadType(from),
            attribute);
   }

   private Signature createSignature(Scope scope, GenericPair[] typeParams, GenericPair[] functionParams, Map<String, GenericPair> params){
      List<Constraint> generics = createConstraintList(functionParams);
      List<Parameter> parameters = createParameterList(params);
      ScopeTable table = scope.getTable();

      for(int i = 0; i < generics.size(); i++){
         Constraint generic = generics.get(i);
         String key = generic.getName(scope);
         Local value = Local.getConstant(generic, key);
         Address address = LOCAL.getAddress(key, i);
         
         table.addValue(address, value);
      }
       return new FunctionSignature(parameters, generics, scope.getModule(), null, Origin.DEFAULT, true);
   }

   private Attribute createAttribute(Scope scope, Class owner, String name, Class type, GenericPair[] typeParams, GenericPair... functionParams){
      Type source = context.getLoader().loadType(owner);
      Constraint constraint = createConstraint(name, type, typeParams);
      List<Constraint> generics = createConstraintList(functionParams);
      ScopeTable table = scope.getTable();
      
      for(int i = 0; i < generics.size(); i++){
         Constraint generic = generics.get(i);  
         String key = generic.getName(scope);
         Local value = Local.getConstant(generic, key);
         Address address = LOCAL.getAddress(key, i);
         
         table.addValue(address, value);
      }
      return new GenericAttribute(source, generics, constraint);
   }  
   
   private static Constraint createConstraint(String name, Class type, GenericPair... generics){
      List<Constraint> parameters = createConstraintList(generics);
      
      if(name != null) {
         return new ClassParameterConstraint(type, name);
      }
      return new ClassConstraint(type, parameters);            
   }
   private static List<Parameter> createParameterList(Map<String, GenericPair> params){
      List<Parameter> parameters = new ArrayList<Parameter>();

      for(Map.Entry<String, GenericPair> entry : params.entrySet()) {
         String name = entry.getKey();
         GenericPair pair = entry.getValue();
         int index = parameters.size();
         Constraint constraint = null;

         if(pair.name != null) {
            constraint= new ClassParameterConstraint(pair.type, pair.name);
         } else {
            constraint = new ClassConstraint(pair.type);
         }
         parameters.add(new Parameter(name, constraint, index, false));
      }
      return parameters;
   }
   private static List<Constraint> createConstraintList(GenericPair... generics){
      List<Constraint> parameters = new ArrayList<Constraint>();
      
      for(GenericPair pair : generics) {
         if(pair.name != null) {
            Constraint parameter = new ClassParameterConstraint(pair.type, pair.name);         
            parameters.add(parameter);
         } else {
            Constraint parameter = new ClassConstraint(pair.type);         
            parameters.add(parameter);
         }
         
      }
      return parameters;
   } 
   private static class GenericAttribute implements Attribute{
      
      private final List<Constraint> generics;
      private final Constraint constraint;
      private final Type owner;
      
      public GenericAttribute(Type owner, List<Constraint> generics, Constraint constraint){
         this.generics = generics;
         this.constraint = constraint;
         this.owner = owner;
      }
      
      @Override
      public List<Constraint> getGenerics() {
         return generics;
      }

      @Override
      public Type getHandle() {
         return null;
      }

      @Override
      public String getName() {
         return null;
      }

      @Override
      public Type getSource() {
         return owner;
      }

      @Override
      public Constraint getConstraint() {
         return constraint;
      }

      @Override
      public int getModifiers() {
         return 0;
      }
      
   }
   
   private static class GenericPair{
      
      public static GenericPair of(String name, Class type) {
         return new GenericPair(type, name);
      }
      
      private final String name;
      private final Class type;
      
      public GenericPair(Class type, String name) {
         this.type = type;
         this.name = name;
      }
   }

   
   

}
