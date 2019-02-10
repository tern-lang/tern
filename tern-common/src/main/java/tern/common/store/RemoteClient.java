package tern.common.store;

import static tern.common.store.RemoteStatus.ERROR;
import static tern.common.store.RemoteStatus.NOT_FOUND;
import static tern.common.store.RemoteStatus.SUCCESS;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class RemoteClient {
   
   private static final String WRITE_METHOD = "PUT";
   private static final int SUCCESS_CODE = 200;
   private static final int NOT_FOUND_CODE = 404;
   
   private final RemoteLocation location;
   private final URI root;
   
   public RemoteClient(URI root) {
      this.location = new RemoteLocation(root);
      this.root = root;
   }

   public RemoteResponse get(String path) {
      try {
         URL address = location.createRelative(path);
         URLConnection connection = address.openConnection();
         HttpURLConnection request = (HttpURLConnection)connection;
         int code = request.getResponseCode();
         
         if(code == SUCCESS_CODE) {
            return new RemoteResponse(request, SUCCESS, path);
         }
         if(code == NOT_FOUND_CODE) {
            return new RemoteResponse(request, NOT_FOUND, path);
         }
         return new RemoteResponse(request, ERROR, path);
      } catch(Exception e) {
         throw new StoreException("Could not load resource '" + path + "' from '" + root + "'", e);
      }
   }
   
   public RemoteResponse put(String path) {
      try {
         URL address = location.createRelative(path);
         URLConnection connection = address.openConnection();
         HttpURLConnection request = (HttpURLConnection)connection;
         
         request.setDoOutput(true);
         request.setRequestMethod(WRITE_METHOD);
         request.connect(); // check if the server is up
         
         return new RemoteResponse(request, SUCCESS, path);
      } catch(Exception e) {
         throw new StoreException("Could not write resource '" + path + "' to '" + root + "'", e);
      } 
   }
}