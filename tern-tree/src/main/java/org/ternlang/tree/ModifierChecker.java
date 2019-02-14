package org.ternlang.tree;

import org.ternlang.core.ModifierType;

public class ModifierChecker extends ModifierData {

   private ModifierData list;
   private int modifiers;
   
   public ModifierChecker(ModifierData list) {
      this.modifiers = -1;
      this.list = list;
   }
   
   @Override
   public int getModifiers() {
      if(modifiers == -1) {
         modifiers = list.getModifiers();
      }
      return modifiers;
   }
   
   public boolean isStatic() {
      if(modifiers == -1) {
         modifiers = list.getModifiers();
      }
      return ModifierType.isStatic(modifiers);
   }
   
   public boolean isConstant() {
      if(modifiers == -1) {
         modifiers = list.getModifiers();
      }
      return ModifierType.isConstant(modifiers);
   }
   
   public boolean isPublic() {
      if(modifiers == -1) {
         modifiers = list.getModifiers();
      }
      return ModifierType.isPublic(modifiers);
   }
   
   public boolean isPrivate() {
      if(modifiers == -1) {
         modifiers = list.getModifiers();
      }
      return ModifierType.isPrivate(modifiers);
   }
   
   public boolean isOverride() {
      if(modifiers == -1) {
         modifiers = list.getModifiers();
      }
      return ModifierType.isOverride(modifiers);
   }
}