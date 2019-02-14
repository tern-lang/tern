package org.ternlang.common.store;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class RemoteResponse {
   
   private final HttpURLConnection request;
   private final RemoteStatus status;
   private final String resource;
   
   public RemoteResponse(HttpURLConnection request, RemoteStatus status, String resource) {
      this.resource = resource;
      this.request = request;
      this.status = status;
   }
   
   public RemoteStatus getStatus() {
      try {
         return status;
      } catch(Exception e) {
         throw new StoreException("Could not determine status for " + resource, e);
      }
   }
   
   public InputStream getInputStream(){
      try {
         return request.getInputStream();
      } catch(Exception e) {
         throw new StoreException("Could not get input for " + resource, e);
      }
   }
   
   public OutputStream getOutputStream() {
      try {
         return request.getOutputStream();
      } catch(Exception e) {
         throw new StoreException("Could not get output for " + resource, e);
      }
   }
}