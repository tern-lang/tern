package org.ternlang.common.store;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public class RemoteStore implements Store {
   
   private final RemoteClient client;
   private final URI root;
   
   public RemoteStore(URI root) {
      this.client = new RemoteClient(root);
      this.root = root;
   }
   
   @Override
   public InputStream getInputStream(String path) {  
      RemoteResponse response = client.get(path);
      RemoteStatus status = response.getStatus();
      
      if(status.isNotFound()) {
         throw new NotFoundException("Could not find resource '" + path + "' from '" + root + "'");
      }
      if(status.isError()) {
         throw new StoreException("Error reading resource '" + path + "' from '" + root + "'");
      }
      return response.getInputStream();
   }

   @Override
   public OutputStream getOutputStream(String path) {
      RemoteResponse response = client.put(path);
      RemoteStatus status = response.getStatus();
      
      if(status.isError()) {
         throw new StoreException("Error writing resource '" + path + "' to '" + root + "'");
      }
      return response.getOutputStream();
   }
}