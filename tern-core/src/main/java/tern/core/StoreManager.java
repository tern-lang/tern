package tern.core;

import java.io.InputStream;

import tern.common.store.CacheStore;
import tern.common.store.NotFoundException;
import tern.common.store.Store;

public class StoreManager implements ResourceManager {

   private final CacheStore store;
   
   public StoreManager(Store store) {
      this(store, 100);
   }
   
   public StoreManager(Store store, int capacity) {
      this(store, capacity, 8192);
   }
   
   public StoreManager(Store store, int capacity, int read) {
      this.store = new CacheStore(store, capacity, read);
   }
   
   @Override
   public InputStream getInputStream(String path) {
      try {
         return store.getInputStream(path);
      }catch(NotFoundException e) {
         return null;
      }
   }

   @Override
   public byte[] getBytes(String path) {
      try {
         return store.getBytes(path);
      }catch(NotFoundException e) {
         return null;
      }
   }

   @Override
   public String getString(String path) {
      try {
         return store.getString(path);
      }catch(NotFoundException e) {
         return null;
      }
   }
}