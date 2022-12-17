package org.ternlang.tree.define;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;
import org.ternlang.core.type.TypePart;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.ModifierList;
import org.ternlang.tree.annotation.AnnotationList;
import org.ternlang.tree.literal.TextLiteral;

public class ImplicitFieldBuilder {

   private final AnnotationList annotations;
   private final ModifierList modifiers;
   private final Module module;
   private final Path path;
   private final int line;

   public ImplicitFieldBuilder(Module module, Path path, int line) {
      this.annotations = new AnnotationList();
      this.modifiers = new ModifierList();
      this.module = module;
      this.path = path;
      this.line = line;
   }

   public TypePart create(String type, Constraint constraint) throws Exception {
      StringToken name = new StringToken(type);
      TextLiteral literal = new TextLiteral(name);
      MemberFieldDeclaration declaration = new MemberFieldDeclaration(literal, constraint);
      MemberField field = new MemberField(annotations, modifiers, declaration);

      return field.compile(module, path, line);
   }
}
