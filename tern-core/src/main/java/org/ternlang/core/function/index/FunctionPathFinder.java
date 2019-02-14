package org.ternlang.core.function.index;

import static org.ternlang.core.type.Phase.DEFINE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ternlang.common.Progress;
import org.ternlang.core.EntityCache;
import org.ternlang.core.constraint.AnyConstraint;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.TypeInspector;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Phase;
import org.ternlang.core.type.Type;

public class FunctionPathFinder {
   
   private final EntityCache<List<Type>> paths;  
   private final TypeInspector inspector;  
   private final Constraint any;
   private final long wait;
   
   public FunctionPathFinder() {
      this(60000);
   }
   
   public FunctionPathFinder(long wait) {
      this.paths = new EntityCache<List<Type>>();
      this.inspector = new TypeInspector();
      this.any = new AnyConstraint();
      this.wait = wait;
   }

   public List<Type> findPath(Type type) {
      List<Type> path = paths.fetch(type);
      
      if(path == null) {
         List<Type> result = new ArrayList<Type>();
      
         findTypes(type, result);
         paths.cache(type, result);
         
         return result;
      }
      return path;
   }

   private void findTypes(Type type, List<Type> done) {
      Progress<Phase> progress = type.getProgress();
      Scope scope = type.getScope();
      Type base = any.getType(scope);
      Class real = type.getType();
      
      if(!progress.wait(DEFINE, wait)) {
         throw new InternalStateException("Type '" + type +"' has not been defined");
      }
      findClasses(type, done);
      
      if(real == null) {
         findTraits(type, done);
      }
      done.add(base); // any is very last
   }
   
   private void findTraits(Type type, List<Type> done) {
      List<Constraint> types = type.getTypes();
      Iterator<Constraint> iterator = types.iterator();
      
      if(iterator.hasNext()) {
         Scope scope = type.getScope();
         Constraint next = iterator.next(); // next in line, i.e base
         
         while(iterator.hasNext()) {
            Constraint trait = iterator.next();
            Type match = trait.getType(scope);
            
            if(!done.contains(match)) {
               done.add(match);
            }
         }
         Type match = next.getType(scope);
         
         if(!done.contains(match)) {
            findTraits(match, done);
         }
      }
   }
   
   private void findClasses(Type type, List<Type> done) {
      List<Constraint> types = type.getTypes();
      Iterator<Constraint> iterator = types.iterator();
      Scope scope = type.getScope();
      
      if(!inspector.isProxy(type) && !inspector.isAny(type)) {
         done.add(type);
      }
      while(iterator.hasNext()) {
         Constraint next = iterator.next();
         Type match = next.getType(scope);
         
         if(!done.contains(match)) {
            findClasses(match, done);
         }
      }
   }
}