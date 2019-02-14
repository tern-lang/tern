package org.ternlang.common.store;

import java.net.URI;
import java.net.URL;

public class RemoteLocation {
   
   private final URI root;
   
   public RemoteLocation(URI root) {
      this.root = root;
   }
   
   public URL createRelative(String path) throws Exception {
      String original = root.getPath();
      String scheme = root.getScheme();
      String host = root.getHost();
      int port = root.getPort();
      
      if(!original.endsWith("/")) {
         original = original + "/";
      }
      if(path.startsWith("/")) {
         path = path.substring(1);
      }
      return new URL(scheme + "://" +  host + ":" + port + original + path);
   }
}