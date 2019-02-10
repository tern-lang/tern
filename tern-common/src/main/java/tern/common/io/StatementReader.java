package tern.common.io;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tern.common.store.CacheStore;
import tern.common.store.ClassPathStore;
import tern.common.store.Store;

public abstract class StatementReader<T> implements Iterable<T> {

   protected final CacheStore cache;
   protected final List<T> list;
   protected final Store store;
   protected final String file;

   protected StatementReader(String file) {
      this.store = new ClassPathStore();
      this.cache = new CacheStore(store);
      this.list = new ArrayList<T>();
      this.file = file;
   }
   
   @Override
   public synchronized Iterator<T> iterator() {
      if(list.isEmpty()) {
         String text = cache.getString(file);

         if(text == null) {
            throw new StatementException("File '" + file + "' not found");
         }
         char[] data = text.toCharArray();
         
         if(data.length == 0) {
            throw new StatementException("File '" + file + "' is empty");
         }
         parse(data);
      } 
      return list.iterator(); 
   }

   private void parse(char[] data) {
      int count = data.length;
      int write = 0;
      int read = 0;
      int line = 1;
      
      if(count > 0) {
         char[] copy = new char[data.length];
      
         while(read < count) {
            char next = data[read++];
            
            if(comment(next)) {
               while(read < count) {
                  char value = data[read++];
                  
                  if(line(value)) {
                     line++;
                     break;
                  }
               }
            } else if(quote(next)) {
               copy[write++] = next;
               
               while(read < count) {
                  char value = data[read++];
               
                  if(line(value)) {
                     line++;
                  } else if(value == next) {
                     copy[write++] = value;
                     break;
                  }
                  copy[write++] = value;
               }
            } else if(terminal(next)) {
               int length = write;
               
               if(!line(next)) {
                  while(read < count) {
                     char value = data[read++];
                     
                     if(line(value)) {
                        line++;
                        break;
                     }
                     if(!space(value)) {
                        throw new StatementException("Error in '" + file + "' at line " + line);
                     }
                  }
               }
               write = 0;
               process(copy, 0, length, line);
            } else if(line(next)) {
               line++;
            } else {
               copy[write++] = next;
            }
         }
      }
   }  
   
   private void process(char[] data, int off, int length, int line){
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
      if(start < finish) {
         T value = create(data, start, finish-start, line);
         list.add(value);
      }
   }
   
   protected boolean line(char value) {
      return value == '\n';
   }
   
   protected boolean comment(char value) {
      return value == '#';
   }
   
   protected boolean quote(char value) {
      switch(value){
      case '"': case '\'':
      case '`':
         return true;
      default:
         return false;
      }
   }
   
   protected boolean space(char value) {
      switch(value){
      case ' ': case '\t':
      case '\n': case '\r':
         return true;
      default:
         return false;
      }
   } 
   
   protected boolean terminal(char value) {
      return value == '\n';
   }
   
   protected abstract T create(char[] data, int off, int length, int line);

}