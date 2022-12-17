package org.ternlang.tree.constraint;

import static java.util.Collections.EMPTY_LIST;
import static org.ternlang.core.ModifierType.ENUM;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypePart;
import org.ternlang.tree.NameReference;
import org.ternlang.tree.define.TypeName;

import java.util.List;

public class EnumName implements TypeName {
   
   private final NameReference reference;
   private final TypePart part;

   public EnumName(Evaluation literal) {
      this(literal, null);
   }

   public EnumName(Evaluation literal, TypePart part) {
      this.reference = new NameReference(literal);
      this.part = part;
   }

   public TypePart getConstructor(Scope scope) throws Exception {
      return part;
   }

   @Override
   public int getModifiers(Scope scope) throws Exception{
      return ENUM.mask;
   }
   
   @Override
   public String getName(Scope scope) throws Exception{ // called from outer class
      String name = reference.getName(scope);
      Type parent = scope.getType();
      
      if(parent != null) {
         String prefix = parent.getName();
         
         if(prefix != null) {
            return prefix + '$'+name;
         }
      }
      return name;
   }
   
   @Override
   public List<Constraint> getGenerics(Scope scope) throws Exception {
      return EMPTY_LIST;
   }
}