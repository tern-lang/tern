package tern.core.store;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;

import tern.common.store.NotFoundException;
import tern.common.store.RemoteStore;
import tern.common.store.StoreException;

import junit.framework.TestCase;

public class RemoteStoreTest extends TestCase {
   
   private static final String SOURCE=
   "function fib(n) {\n"+
   "   if (n<2) {\n"+
   "      return 1;\n"+
   "   }\n"+
   "   return fib(n-1) + fib(n-2);\n"+
   "}\n"+
   "var result = fib(30);\n"+
   "println(result);\n";
   
   public void testRemoteStoreSuccess() throws Exception {
      MockServer server = new MockServer(SOURCE, "text/plain", "OK", 200);
      URI address = server.start();
      RemoteStore store = new RemoteStore(address);
      InputStream source = store.getInputStream("/path.snap"); 
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      byte[] chunk = new byte[1024];
      int count = 0;
      
      while((count = source.read(chunk))!=-1){
         buffer.write(chunk, 0, count);
      }
      String text = buffer.toString("UTF-8");
      assertEquals(text, SOURCE);
   }
   
   public void testRemoteStoreError() throws Exception {
      MockServer server = new MockServer("Internal Server Error", "text/plain", "Internal Server Error", 500);
      URI address = server.start();
      RemoteStore store = new RemoteStore(address);
      boolean failure = false;
      
      try {
         store.getInputStream("/path.snap"); 
      }catch(StoreException e){
         e.printStackTrace();
         failure = true;
      }
      assertTrue("HTTP 500 response is an error", failure);
   }
   
   public void testRemoteStoreNotFound() throws Exception{
      MockServer server = new MockServer("Resource Not Found", "text/plain", "Not Found", 404);
      URI address = server.start();
      RemoteStore store = new RemoteStore(address);
      boolean failure = false;
      
      try {
         store.getInputStream("/path.snap"); 
      }catch(NotFoundException e){
         e.printStackTrace();
         failure = true;
      }
      assertTrue("HTTP 404 response is an error", failure);
   }

   private static class MockServer {
      
      private String message;
      private String content;
      private String type;
      private int status;
      
      public MockServer(String content, String type, String message, int status){
         this.content = content;
         this.message = message;
         this.status = status;
         this.type = type;
      }
      
      public URI start() throws Exception {
         Acceptor acceptor = new Acceptor();
         acceptor.start();
         int port = acceptor.getPort();
         return new URI("http://localhost:"+port+"/");
      }

      private class Acceptor extends Thread{
      
         private ServerSocket server;
         
         public Acceptor() throws IOException{
            this.server = new ServerSocket(0);
         }
         public int getPort(){
            return server.getLocalPort();
         }
         @Override
         public void run(){
            try {
               Socket socket = server.accept();
               CountDownLatch latch = new CountDownLatch(1);
               Handler handler = new Handler(latch,socket);
               handler.start();
               latch.await(); // make sure the handler starts
            }catch(Exception e){
               e.printStackTrace();
            }
         }
      }
      
      private class Handler extends Thread {
         
         private final CountDownLatch latch;
         private final Socket socket;
         
         public Handler(CountDownLatch latch, Socket socket) {
            this.latch = latch;
            this.socket = socket;
         }
         @Override
         public void run() {
            latch.countDown();
            try {
               SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
               Date date = new Date();
               dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
               String payload = "HTTP/1.1 " + status + " " +message +"\r\n"+
                     "Content-Type: "+type+"\r\n"+
                     "Server: Test/1.0\r\n"+
                     "Date: "+dateFormat.format(date)+"\r\n"+
                     "Connection: close\r\n"+
                     "\r\n"+
                     content;
               byte[] data = payload.getBytes("UTF-8");
               System.err.println(payload);
               socket.getOutputStream().write(data);
               socket.close();  
            }catch(Exception e){
               e.printStackTrace();
            }
         }
      }
   }
}
