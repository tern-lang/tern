package tern.tree.constraint;

import static tern.core.ModifierType.CLASS;

import java.util.List;

import tern.core.constraint.Constraint;
import tern.core.link.ImportManager;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.tree.NameReference;
import tern.tree.define.TypeName;
import tern.tree.literal.TextLiteral;

public class ClassName implements TypeName {
   
   private final NameReference reference;
   private final GenericList generics;

   public ClassName(TextLiteral literal, GenericList generics) {
      this.reference = new NameReference(literal);
      this.generics = generics;
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