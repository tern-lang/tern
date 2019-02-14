package org.ternlang.tree.define;

import static org.ternlang.core.ModifierType.CONSTANT;
import static org.ternlang.core.ModifierType.STATIC;

import org.ternlang.core.Evaluation;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.TypeState;
import org.ternlang.core.type.Type;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.type.TypeBody;
import org.ternlang.tree.ModifierData;
import org.ternlang.tree.literal.TextLiteral;

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