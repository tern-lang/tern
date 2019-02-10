package tern.compile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import junit.framework.TestCase;

import tern.common.store.ClassPathStore;
import tern.common.store.Store;
import tern.compile.verify.VerifyError;
import tern.compile.verify.VerifyException;
import tern.core.Context;
import tern.core.scope.EmptyModel;
import tern.core.scope.Model;

public abstract class ScriptTestCase extends TestCase {
   
   private static final Executor DEFAULT_EXECUTOR = new ScheduledThreadPoolExecutor(10);
   private final Map<String, String> scripts;
   
   protected ScriptTestCase() {
      this.scripts = new ConcurrentHashMap<String, String>();
   }
   
   @Override
   public void setUp() {
      scripts.clear();
   }
   
   @Override
   public void tearDown() {
      scripts.clear();
   }
   
   public ScriptTestCase addScript(String path, String source) {
      scripts.put(path, source);
      System.err.println("// " + path + "\n" + source);
      return this;
   }
   
   private static class MapStore implements Store {
      
      private final Map<String, String> sources;
      
      public MapStore(Map<String, String> sources){
         this.sources = sources;
      }

      @Override
      public InputStream getInputStream(String name) {
         String source = sources.get(name);
         if(source != null) {
            try {
               System.err.println(name);
               byte[] data = source.getBytes("UTF-8");
               return new ByteArrayInputStream(data);
            }catch(Exception e){
               throw new IllegalStateException("Could not load " + name, e);
            }
         }
         return null;
      }

      @Override
      public OutputStream getOutputStream(String name) {
         return null;
      }
      
   }
   
   protected static class AssertionContext{
      private final Compiler compiler;
      private final Context context;
      
      public AssertionContext(Compiler compiler, Context context){
         this.compiler = compiler;
         this.context = context;
      }
      
      public Compiler getCompiler(){
         return compiler;
      }
      
      public Context getContext(){
         return context;
      }
   }

   protected boolean isThreadPool() {
      return false;
   }

   protected AssertionContext getContext() {
      if(!scripts.isEmpty()) {
         Store store = new MapStore(scripts);
         Context context = new StoreContext(store, DEFAULT_EXECUTOR);         
         Compiler compiler = new ResourceCompiler(context);
         return new AssertionContext(compiler, context);
      }
      if(isThreadPool()){
         Store store = new ClassPathStore();
         Context context = new StoreContext(store, DEFAULT_EXECUTOR);
         Compiler compiler = new StringCompiler(context);
         return new AssertionContext(compiler, context);
      }
      Store store = new ClassPathStore();
      Context context = new StoreContext(store, null);
      Compiler compiler = new StringCompiler(context);
      return new AssertionContext(compiler, context);
   }
   
   private boolean isRegisteredScript(String script) {
      if(!scripts.isEmpty()) {
         String source = scripts.get(script);
         
         if(source == null) {
            throw new IllegalArgumentException("Coult not find script " + script);
         }
         return true;
      }
      return false;
   }
   
   public static abstract class AssertionCallback {
      public void onSuccess(Context context, Object result) throws Exception{}
      public void onException(Context context, Exception cause) throws Exception{
         throw cause;
      }
   }
   
   private static interface AssertionAction{
      Object execute(AssertionContext compiler, String callerMethod) throws Exception;
   }

   public void assertScriptCompiles(final String script) throws Exception {
      assertScriptCompiles(script, null);
   }
   
   public void assertScriptCompiles(final String script, final AssertionCallback callback) throws Exception {
      executeAssertion(script, new AssertionAction() {
         public Object execute(AssertionContext context, String callerMethod) throws Exception {
            Executable executable = context.getCompiler().compile(script);
            Model model = new EmptyModel();
            executable.execute(model, true);
            return null;

         }        
      }, callback);
   }

   public void assertScriptExecutes(final String script) throws Exception {
      assertScriptExecutes(script, null);
   }
   
   public void assertScriptExecutes(final String script, final AssertionCallback callback) throws Exception {
      executeAssertion(script, new AssertionAction() {
         public Object execute(AssertionContext context, String callerMethod) throws Exception {
            Executable executable = context.getCompiler().compile(script);
            Timer.timeExecution(callerMethod, executable);
            return null;
         }        
      }, callback);
   }
   
   public void assertExpressionEvaluates(final String script, final String expression) throws Exception {
      assertExpressionEvaluates(script, expression, (AssertionCallback)null);
   }
   
   public void assertExpressionEvaluates(final String script, final String expression, final AssertionCallback callback) throws Exception {
      executeAssertion(script, new AssertionAction() {
         public Object execute(AssertionContext context, String callerMethod) throws Exception {
            Executable executable = context.getCompiler().compile(script);
            Timer.timeExecution(callerMethod, executable);
            Model model = new EmptyModel();
            return context.getContext().getEvaluator().evaluate(model, expression);
         }        
      }, callback);
   }
   
   public void assertExpressionEvaluates(final String script, final String expression, final String module) throws Exception {
      assertExpressionEvaluates(script, expression, module, null);
   }
   
   public void assertExpressionEvaluates(final String script, final String expression, final String module, final AssertionCallback callback) throws Exception {
      executeAssertion(script, new AssertionAction() {
         public Object execute(AssertionContext context, String callerMethod) throws Exception {
            Executable executable = context.getCompiler().compile(script);
            Timer.timeExecution(callerMethod, executable);
            Model model = new EmptyModel();
            return context.getContext().getEvaluator().evaluate(model, expression, module);

         }        
      }, callback);
   }
   
   private void executeAssertion(String script, AssertionAction assertion, AssertionCallback callback) throws Exception {      
      if(!isRegisteredScript(script)) {     
         System.err.println(script); // dump it if its a string
      }
      AssertionContext compiler = getContext();
      try {
         Class thisClass = getClass();
         StackTraceElement[] elements = new Exception().getStackTrace();
         StackTraceElement caller = elements[2];
         for(int i = 0; i < elements.length; i++) {
            String stackMethod = elements[i].getMethodName();
            String stackClassName = elements[i].getClassName();
            Class stackClass = Class.forName(stackClassName);
                  
            if(stackClass.isAssignableFrom(thisClass)) {
               if(stackMethod.startsWith("test")) {
                  caller = elements[i];
                  break;
               }               
            }
         }
         String testMethod = caller.getMethodName();
         String testClass = caller.getClassName();
         int lastIndex = caller.getClassName().lastIndexOf(".");
         String testClassName = lastIndex >= 0 ? testClass.substring(lastIndex + 1) : testClass;         
         Object result = assertion.execute(compiler, String.format("%s.%s", testClassName, testMethod));
         
         if(callback!= null) {
            callback.onSuccess(compiler.getContext(), result);;
         }
      }catch(VerifyException cause){
         List<VerifyError> errors = cause.getErrors();
         for(VerifyError error : errors) {
            error.getCause().printStackTrace();
         }
         if(callback!= null) {
            callback.onException(compiler.getContext(), errors.get(0).getCause());
         } else {
            throw cause;
         }
      }catch(Exception cause) {
         if(callback!= null) {
            callback.onException(compiler.getContext(), cause);
         } else {
            throw cause;
         }
      }
   }
}
