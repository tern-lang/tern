package org.ternlang.compile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.ternlang.common.store.RemoteStore;
import org.ternlang.common.store.Store;
import org.ternlang.core.Context;

public class ImportRemoteResourceTest extends TestCase {
   
   private static final String SOURCE_1 =
   "import util.regex.Pattern;\r\n"+
   "class Foo {\n"+
   "   var x,y;\n"+
   "   new(x,y){\n"+
   "      this.x=x;\n"+
   "      this.y=y;\n"+
   "   }\n"+
   "   toString(){\n"+
   "      \"Foo(${x},${y})\";\n"+
   "   }\n"+
   "}\n"+
   "class Bar{\n"+
   "   var text;\n"+
   "   new(text){\n"+
   "      this.text=text;\n"+
   "   }\n"+
   "   toString(){\n"+
   "      \"Bar(${text})\";\n"+
   "   }\n"+
   "}\n";
         
   private static final String SOURCE_2 =
   "import example.Foo;\n"+
   "import example.Bar;\n"+
   "\n"+
   "class Demo{\n"+
   "   var foo;\n"+
   "   new(x,y){\n"+
   "      this.foo = new Foo(x,y);\n"+
   "   }\n"+
   "   get(): Foo{\n"+
   "      return foo;\n"+
   "   }\n"+
   "}";

   private static final String SOURCE_3 =
   "import demo.Demo;\n"+
   "\n"+
   "class Builder{\n"+
   "   var demo;\n"+
   "   new(text){\n"+
   "      this.demo = new Demo(text,1);\n"+
   "   }\n"+
   "   get(): Demo{\n"+
   "      return demo;\n"+
   "   }\n"+
   "}\n";
   
   private static final String SOURCE_4 =
   "import example.Bar;\n"+
   "import demo.Demo;\n"+   
   "\n"+
   "var bar = new Bar(\"test\");\n"+
   "var demo = new Demo(\"x\",33);\n"+
   "var foo = demo.get();\n"+
   "\n"+
   "println(bar);\n"+
   "println(foo);\n";     
   
   private static final String SOURCE_5 =
   "import builder.Builder;\n"+
   "\n"+
   "var example = new Builder(\"test\");\n"+
   "var demo = example.get();\n"+
   "\n"+
   "println(demo);\n";   
         
   public void testRemoteImports() throws Exception {
      Map<String, String> sources = new HashMap<String, String>();
      sources.put("/resource/example.tern", SOURCE_1);
      sources.put("/resource/demo.tern", SOURCE_2);
      sources.put("/resource/builder/Builder.tern", SOURCE_3);
      sources.put("/resource/main.tern", SOURCE_4);
      sources.put("/resource/launch.tern", SOURCE_5);
      System.err.println(SOURCE_3);
      MockServer server = new MockServer(sources, "/resource");
      try {
         URI address = server.start();
         Store store = new RemoteStore(address);
         Context context = new StoreContext(store, null);
         Compiler compiler = new ResourceCompiler(context);
         Timer.timeExecution("testRemoteImports", compiler.compile("/main.tern"));
         Timer.timeExecution("testRemoteImports", compiler.compile("/launch.tern"));
      }finally{
         server.stop();
      }
   }

   private static class MockServer {
      
      private final Map<String, String> resources;
      private final Set<Acceptor> acceptors;
      private final String prefix;
      
      public MockServer(Map<String, String> resources, String prefix){
         this.acceptors = new CopyOnWriteArraySet<Acceptor>();
         this.resources = resources;
         this.prefix = prefix;
      }
      
      public URI start() throws Exception {
         Acceptor acceptor = new Acceptor();
         StringBuilder builder = new StringBuilder();
         acceptor.start();
         int port = acceptor.getPort();
         builder.append("http://localhost:");
         builder.append(port);
         if(prefix.startsWith("/")) {
            builder.append(prefix);
         }else {
            builder.append("/");
            builder.append(prefix);
         }
         String location = builder.toString();
         acceptors.add(acceptor);
         return new URI(location);
      }
      
      public void stop(){
         for(Acceptor acceptor : acceptors){
            acceptor.close();
            try {
               acceptor.join(2000);
            }catch(Exception e){
               e.printStackTrace();
            }
         }
      }

      private class Acceptor extends Thread{
      
         private ServerSocket server;
         
         public Acceptor() throws IOException{
            this.server = new ServerSocket(0);
         }
         public int getPort(){
            return server.getLocalPort();
         }
         public void close(){
            try{
               server.close();
            }catch(Exception e){
               e.printStackTrace();
            }
         }
         @Override
         public void run(){
            try {
               while(!server.isClosed()){
                  Socket socket = server.accept();
                  CountDownLatch latch = new CountDownLatch(1);
                  Handler handler = new Handler(latch,socket);
                  handler.start();
                  latch.await(); // make sure the handler starts
               }
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
               String resource = createRequest();
               
               if(resource != null) {
                  String content = resources.get(resource);
                  String payload = null;
                  
                  if(content == null) {
                     payload = createResponse("Resource "+resource+" not found", "Not Found", 404);
                  } else {
                     payload = createResponse(content, "OK", 200);
                  }
                  byte[] data = payload.getBytes("UTF-8");
                  System.err.println(payload);
                  socket.getOutputStream().write(data);
                  socket.close();  
               } else {
                  String payload = createResponse("Request was malformed", "Internal Server Error", 500);
                  byte[] data = payload.getBytes("UTF-8");
                  System.err.println(payload);
                  socket.getOutputStream().write(data);
                  socket.close();   
               }
            }catch(Exception e){
               e.printStackTrace();
            }
         }
         
         private String createRequest() throws Exception {
            InputStream source = socket.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int count = 0;
            while((count = source.read()) != -1) {
               if(count == '\n') {
                  break;
               }
               buffer.write(count);
            }
            String line = buffer.toString("UTF-8").trim();
            Pattern pattern = Pattern.compile("GET\\s+(.*)\\s+HTTP.*");
            Matcher matcher = pattern.matcher(line.trim());
            
            System.err.println(line);
            
            if(matcher.matches()) {
               return matcher.group(1);
            }
            return null;
         }
         
         private String createResponse(String content, String message, int status) throws Exception {
            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
            Date date = new Date();
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            return "HTTP/1.1 "+status+" "+message+"\r\n"+
                  "Content-Type: text/plain\r\n"+
                  "Server: Test/1.0\r\n"+
                  "Date: "+format.format(date)+"\r\n"+
                  "Connection: close\r\n"+
                  "\r\n"+
                  content;
         }
      }
   }
}
