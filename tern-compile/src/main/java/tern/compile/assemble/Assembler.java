package tern.compile.assemble;

import tern.core.module.Path;
import tern.parse.SyntaxNode;

public interface Assembler {
   <T> T assemble(SyntaxNode token, Path path) throws Exception;
}