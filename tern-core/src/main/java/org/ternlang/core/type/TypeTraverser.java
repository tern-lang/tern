package org.ternlang.core.type;

import static org.ternlang.core.type.Phase.DEFINE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.ternlang.common.Progress;
import org.ternlang.core.EntityCache;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;

public class TypeTraverser {
   
   private final EntityCache<Set<Type>> types;
   
   public TypeTraverser() {
      this.types = new EntityCache<Set<Type>>();
   }   

   public Set<Type> findHierarchy(Type type) {
      Set<Type> list = types.fetch(type);
      
      if(list == null) {
         Progress<Phase> progress = type.getProgress();
         Set<Type> hierarchy = findHierarchy(type, type);
         
         if(progress.pass(DEFINE)) {
            types.cache(type, hierarchy);
         }
         return hierarchy;
      }
      return list;
   }
   
   private Set<Type> findHierarchy(Type root, Type type) {
      Set<Type> list = new LinkedHashSet<Type>();
      
      if(type != null) {
         findHierarchy(root, type, list);
      }
      return Collections.unmodifiableSet(list);
   }
   
   private Set<Type> findHierarchy(Type root, Type type, Set<Type> list) {
      List<Constraint> types = type.getTypes();
      Scope scope = type.getScope();
      
      if(list.add(type)) {
         for(Constraint base : types) {
            if(base != null) {
               Type match = base.getType(scope);

               if (match == root) {
                  throw new InternalStateException("Hierarchy for '" + type + "' contains a cycle");
               }
               findHierarchy(root, match, list);
            }
         }
      }
      return list;
   }
   
   public Type findEnclosing(Type type, String name) {
      Set<Type> done = new LinkedHashSet<Type>();
      
      if(type != null) {
         return findEnclosing(type, name, done);
      }
      return null;
   }
   
   private Type findEnclosing(Type type, String name, Set<Type> done) {
      Module module = type.getModule();
      
      while(type != null){ // search outer classes
         String prefix = type.getName();
         Type result = module.getType(prefix + "$"+name);
         
         if(result == null) {
            result = findHierarchy(type, name, done);
         }
         if(result != null) {
            return result;
         }
         type = type.getOuter();
      }
      return null;
   }
   
   private Type findHierarchy(Type type, String name, Set<Type> done) {
      List<Constraint> types = type.getTypes(); // do not use extractor here
      Scope scope = type.getScope();
      
      for(Constraint base : types) {
         if(base != null) {
            Type match = base.getType(scope);

            if (done.add(match)) { // avoid loop
               Type result = findEnclosing(match, name, done);

               if (result != null) {
                  return result;
               }
            }
         }
      }
      return null;
   }
   
   public List<Constraint> findPath(Type constraint, Type require) {
      List<Constraint> path = new ArrayList<Constraint>();

      findPath(constraint, require, path);
      Collections.reverse(path);

      return path;
   }
   
   private boolean findPath(Type constraint, Type require, List<Constraint> path) {
      Scope scope = require.getScope();
      
      if(constraint != require) {
         List<Constraint> types = constraint.getTypes();
         
         for(Constraint base : types) {
            if(base != null) {
               Type match = base.getType(scope);

               if (findPath(match, require, path)) {
                  path.add(base);
                  return true;
               }
            }
         }
         return false;
      }
      return true;
   }
}