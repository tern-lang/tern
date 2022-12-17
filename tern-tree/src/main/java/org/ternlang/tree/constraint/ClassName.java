package org.ternlang.tree.constraint;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.link.ImportManager;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypePart;
import org.ternlang.tree.NameReference;
import org.ternlang.tree.define.TypeName;
import org.ternlang.tree.literal.TextLiteral;

import java.util.List;

import static org.ternlang.core.ModifierType.CLASS;

public class ClassName implements TypeName {
   
   private final NameReference reference;
   private final GenericList generics;
   private final TypePart part;

   public ClassName(Evaluation literal, GenericList generics) {
      this(literal, generics, null);
   }

   public ClassName(Evaluation literal, GenericList generics, TypePart part) {
      this.reference = new NameReference(literal);
      this.generics = generics;
      this.part = part;
   }

   public TypePart getConstructor(Scope scope) throws Exception {
      return part;
   }
   
   @Override
   public int getModifiers(Scope scope) throws Exception{
      return CLASS.mask;
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
      List<Constraint> constraints = generics.getGenerics(scope);
      Module module = scope.getModule();
      ImportManager manager = module.getManager();
      
      for(Constraint constraint : constraints) {
         Type type = constraint.getType(scope);         
         String alias = constraint.getName(scope);
      
         if(alias != null) {
            Type parent = scope.getType();
            String prefix = parent.getName();
            
            manager.addImport(type, prefix +'$' +alias);            
         }
      }
      return constraints;
   }
}