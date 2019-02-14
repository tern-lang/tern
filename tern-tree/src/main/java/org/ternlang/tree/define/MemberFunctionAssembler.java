package org.ternlang.tree.define;

import java.util.List;

import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.DeclarationConstraint;
import org.ternlang.core.function.Signature;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.tree.ModifierChecker;
import org.ternlang.tree.ModifierList;
import org.ternlang.tree.constraint.FunctionName;
import org.ternlang.tree.function.ParameterList;

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