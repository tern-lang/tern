package tern.core.trace;

import static tern.core.trace.TraceType.ALLOCATE;
import static tern.core.trace.TraceType.CONSTRUCT;
import static tern.core.trace.TraceType.DEBUG;
import static tern.core.trace.TraceType.DEFINE;
import static tern.core.trace.TraceType.IMPORT;
import static tern.core.trace.TraceType.INVOKE;
import static tern.core.trace.TraceType.NATIVE;
import static tern.core.trace.TraceType.NORMAL;
import static tern.core.trace.TraceType.REFERENCE;

import tern.core.module.Module;
import tern.core.module.Path;

public class Trace {
   
   public static Trace getNative(Module module, Path path) {
      return new Trace(NATIVE, module, path, -2); // see StackTraceElement.isNativeMethod
   }
   
   public static Trace getConstruct(Module module, Path path, int line) {
      return new Trace(CONSTRUCT, module, path, line);
   }
   
   public static Trace getReference(Module module, Path path, int line) {
      return new Trace(REFERENCE, module, path, line);
   }
   
   public static Trace getInvoke(Module module, Path path, int line) {
      return new Trace(INVOKE, module, path, line);
   }
   
   public static Trace getNormal(Module module, Path path, int line) {
      return new Trace(NORMAL, module, path, line);
   }
   
   public static Trace getDefine(Module module, Path path, int line) {
      return new Trace(DEFINE, module, path, line);
   }   

   public static Trace getImport(Module module, Path path, int line) {
      return new Trace(IMPORT, module, path, line);
   }  
   
   public static Trace getAllocate(Module module, Path path, int line) {
      return new Trace(ALLOCATE, module, path, line);
   }
   
   public static Trace getDebug(Module module, Path path, int line) {
      return new Trace(DEBUG, module, path, line);
   }
   
   private final TraceType type;
   private final Module module;
   private final Path path;
   private final int line;
   
   public Trace(TraceType type, Module module, Path path, int line) {
      this.module = module;
      this.path = path;
      this.line = line;
      this.type = type;
   }

   public TraceType getType() {
      return type;
   }
   
   public Module getModule(){
      return module;
   }

   public Path getPath() {
      return path;
   }

   public int getLine() {
      return line;
   }
   
   @Override
   public String toString() {
      return String.format("%s at line %s", path, line);
   }
}