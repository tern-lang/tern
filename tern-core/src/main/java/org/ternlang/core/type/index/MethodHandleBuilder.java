package org.ternlang.core.type.index;

import static java.lang.Integer.MAX_VALUE;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodHandleBuilder {

   private static final String ALLOWED_MODES = "allowedModes";
   
   private final Method method;

   public MethodHandleBuilder(Method method) {
      this.method = method;
   }
   
   public MethodHandle create() throws Exception {
      Class target = method.getDeclaringClass();
      
      try {
         return createDefault(target);
      } catch(Throwable e){
         return createField(target);
      }
   }

   private MethodHandle createDefault(Class target) throws Exception {
      Constructor constructor = Lookup.class.getDeclaredConstructor(Class.class, int.class);
      
      if(!constructor.isAccessible()) {
         constructor.setAccessible(true);
      }
      Lookup lookup = (Lookup)constructor.newInstance(target, MAX_VALUE);
      
      return lookup.unreflectSpecial(method, target);
   }
   
   private MethodHandle createField(Class target) throws Exception {
      Lookup lookup = MethodHandles.lookup();
      Lookup actual = lookup.in(target);
      Class access = lookup.getClass();

      try {
         Field modes = access.getDeclaredField(ALLOWED_MODES);

         if (!modes.isAccessible()) {
            modes.setAccessible(true);
         }
         modes.set(actual, Modifier.PRIVATE);
      } catch(Exception e) {

      }
      return lookup.unreflectSpecial(method, target);
   }
}