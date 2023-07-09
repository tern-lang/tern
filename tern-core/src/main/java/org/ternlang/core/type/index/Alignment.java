package org.ternlang.core.type.index;

import org.ternlang.common.ArraySlice;
import org.ternlang.core.error.InternalStateException;

import java.lang.reflect.Array;
import java.lang.reflect.Executable;

public enum Alignment {
   NORMAL(false) {
      @Override
      public Object[] align(Executable executable, Object[] list) {
         return list;
      }
   },
   VARARGS(true) {
      @Override
      public Object[] align(Executable executable, Object[] list) {
         Class[] types = executable.getParameterTypes();
         int require = types.length;
         int actual = list.length;
         int start = require - 1;
         int remaining = actual - start;

         if(remaining >= 0) {
            Class type = types[require - 1];
            Class component = type.getComponentType();
            Object array = Array.newInstance(component, remaining);

            for (int i = 0; i < remaining; i++) {
               try {
                  Array.set(array, i, list[i + start]);
               } catch (Exception e) {
                  throw new InternalStateException("Invalid argument at " + i + " for" + executable, e);
               }
            }
            Object[] copy = new Object[require];

            if (require > list.length) {
               System.arraycopy(list, 0, copy, 0, list.length);
            } else {
               System.arraycopy(list, 0, copy, 0, require);
            }
            copy[start] = array;
            return copy;
         }
         return list;
      }
   },
   ARRAY(true) {
      @Override
      public Object[] align(Executable executable, Object[] list) {
         Class[] types = executable.getParameterTypes();
         int require = types.length;
         int actual = list.length;
         int start = require - 1;
         int remaining = actual - start;

         if(remaining >= 0) {
            Object[] copy = new Object[require];
            Object[] array = new Object[remaining];

            for (int i = 0; i < remaining; i++) {
               try {
                  array[i] = list[i + start];
               } catch (Exception e) {
                  throw new InternalStateException("Invalid argument at " + i + " for" + executable, e);
               }
            }
            if (require > list.length) {
               System.arraycopy(list, 0, copy, 0, list.length);
            } else {
               System.arraycopy(list, 0, copy, 0, require);
            }
            copy[start] = new ArraySlice(array);
            return copy;
         }
         return list;
      }
   };

   public final boolean variable;

   private Alignment(boolean variable) {
      this.variable = variable;
   }

   public boolean isVariable() {
      return variable;
   }

   public abstract Object[] align(Executable executable, Object[] list);

   public static Alignment resolve(Executable executable) {
      if(executable.isVarArgs()) {
         return VARARGS;
      }
      int require = executable.getParameterCount();

      if(require > 0) {
         Class[] types = executable.getParameterTypes();
         Class expect = org.ternlang.common.Array.class;
         Class last = types[require - 1];

         if(last == expect) {
            return ARRAY;
         }
      }
      return NORMAL;
   }
}
