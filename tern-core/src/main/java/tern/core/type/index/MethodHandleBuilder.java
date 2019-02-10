package tern.core.type.index;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
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
      Lookup lookup = MethodHandles.lookup();
      Lookup actual = lookup.in(target);
      Class access = lookup.getClass();
      Field modes = access.getDeclaredField(ALLOWED_MODES);
      
      modes.setAccessible(true);
      modes.set(actual, Modifier.PRIVATE);
      
      return actual.unreflectSpecial(method, target);
   }
}