package tern.core.function.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tern.core.function.Function;
import tern.core.type.Type;

public class FunctionIndexPartition {

   private final Map<String, FunctionIndexGroup> groups;
   private final Set<FunctionPointer> pointers;
   private final FunctionKeyBuilder builder;
   private final FunctionReducer matcher;
   
   public FunctionIndexPartition(FunctionReducer matcher, FunctionKeyBuilder builder) {
      this.groups = new HashMap<String, FunctionIndexGroup>();
      this.pointers = new HashSet<FunctionPointer>();
      this.matcher = matcher;
      this.builder = builder;
   }
   
   public FunctionPointer resolve(String name, Type... list) throws Exception {
      FunctionIndexGroup group = groups.get(name);
      
      if(group != null) {
         return group.resolve(list);
      }
      return null;
   }
   
   public FunctionPointer resolve(String name, Object... list) throws Exception {
      FunctionIndexGroup group = groups.get(name);
      
      if(group != null) {
         return group.resolve(list);
      }
      return null;
   }
   
   public List<FunctionPointer> resolve(int modifiers) {
      List<FunctionPointer> matches = new ArrayList<FunctionPointer>();
      
      for(FunctionPointer pointer : pointers){
         Function function = pointer.getFunction();
         int mask = function.getModifiers();
         
         if((modifiers & mask) == modifiers) {
            matches.add(pointer);
         }
      }
      return matches;
   }
   
   public void index(FunctionPointer pointer) throws Exception {
      Function function = pointer.getFunction();
      String name = function.getName();
      FunctionIndexGroup group = groups.get(name);
      
      if(group == null) {
         group = new FunctionIndexGroup(matcher, builder, name);
         groups.put(name, group);
      }
      pointers.add(pointer);
      group.index(pointer);
   }
}