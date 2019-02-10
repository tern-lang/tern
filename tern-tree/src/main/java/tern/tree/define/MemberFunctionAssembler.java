package tern.tree.define;

import java.util.List;

import tern.core.Statement;
import tern.core.constraint.Constraint;
import tern.core.constraint.DeclarationConstraint;
import tern.core.function.Signature;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.tree.ModifierChecker;
import tern.tree.ModifierList;
import tern.tree.constraint.FunctionName;
import tern.tree.function.ParameterList;

public class MemberFunctionAssembler {
   
   private final DeclarationConstraint constraint;
   private final ParameterList parameters;
   private final ModifierChecker checker;
   private final FunctionName identifier;
   private final ModifierList list;
   private final Statement body;
   
   public MemberFunctionAssembler(ModifierList list, FunctionName identifier, ParameterList parameters, Constraint constraint, Statement body){
      this.constraint = new DeclarationConstraint(constraint);
      this.checker = new ModifierChecker(list);
      this.identifier = identifier;
      this.parameters = parameters;
      this.list = list;
      this.body = body;
   } 

   public MemberFunctionBuilder assemble(Scope scope, int mask) throws Exception {
      int modifiers = list.getModifiers();
      String name = identifier.getName(scope);
      List<Constraint> generics = identifier.getGenerics(scope);
      Signature signature = parameters.create(scope, generics);
      Constraint require = constraint.getConstraint(scope, modifiers | mask);
      
      if(checker.isStatic()) {
         return new StaticFunctionBuilder(signature, body, require, name, modifiers | mask);
      }
      return new InstanceFunctionBuilder(signature, body, require, name, modifiers | mask);
      
   }
}