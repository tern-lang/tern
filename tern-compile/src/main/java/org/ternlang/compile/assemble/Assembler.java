package org.ternlang.compile.assemble;

import org.ternlang.core.module.Path;
import org.ternlang.parse.SyntaxNode;

public interface Assembler {
   <T> T assemble(SyntaxNode token, Path path) throws Exception;
}