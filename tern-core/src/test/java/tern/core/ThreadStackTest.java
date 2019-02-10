package tern.core;

import static java.util.Collections.EMPTY_LIST;
import static tern.core.function.Origin.DEFAULT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import tern.core.function.FunctionSignature;
import tern.core.function.FunctionType;
import tern.core.function.InvocationFunction;
import tern.core.function.Parameter;
import tern.core.function.Signature;
import tern.core.module.ContextModule;
import tern.core.module.FilePathConverter;
import tern.core.module.Module;
import tern.core.stack.ThreadStack;
import tern.core.trace.Trace;
import tern.core.trace.TraceType;

public class ThreadStackTest extends TestCase {
   
   public void testDeepStack() throws Exception {
      ThreadStack stack = new ThreadStack();
      Throwable cause = new Throwable();
      
      createTrace(stack, "/run.snap", 1);
      createTrace(stack, "/run.snap", 13);
      createModuleFunction(stack, "start", "run.graphics.Launcher");
      createTrace(stack, "/run.snap", 137);
      createTypeFunction(stack, "paint", "Panel", "run.graphics");
      createTrace(stack, "/run.snap", 413);
      createTypeFunction(stack, "transform", "Panel", "run.graphics");
      createTrace(stack, "/run.snap", 21);
      createTypeFunction(stack, "getDimensions", "PanelLayout", "run.layout");
      createTrace(stack, "/layout.snap", 33);
      
      StackTraceElement[] list =stack.build();
      cause.setStackTrace(list);
      cause.printStackTrace();
      
      assertEquals(list[0].toString(), "run.layout.PanelLayout.getDimensions(/layout.snap:33)");
      assertEquals(list[1].toString(), "run.graphics.Panel.transform(/run.snap:21)");
      assertEquals(list[2].toString(), "run.graphics.Panel.paint(/run.snap:413)");
      assertEquals(list[3].toString(), "run.graphics.Launcher.start(/run.snap:137)"); // module function
      assertEquals(list[4].toString(), "run.main(/run.snap:13)");
   }
   
   public static void createTrace(ThreadStack stack, String resource, int line){
      FilePathConverter converter = new FilePathConverter();
      stack.before(new Trace(TraceType.NORMAL, new ContextModule(null, null, converter.createPath(resource), converter.createModule(resource),"", -1), converter.createPath(resource), line));
   }
   
   public static void createTypeFunction(ThreadStack stack, String functionName, String typeName, String moduleName){
      FilePathConverter converter = new FilePathConverter();
      Module module = new ContextModule(null, null, converter.createPath(moduleName), moduleName,"", -1);
      MockType type = new MockType(module, typeName, null, null);
      List<Parameter> parameters = new ArrayList<Parameter>();
      Signature signature = new FunctionSignature(parameters, EMPTY_LIST, module, null, DEFAULT, true);
      
      stack.before(new InvocationFunction(signature, null, type, null, functionName, 11));
   }
   
   public static void createModuleFunction(ThreadStack stack, String functionName, String moduleName){
      FilePathConverter converter = new FilePathConverter();
      Module module = new ContextModule(null, null, converter.createPath(moduleName), moduleName,"", -1);
      List<Parameter> parameters = new ArrayList<Parameter>();
      Signature signature = new FunctionSignature(parameters, EMPTY_LIST, module, null, DEFAULT, true);
      FunctionType type = new FunctionType(signature, module, null);
      
      stack.before(new InvocationFunction(signature, null, type, null, functionName, 11));
   }
}
