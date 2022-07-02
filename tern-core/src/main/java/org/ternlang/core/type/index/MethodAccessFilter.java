package org.ternlang.core.type.index;

import org.ternlang.common.Predicate;

import java.lang.reflect.Method;
import java.util.*;

public class MethodAccessFilter implements Predicate<Method> {

   public static final String SEPARATOR = "::";

   private volatile Map<Class, Set<String>> matches;
   private volatile String[] filters;

   public MethodAccessFilter(String... filters) {
      this.matches = Collections.EMPTY_MAP;
      this.filters = filters;
   }

   @Override
   public boolean accept(Method method) {
      if (!method.isAccessible()) {
         String name = method.getName();
         Class parent = method.getDeclaringClass();
         Set<String> methods = compile().get(parent);

         if(methods != null) {
            return !methods.contains(name);
         }
      }
      return true;
   }

   private Map<Class, Set<String>> compile() {
      if(matches.isEmpty()) {
         Map<Class, Set<String>> local = new HashMap<>();

         for(String filter : filters) {
            String[] parts = filter.split(SEPARATOR);

            try {
               Class type = Class.forName(parts[0]);
               Set<String> names = local.get(type);

               if (names == null) {
                  names = new HashSet<>();
                  local.put(type, names);
               }
               names.add(parts[1]);
            } catch(Throwable cause) {
               throw new IllegalStateException("Could not resolve " + filter, cause);
            }
         }
         matches = local;
      }
      return matches;
   }
}
