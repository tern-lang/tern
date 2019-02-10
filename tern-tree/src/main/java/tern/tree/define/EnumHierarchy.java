package tern.tree.define;

import tern.tree.constraint.TraitConstraint;

public class EnumHierarchy extends ClassHierarchy{
   
   public EnumHierarchy(TraitConstraint... traits) {
      super(null, traits);     
   }
}
