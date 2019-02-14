package org.ternlang.compile.assemble;

import java.util.List;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.module.Path;
import org.ternlang.core.type.Type;
import org.ternlang.parse.Line;
import org.ternlang.parse.SyntaxNode;
import org.ternlang.parse.Token;
import org.ternlang.tree.Operation;
import org.ternlang.tree.OperationResolver;

public class OperationTraverser {
   
   private final OperationResolver resolver;
   private final OperationBuilder builder;
   private final Object[] empty;

   public OperationTraverser(OperationBuilder builder, OperationResolver resolver) {
      this.empty = new Object[]{};
      this.resolver = resolver;
      this.builder = builder;
   }

   public Object create(SyntaxNode node, Path path) throws Exception {
      return create(node, path, 0);
   }
   
   private Object create(SyntaxNode node, Path path, int depth) throws Exception {
      List<SyntaxNode> children = node.getNodes();
      String grammar = node.getGrammar();
      Operation type = resolver.resolve(grammar);
      int size = children.size();
      
      if (type == null) {
         return createChild(node, path, children, type,depth);
      }
      if (size > 0) {
         return createBranch(node, path, children, type,depth);
      }
      return createLeaf(node, path, children, type,depth);
   }
   
   private Object createBranch(SyntaxNode node, Path path, List<SyntaxNode> children, Operation operation, int depth) throws Exception {
      Type type = operation.getType();
      Line line = node.getLine();
      int size = children.size();
      
      if(size > 0) {
         Object[] arguments = new Object[size];

         for (int i = 0; i < size; i++) {
            SyntaxNode child = children.get(i);
            Object argument = create(child, path, depth+1);

            arguments[i] = argument;
         }
         return builder.create(type, arguments, line);
      }
      return builder.create(type, empty, line);
   }

   private Object createChild(SyntaxNode node, Path path, List<SyntaxNode> children, Operation operation, int depth) throws Exception {
      String grammar = node.getGrammar();
      int size = children.size();
      
      if (size > 1) {
         throw new InternalStateException("No type defined for '" + grammar + "'");
      }
      if (size > 0) {
         SyntaxNode child = children.get(0);

         if (child == null) {
            throw new InternalStateException("No child for '" + grammar + "'");
         }
         return create(child, path, depth);
      }
      if (size > 0) {
         return createBranch(node, path, children, operation, depth);
      }
      return createLeaf(node, path, children, operation, depth);
   }
   
   private Object createLeaf(SyntaxNode node, Path path, List<SyntaxNode> children, Operation operation, int depth) throws Exception {
      Token token = node.getToken();     
      Line line = node.getLine();
      
      if (operation != null) {
         Type type = operation.getType();
         
         if (token == null) {
            return builder.create(type, empty, line); // no line number????
         }      
         return builder.create(type, new Object[]{token}, line);
      }
      return token;
   }
}