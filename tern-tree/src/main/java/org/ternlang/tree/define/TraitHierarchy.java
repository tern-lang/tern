package org.ternlang.tree.define;

import org.ternlang.tree.constraint.TraitConstraint;

public class TraitHierarchy extends ClassHierarchy{
   
   public TraitHierarchy(TraitConstraint... traits) {
      super(null, traits);     
   }
}
