package tern.common.store;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import tern.common.Cache;
import tern.common.SoftCache;

public class CacheStore implements Store {

   private final Cache<String, byte[]> cache;
   private final Set<String> failures;
   private final StoreReader reader;
   private final Store store;
   
   public CacheStore(Store store) {
      this(store, 200);
   }
   
   public CacheStore(Store store, int capacity) {
      this(store, capacity, 8192);
   }
   
   public CacheStore(Store store, int capacity, int read) {
      this.cache = new SoftCache<String, byte[]>(capacity);
      this.failures = new CopyOnWriteArraySet<String>();
      this.reader = new StoreReader(store, read);
      this.store = store;
   }
   
   public byte[] getBytes(String path) {
      if(!failures.contains(path)) {
         byte[] resource = cache.fetch(path);
         
         if(resource == null) {
            try {
               resource = reader.getBytes(path);
               cache.cache(path, resource);
            } catch(NotFoundException cause) {
               failures.add(path);
               throw cause;
            }
         }
         if(resource == null) {
            throw new NotFoundException("Could not find '" + path + "'");
         }
         return resource;
      }
      return null;
   }
   
   public String getString(String path) {
      byte[] resource = getBytes(path);
      
      try {
         if(resource != null) {
            return new String(resource, "UTF-8");
         }
      }catch(Exception e) {
         throw new StoreException("Could not read resource '" + path + "'", e);
      }
      return null;
   }
   
   @Override
   public InputStream getInputStream(String path) {
      byte[] resource = getBytes(path);
      
      try {
         if(resource != null) {
            return new ByteArrayInputStream(resource);
         }
      }catch(Exception e) {
         throw new StoreException("Could not read resource '" + path + "'", e);
      }
      return null;
   }
   
   @Override
   public OutputStream getOutputStream(String path) {
      return store.getOutputStream(path);
   }
}