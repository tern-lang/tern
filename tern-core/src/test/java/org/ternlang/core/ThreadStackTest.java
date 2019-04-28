package org.ternlang.core;

import static java.util.Collections.EMPTY_LIST;
import static org.ternlang.core.function.Origin.DEFAULT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.ternlang.core.function.FunctionSignature;
import org.ternlang.core.function.FunctionType;
import org.ternlang.core.function.InvocationFunction;
import org.ternlang.core.function.Parameter;
import org.ternlang.core.function.Signature;
import org.ternlang.core.module.ContextModule;
import org.ternlang.core.module.FilePathConverter;
import org.ternlang.core.module.Module;
import org.ternlang.core.stack.ThreadStack;
import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceType;

public class ThreadStackTest extends TestCase {
   
   public void testDeepStack() throws Exception {
      ThreadStack stack = new ThreadStack();
      Throwable cause = new Throwable();
      
      createTrace(stack, "/run.tern", 1);
      createTrace(stack, "/run.tern", 13);
      createModuleFunction(stack, "start", "run.graphics.Launcher");
      createTrace(stack, "/run.tern", 137);
      createTypeFunction(stack, "paint", "Panel", "run.graphics");
      createTrace(stack, "/run.tern", 413);
      createTypeFunction(stack, "transform", "Panel", "run.graphics");
      createTrace(stack, "/run.tern", 21);
      createTypeFunction(stack, "getDimensions", "PanelLayout", "run.layout");
      createTrace(stack, "/layout.tern", 33);
      
      StackTraceElement[] list =stack.build();
      cause.setStackTrace(list);
      cause.printStackTrace();
      
      assertEquals(list[0].toString(), "run.layout.PanelLayout.getDimensions(/layout.tern:33)");
      assertEquals(list[1].toString(), "run.graphics.Panel.transform(/run.tern:21)");
      assertEquals(list[2].toString(), "run.graphics.Panel.paint(/run.tern:413)");
      assertEquals(list[3].toString(), "run.graphics.Launcher.start(/run.tern:137)"); // module function
      assertEquals(list[4].toString(), "run.main(/run.tern:13)");
   }
   
   public static void createTrace(ThreadStack stack, String resource, int line){
      FilePathConverter converter = new FilePathConverter();
      stack.trace().before(new Trace(TraceType.NORMAL, new ContextModule(null, null, converter.createPath(resource), converter.createModule(resource),"", -1), converter.createPath(resource), line));
   }
   
   public static void createTypeFunction(ThreadStack stack, String functionName, String typeName, String moduleName){
      FilePathConverter converter = new FilePathConverter();
      Module module = new ContextModule(null, null, converter.createPath(moduleName), moduleName,"", -1);
      MockType type = new MockType(module, typeName, null, null);
      List<Parameter> parameters = new ArrayList<Parameter>();
      Signature signature = new FunctionSignature(parameters, EMPTY_LIST, module, null, DEFAULT, true);
      
      stack.trace().before(new InvocationFunction(signature, null, type, null, functionName, 11));
   }
   
   public static void createModuleFunction(ThreadStack stack, String functionName, String moduleName){
      FilePathConverter converter = new FilePathConverter();
      Module module = new ContextModule(null, null, converter.createPath(moduleName), moduleName,"", -1);
      List<Parameter> parameters = new ArrayList<Parameter>();
      Signature signature = new FunctionSignature(parameters, EMPTY_LIST, module, null, DEFAULT, true);
      FunctionType type = new FunctionType(signature, module, null);
      
      stack.trace().before(new InvocationFunction(signature, null, type, null, functionName, 11));
   }
}
