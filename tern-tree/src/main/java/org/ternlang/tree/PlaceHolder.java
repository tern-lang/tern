package org.ternlang.tree;

import org.ternlang.core.Compilation;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.literal.TextLiteral;
import org.ternlang.tree.variable.Variable;

public class PlaceHolder implements Compilation {

   private final TextLiteral identifier;
   private final Variable variable;

   public PlaceHolder(StringToken token) {
      this.identifier = new TextLiteral(token);
      this.variable = new Variable(identifier, true);
   }

   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      return variable.compile(module, path, line);
   }
}
