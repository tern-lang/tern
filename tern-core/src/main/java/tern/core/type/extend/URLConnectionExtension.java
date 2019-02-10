package tern.core.type.extend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tern.common.Consumer;

public class URLConnectionExtension {
   
   private static final String GET = "GET";
   private static final String POST = "POST";
   private static final String PUT = "PUT";
   private static final String DELETE = "DELETE";
   private static final String HEAD = "HEAD";
   
   private final InputStreamExtension extension;
   private final byte[] empty;
   
   public URLConnectionExtension() {
      this.extension = new InputStreamExtension();
      this.empty = new byte[]{};
   }
   
   public URLConnection get(URLConnection connection) throws IOException {
      return execute(connection, empty, GET);
   }
   
   public URLConnection head(URLConnection connection) throws IOException {
      return execute(connection, empty, HEAD);
   }
   
   public URLConnection delete(URLConnection connection) throws IOException {
      return execute(connection, empty, DELETE);
   }
   
   public URLConnection post(URLConnection connection, byte[] data) throws IOException {
      return execute(connection, data, POST);
   }
   
   public URLConnection post(URLConnection connection, String text) throws IOException {
      return execute(connection, text, POST);
   }
   
   public URLConnection post(URLConnection connection, InputStream source) throws IOException {
      return execute(connection, source, POST);
   }   
   
   public URLConnection put(URLConnection connection, byte[] data) throws IOException {
      return execute(connection, data, PUT);
   }
   
   public URLConnection put(URLConnection connection, String text) throws IOException {
      return execute(connection, text, PUT);
   }
   
   public URLConnection put(URLConnection connection, InputStream source) throws IOException {
      return execute(connection, source, PUT);
   }
   
   public URLConnection header(URLConnection connection, String name, String value) throws IOException {
      HttpURLConnection request = (HttpURLConnection)connection;

      if(name != null) {
         request.setRequestProperty(name, value);
      }
      return connection;
   }
   
   public URLConnection success(URLConnection connection, Runnable task) throws IOException {
      URLResponse response = response(connection);
      int status = response.getStatus();
      
      if(status >= 200 && status < 300) {
         task.run();
      }
      return connection;
   }
   
   public URLConnection success(URLConnection connection, Consumer<URLResponse, ?> consumer) throws IOException {
      URLResponse response = response(connection);
      int status = response.getStatus();
      
      if(status >= 200 && status < 300) {
         consumer.consume(response);
      }
      return connection;
   }
   
   public URLConnection failure(URLConnection connection, Runnable task) throws IOException {
      URLResponse response = response(connection);
      int status = response.getStatus();
      
      if(status < 200 || status >= 300) {
         task.run();
      }
      return connection;
   }
   
   public URLConnection failure(URLConnection connection, Consumer<URLResponse, ?> consumer) throws IOException {
      URLResponse response = response(connection);
      int status = response.getStatus();
      
      if(status < 200 || status >= 300) {
         consumer.consume(response);
      }
      return connection;
   }

   public URLResponse response(URLConnection connection) throws IOException {
      HttpURLConnection request = (HttpURLConnection)connection;
      String status = request.getHeaderField(0);
      int code = request.getResponseCode(); // force write

      return new URLResponse(request, status, code);
   }

   public InputStream stream(URLConnection connection) throws IOException {
      HttpURLConnection request = (HttpURLConnection)connection;

      try {
         return request.getInputStream();
      } catch(Exception e) {
         return request.getErrorStream();
      }
   }

   private URLConnection execute(URLConnection connection, byte[] data, String method) throws IOException {
      HttpURLConnection request = (HttpURLConnection)connection;
      
      if(data.length > 0) {
         request.setDoOutput(true);
         request.setRequestMethod(method);
         
         try {
            OutputStream stream = request.getOutputStream();
            
            stream.write(data);
            stream.close();
         } catch(Exception e) {
            throw new IOException("Could not execute '" + method + "' for '" + connection + "'", e);
         }
      }
      request.getResponseCode(); // force write
      return connection;
   }
   
   private URLConnection execute(URLConnection connection, String source, String method) throws IOException {
      HttpURLConnection request = (HttpURLConnection)connection;
      byte[] data = source.getBytes();
      
      if(data.length > 0) {
         request.setDoOutput(true);
         request.setRequestMethod(method);
         
         try {
            OutputStream stream = request.getOutputStream();
            
            stream.write(data);
            stream.close();
         } catch(Exception e) {
            throw new IOException("Could not execute '" + method + "' for '" + connection + "'", e);
         }
      }
      request.getResponseCode(); // force write
      return connection;
   }
   
   private URLConnection execute(URLConnection connection, InputStream source, String method) throws IOException {
      HttpURLConnection request = (HttpURLConnection)connection;
      
      request.setDoOutput(true);
      request.setRequestMethod(method);
      
      try {
         OutputStream stream = request.getOutputStream();
         
         extension.copyTo(source, stream);
         stream.close();
      } catch(Exception e) {
         throw new IOException("Could not execute '" + method + "' for '" + connection + "'", e);
      }
      request.getResponseCode(); // force write
      return connection;
   }

   public static class URLResponse {

      private final HttpURLConnection connection;
      private final String status;
      private final int code;

      public URLResponse(HttpURLConnection connection, String status, int code) {
         this.connection = connection;
         this.status = status;
         this.code = code;
      }

      public int getStatus() {
         return code;
      }

      public URLConnection getConnection() {
         return connection;
      }

      public InputStream getInputStream() {
         try {
            return connection.getInputStream();
         } catch(Exception e) {
            return connection.getErrorStream();
         }
      }

      public String getHeader(String name) {
         Map<String, List<String>> headers = connection.getHeaderFields();
         List<String> values = headers.get(name);

         if(values != null) {
            for (String value : values) {
               return value;
            }
         }
         return null;
      }

      public List<String> getHeaders(String name) {
         Map<String, List<String>> headers = connection.getHeaderFields();
         List<String> values = headers.get(name);

         if(values == null) {
            return Collections.emptyList();
         }
         return values;
      }

      @Override
      public String toString() {
         Map<String, List<String>> headers = connection.getHeaderFields();
         Set<String> names = headers.keySet();
         
         if(!names.isEmpty()) {
            StringBuilder builder = new StringBuilder();

            builder.append(status);
            builder.append("\r\n");

            for (String name : names) {
               List<String> values = headers.get(name);
   
               if (values != null && name != null) {
                  for (String value : values) {
                     builder.append(name);
                     builder.append(": ");
   
                     if (value != null) {
                        builder.append(value);
                     }
                     builder.append("\r\n");
                  }
               }
            }
            builder.append("\r\n");
            return builder.toString();
         }
         return String.format("%s\r\n", status);
      }
   }
}
