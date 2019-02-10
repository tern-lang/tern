package tern.core.type.extend;

import static tern.core.function.Origin.SYSTEM;

import java.util.ArrayList;
import java.util.List;

import tern.core.Context;
import tern.core.error.InternalStateException;
import tern.core.function.Function;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.TypeLoader;

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