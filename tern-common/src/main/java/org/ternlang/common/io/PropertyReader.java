package org.ternlang.common.io;

public abstract class PropertyReader<T> extends StatementReader<T> {
   
   protected PropertyReader(String file) {
      super(file);
   }

   @Override
   protected T create(char[] data, int off, int length, int line) {
      int seek = 0;

      while(seek < length) {
         char next = data[off + seek++];
         
         if(separator(next)) {
            String name = format(data, off, seek - 1);
            
            if(seek >= length) {
               throw new StatementException("No value in '" + file + "' at line " + line);
            }
            return create(name, data, off + seek, length - seek, line);
         }
      }
      throw new StatementException("Error in '" + file + "' at line " + line);
   }

   protected boolean separator(char value) {
      return value == '=';
   }

   protected String format(char[] data, int off, int length) {
      int finish = off + length;
      int start = off;

      while(start < finish) {
         char next = data[start];

         if(!space(next)) {
            break;
         }
         start++;
      }
      while(finish > start) {
         char next = data[finish-1];

         if(!space(next)) {
            break;
         }
         finish--;
      }
      return new String(data, start, finish -start);
   }

   protected abstract T create(String name, char[] data, int off, int length, int line);
}