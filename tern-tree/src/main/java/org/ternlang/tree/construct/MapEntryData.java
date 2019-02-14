package org.ternlang.tree.construct;

import static org.ternlang.core.constraint.Constraint.NONE;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.ternlang.core.Context;
import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;

public class MapEntryData extends Evaluation{
   
   private final MapEntry[] list;
   
   public MapEntryData(MapEntry... list) {
      this.list = list;
   }
   
   @Override
   public void define(Scope scope) throws Exception{
      for(int i = 0; i < list.length; i++){
         list[i].define(scope);
      }
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      for(int i = 0; i < list.length; i++){
         list[i].compile(scope);
      }
      return NONE;
   }
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception{
      Map map = new LinkedHashMap();
      
      for(int i = 0; i < list.length; i++){
         Entry entry = list[i].create(scope);
         Module module = scope.getModule();
         Context context = module.getContext();
         ProxyWrapper wrapper = context.getWrapper();
         Object key = entry.getKey();
         Object value = entry.getValue();
         Object keyProxy = wrapper.toProxy(key);
         Object valueProxy = wrapper.toProxy(value);
         
         map.put(keyProxy, valueProxy);
      }
      return Value.getTransient(map);
   }
}