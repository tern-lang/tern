package org.ternlang.tree.define;

import static org.ternlang.core.ModifierType.CONSTANT;
import static org.ternlang.core.ModifierType.PUBLIC;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.constraint.DeclarationConstraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.tree.literal.TextLiteral;

public class MemberFieldDeclaration {

   private final DeclarationConstraint constraint;
   private final MemberFieldReference identifier;
   private final Evaluation value;
   
   public MemberFieldDeclaration(TextLiteral identifier) {
      this(identifier, null, null);
   }
   
   public MemberFieldDeclaration(TextLiteral identifier, Constraint constraint) {      
      this(identifier, constraint, null);
   }
   
   public MemberFieldDeclaration(TextLiteral identifier, Evaluation value) {
      this(identifier, null, value);
   }
   
   public MemberFieldDeclaration(TextLiteral identifier, Constraint constraint, Evaluation value) {      
      this.constraint = new DeclarationConstraint(constraint);
      this.identifier = new MemberFieldReference(identifier);
      this.value = value;
   }   
   
   public MemberFieldData create(Scope scope, int modifiers) throws Exception {
      Type type = scope.getType();
      String alias = identifier.getName(type, modifiers);
      String name = identifier.getName(type, PUBLIC.mask);
      Constraint require = constraint.getConstraint(scope, modifiers);   
      
      if(value == null) {
         int mask = modifiers & ~CONSTANT.mask;
         Constraint blank = constraint.getConstraint(scope, mask); // const that is not assigned
         
         return new MemberFieldData(name, alias, blank, null);
      }
      return new MemberFieldData(name, alias, require, value);
   }
}