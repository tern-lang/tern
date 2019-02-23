package org.ternlang.tree.collection;

import static org.ternlang.core.constraint.Constraint.CHARACTER;
import static org.ternlang.core.constraint.Constraint.NONE;
import static org.ternlang.core.variable.Value.NULL;

import java.util.List;
import java.util.Map;

import org.ternlang.core.Context;
import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.error.InternalArgumentException;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.variable.ListValue;
import org.ternlang.core.variable.MapValue;
import org.ternlang.core.variable.StringValue;
import org.ternlang.core.variable.Value;
import org.ternlang.tree.Argument;

public class CollectionIndex extends Evaluation {
   
   private final CollectionConverter converter;    
   private final Argument argument;
  
   public CollectionIndex(Argument argument) {
      this.converter = new CollectionConverter();
      this.argument = argument;
   }

   @Override
   public void define(Scope scope) throws Exception {
      argument.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Constraint index = argument.compile(scope, null);
      Type type = left.getType(scope);
      
      if(type != null) {
         Type entry = type.getEntry();
         Class real = type.getType();

         if(entry != null) { // is this a compile error?
            return Constraint.getConstraint(entry);
         }
         if(real == String.class) {
            return CHARACTER;
         }
      }
      return NONE;
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      Value index = argument.evaluate(scope, NULL);
      Object object = left.getValue();
      
      if(index == null) {
         throw new InternalArgumentException("Illegal index with null");
      }
      if(object == null) {
         throw new InternalArgumentException("Illegal index of null");
      }
      return index(scope, object, index);
   }
   
   private Value index(Scope scope, Object object, Value index) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      Object source = converter.convert(object);
      Class type = object.getClass();
      
      if(List.class.isInstance(source)) {
         int number = index.getInteger();
         List list = (List)source;
         
         return new ListValue(wrapper, list, number);
      }
      if(Map.class.isInstance(source)) {
         Object key = index.getValue();
         Map map = (Map)source;
         
         return new MapValue(wrapper, map, key);
      }
      if(String.class.isInstance(source)) {
         int number = index.getInteger();
         String text = (String)source;

         return new StringValue(text, number);
      }
      if(Type.class.isInstance(type)) {
         throw new InternalArgumentException("Illegal index of type " + object);
      }
      throw new InternalArgumentException("Illegal index of " + type);
   }
}