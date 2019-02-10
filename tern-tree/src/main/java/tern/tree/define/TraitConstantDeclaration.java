package tern.tree.define;

import static tern.core.ModifierType.CONSTANT;
import static tern.core.ModifierType.STATIC;

import tern.core.Evaluation;
import tern.core.scope.Scope;
import tern.core.type.TypeState;
import tern.core.type.Type;
import tern.core.constraint.Constraint;
import tern.core.type.TypeBody;
import tern.tree.ModifierData;
import tern.tree.literal.TextLiteral;

public class TraitConstantDeclaration {
   
   private final MemberFieldDeclaration declaration;
   private final MemberFieldAssembler assembler;
   private final ModifierData modifiers;

   public TraitConstantDeclaration(TextLiteral identifier, Constraint constraint, Evaluation value) {
      this.declaration = new MemberFieldDeclaration(identifier, constraint, value);
      this.modifiers = new ModifierData(CONSTANT, STATIC);
      this.assembler = new MemberFieldAssembler(modifiers);
   }
   
   public TypeState declare(TypeBody body, Type type) throws Exception {
      int mask = modifiers.getModifiers();
      Scope scope = type.getScope();
      MemberFieldData data = declaration.create(scope, mask);
      
      return assembler.assemble(data);
   }
}