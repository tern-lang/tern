package org.ternlang.core.type.extend;

import static org.ternlang.core.function.Origin.SYSTEM;

import java.util.ArrayList;
import java.util.List;

import org.ternlang.core.Context;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Function;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.TypeLoader;

public class ModuleExtender {
   
   private final List<Function> functions;
   private final Context context;
   
   public ModuleExtender(Context context) {
      this.functions = new ArrayList<Function>();
      this.context = context;
   }
   
   public synchronized void extend(Module module){
      List<Function> available = module.getFunctions();
      TypeLoader loader = context.getLoader();
      
      if(functions.isEmpty()) {
         FunctionExtractor extractor = new FunctionExtractor(loader, SYSTEM);
         
         try {
            List<Function> list = extractor.extract(module, Scope.class, ScopeExtension.class);
            
            for(Function function : list) {
               functions.add(function);
            }
         } catch(Exception e) {
            throw new InternalStateException("Could not export runtime", e);
         }
      }
      available.addAll(functions);
   }
}