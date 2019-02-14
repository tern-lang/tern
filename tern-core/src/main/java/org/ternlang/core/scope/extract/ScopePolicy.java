package org.ternlang.core.scope.extract;

import static org.ternlang.core.type.Phase.COMPILE;
import static org.ternlang.core.type.Phase.EXECUTE;

import org.ternlang.core.type.Phase;

public enum ScopePolicy {
   COMPILE_CLOSURE(COMPILE, false, true, false),
   COMPILE_EVALUATE(COMPILE, true, true, false),
   COMPILE_MEMBER(COMPILE, false, true, false),
   COMPILE_SCRIPT(COMPILE, true, false, true),
   COMPILE_GENERICS(COMPILE, false, true, false),
   EXECUTE_MEMBER(EXECUTE, false, true, false),
   EXECUTE_SCRIPT(EXECUTE, true, false, true),
   EXECUTE_SUPER(EXECUTE, true, false, false);

   private final Phase phase;
   private final boolean reference;
   private final boolean extension;
   private final boolean globals;

   private ScopePolicy(Phase phase, boolean reference, boolean extension, boolean globals) {
      this.reference = reference;
      this.extension = extension;
      this.globals = globals;
      this.phase = phase;
   }

   public boolean isReference() {
      return reference;
   }

   public boolean isExtension() {
      return extension;
   }

   public boolean isGlobals() {
      return globals;
   }

   public boolean isCompiled() {
      return phase == EXECUTE;
   }
}
