package org.ternlang.tree.construct;

import java.util.Map.Entry;

import org.ternlang.core.Evaluation;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

public class MapEntry {
   
   private final Evaluation value;
   private final Evaluation key;
   
   public MapEntry(Evaluation key, Evaluation value) {
      this.value = value;
      this.key = key;
   }
   
   public void define(Scope scope) throws Exception{
      key.define(scope);
      value.define(scope);
   }
   
   public void compile(Scope scope) throws Exception{
      key.compile(scope, null);
      value.compile(scope, null);
   }
   
   public Entry create(Scope scope) throws Exception{
      Value valueResult = value.evaluate(scope, null);
      Value keyResult = key.evaluate(scope, null);
      Object valueObject = valueResult.getValue();
      Object keyObject = keyResult.getValue();
      
      return new Pair(keyObject, valueObject);
   }
   
   private class Pair implements Entry {
      
      private final Object value;
      private final Object key;
      
      public Pair(Object key, Object value) {
         this.value = value;
         this.key = key;
      }

      @Override
      public Object getKey() {
         return key;
      }

      @Override
      public Object getValue() {
         return value;
      }

      @Override
      public Object setValue(Object value) {
         throw new InternalStateException("Modification of constant entry '" + key + "'");
      }
   }
}