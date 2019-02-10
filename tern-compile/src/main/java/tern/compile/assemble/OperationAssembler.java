package tern.compile.assemble;

import static tern.core.Reserved.INSTRUCTION_FILE;

import java.util.concurrent.Executor;

import tern.core.Context;
import tern.core.module.Path;
import tern.core.stack.ThreadStack;
import tern.parse.SyntaxNode;
import tern.tree.InstructionResolver;
import tern.tree.OperationResolver;

public class OperationAssembler implements Assembler {
   
   private final OperationTraverser traverser;
   private final OperationResolver resolver;
   private final OperationBuilder builder;
   private final Context context;

   public OperationAssembler(Context context, Executor executor) {
      this(context, executor, INSTRUCTION_FILE);
   }
   
   public OperationAssembler(Context context, Executor executor, String file) {
      this.builder = new OperationBuilder(context, executor);
      this.resolver = new InstructionResolver(context, file);
      this.traverser = new OperationTraverser(builder, resolver);
      this.context = context;
   }
   
   @Override
   public <T> T assemble(SyntaxNode token, Path path) throws Exception {
      ThreadStack stack = context.getStack();
      
      try {
         return (T)traverser.create(token, path);
      } finally {
         stack.clear();
      }
   }
}