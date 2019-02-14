package org.ternlang.compile.verify;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.ternlang.core.trace.Trace;
import org.ternlang.core.trace.TraceErrorCollector;

public class ExecutableVerifier extends TraceErrorCollector implements Verifier {
   
   private final List<VerifyError> errors;
   
   public ExecutableVerifier() {
      this.errors = new CopyOnWriteArrayList<VerifyError>();
   }

   @Override
   public void compileError(Exception cause, Trace trace) {
      VerifyError error = new VerifyError(cause, trace);
      errors.add(error);
   }

   @Override
   public void verify(){
      if(!errors.isEmpty()) {
         throw new VerifyException("Compilation errors found " + errors, errors);
      }
   }
}
