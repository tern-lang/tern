package org.ternlang.core.convert;

import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeLoader;

public class ConstraintInspector extends TypeInspector{
   
   private final CastChecker checker;
   private final TypeLoader loader;
   
   public ConstraintInspector(TypeLoader loader, CastChecker checker) {
      this.checker = checker;
      this.loader = loader;
   }
   
   public boolean isLike(Type type, Class require) throws Exception {
      Type actual = loader.loadType(require);
      Score score = checker.toType(type, actual);
      
      return score.isValid();
   }
}