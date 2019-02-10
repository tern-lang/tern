package tern.core.type.extend;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class URLExtension {

   private final URLConnectionExtension extension;
   
   public URLExtension() {
      this.extension = new URLConnectionExtension();
   }
   
   public URLConnection get(URL target) throws IOException {
      URLConnection connection = target.openConnection();
      return extension.get(connection);
   }
   
   public URLConnection head(URL target) throws IOException {
      URLConnection connection = target.openConnection();
      return extension.head(connection);
   }
   
   public URLConnection delete(URL target) throws IOException {
      URLConnection connection = target.openConnection();
      return extension.delete(connection);
   }
   
   public URLConnection post(URL target, byte[] data) throws IOException {
      URLConnection connection = target.openConnection();
      return extension.post(connection, data);
   }
   
   public URLConnection post(URL target, String text) throws IOException {
      URLConnection connection = target.openConnection();
      return extension.post(connection, text);
   }
   
   public URLConnection post(URL target, InputStream source) throws IOException {
      URLConnection connection = target.openConnection();
      return extension.post(connection, source);
   }   
   
   public URLConnection put(URL target, byte[] data) throws IOException {
      URLConnection connection = target.openConnection();
      return extension.put(connection, data);
   }
   
   public URLConnection put(URL target, String text) throws IOException {
      URLConnection connection = target.openConnection();
      return extension.post(connection, text);
   }
   
   public URLConnection put(URL target, InputStream source) throws IOException {
      URLConnection connection = target.openConnection();
      return extension.post(connection, source);
   }
   
   public URLConnection header(URL target, String name, String value) throws IOException {
      URLConnection connection = target.openConnection();
      return extension.header(connection, name, value);
   }
}
