package tern.common.store;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StoreReader {

   private final Store store;
   private final int read;
   
   public StoreReader(Store store) {
      this(store, 8192);
   }
   
   public StoreReader(Store store, int read) {
      this.store = store;
      this.read = read;
   }
   
   public byte[] getBytes(String path) {
      InputStream source = store.getInputStream(path);
      
      try {
         if(source != null) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream(read);
            
            try {
               byte[] array = new byte[read];
               int count = 0;
               
               while((count = source.read(array)) != -1) {
                  buffer.write(array, 0, count);
               }
               return buffer.toByteArray();
            } finally {
               source.close();
            }
         }
      } catch(Exception e) {
         throw new StoreException("Could not read resource '" + path + "'", e);
      }
      return null;
   }
}