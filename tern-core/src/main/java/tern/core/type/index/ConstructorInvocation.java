package tern.core.type.index;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;

import tern.core.error.InternalStateException;
import tern.core.function.Invocation;
import tern.core.scope.Scope;

public class ConstructorInvocation implements Invocation<Object> {

   private final Constructor constructor;
   private final Invocation invocation;
   
   public ConstructorInvocation(Invocation invocation, Constructor constructor) {
      this.constructor = constructor;
      this.invocation = invocation;
   }
   
   @Override
   public Object invoke(Scope scope, Object left, Object... list) throws Exception {
      if(constructor.isVarArgs()) {
         Class[] types = constructor.getParameterTypes();
         int require = types.length;
         int actual = list.length;
         int start = require - 1;
         int remaining = actual - start;

         if(remaining >= 0) {
            Class type = types[require - 1];
            Class component = type.getComponentType();
            Object array = Array.newInstance(component, remaining);
            
            for(int i = 0; i < remaining; i++) {
               try {
                  Array.set(array, i, list[i + start]);
               } catch(Exception e){
                  throw new InternalStateException("Invalid argument at " + i + " for" + constructor, e);
               }
            }
            Object[] copy = new Object[require];
            
            if(require > list.length) {
               System.arraycopy(list, 0, copy, 0, list.length);
            } else {
               System.arraycopy(list, 0, copy, 0, require);
            }
            copy[start] = array;
            list = copy;
         }
      }     
      return invocation.invoke(scope, null, list);
   }
}