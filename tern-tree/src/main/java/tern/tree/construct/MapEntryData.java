package tern.tree.construct;

import static tern.core.constraint.Constraint.NONE;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import tern.core.Context;
import tern.core.Evaluation;
import tern.core.constraint.Constraint;
import tern.core.convert.proxy.ProxyWrapper;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.variable.Value;

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