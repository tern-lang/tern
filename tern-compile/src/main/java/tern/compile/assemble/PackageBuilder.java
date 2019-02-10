package tern.compile.assemble;

import static tern.core.Reserved.GRAMMAR_FILE;

import java.util.concurrent.Executor;

import tern.core.Context;
import tern.core.Statement;
import tern.core.link.Package;
import tern.core.link.StatementPackage;
import tern.core.module.FilePathConverter;
import tern.core.module.Path;
import tern.core.module.PathConverter;
import tern.parse.SyntaxCompiler;
import tern.parse.SyntaxNode;
import tern.parse.SyntaxParser;

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