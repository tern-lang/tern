package org.ternlang.compile.assemble;

import static org.ternlang.core.Reserved.GRAMMAR_FILE;

import java.util.concurrent.Executor;

import org.ternlang.core.Context;
import org.ternlang.core.Statement;
import org.ternlang.core.link.Package;
import org.ternlang.core.link.StatementPackage;
import org.ternlang.core.module.FilePathConverter;
import org.ternlang.core.module.Path;
import org.ternlang.core.module.PathConverter;
import org.ternlang.parse.SyntaxCompiler;
import org.ternlang.parse.SyntaxNode;
import org.ternlang.parse.SyntaxParser;

public class PackageBuilder {

   private final SyntaxCompiler compiler;
   private final PathConverter converter;
   private final Assembler assembler;   
   
   public PackageBuilder(Context context, Executor executor) {
      this.compiler = new SyntaxCompiler(GRAMMAR_FILE);
      this.assembler = new OperationAssembler(context, executor);
      this.converter = new FilePathConverter();
   }

   public Package create(Path path, String source, String grammar) throws Exception {
      String resource = path.getPath();
      SyntaxParser parser = compiler.compile();
      SyntaxNode node = parser.parse(resource, source, grammar); // source could be split here!
      Statement statement = assembler.assemble(node, path);
      String module = converter.createModule(resource);

      return new StatementPackage(statement, path, module);
   } 
}