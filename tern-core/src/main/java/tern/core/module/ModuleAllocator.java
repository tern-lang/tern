package tern.core.module;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import tern.core.Context;
import tern.core.NameFormatter;
import tern.core.error.InternalArgumentException;
import tern.core.error.InternalStateException;

public class ModuleAllocator {

   private final NameFormatter formatter;
   private final AtomicInteger counter;
   private final Executor executor;
   private final Context context;
   private final int limit;
   
   public ModuleAllocator(Context context, Executor executor) {
      this(context, executor, 100000);
   }
   
   public ModuleAllocator(Context context, Executor executor, int limit) {
      this.formatter = new NameFormatter();
      this.counter = new AtomicInteger(1);
      this.executor = executor;
      this.context = context;
      this.limit = limit;
   }
   
   public Module allocate(String name, Path path) {
      if (name == null) {
         throw new InternalArgumentException("Module name was null");
      }
      String local = formatter.formatLocalName(name);
      int order = counter.getAndIncrement();
      
      if(order > limit) {
         throw new InternalStateException("Module limit of " + limit + " exceeded");
      }
      return new ContextModule(context, executor, path, name, local, order); 
   }
}