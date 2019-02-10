package tern.tree.define;

import tern.tree.constraint.TraitConstraint;

public class TraitHierarchy extends ClassHierarchy{
   
   public TraitHierarchy(TraitConstraint... traits) {
      super(null, traits);     
   }
}
