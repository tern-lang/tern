package org.ternlang.tree.constraint;

import static java.util.Collections.EMPTY_LIST;
import static org.ternlang.core.ModifierType.ENUM;

import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.tree.NameReference;
import org.ternlang.tree.define.TypeName;
import org.ternlang.tree.literal.TextLiteral;

public class EnumName implements TypeName {
   
   private final NameReference reference;

   public EnumName(TextLiteral literal) {
      this.reference = new NameReference(literal);
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