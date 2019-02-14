package org.ternlang.tree.define;

import org.ternlang.tree.constraint.TraitConstraint;

public class EnumHierarchy extends ClassHierarchy{
   
   public EnumHierarchy(TraitConstraint... traits) {
      super(null, traits);     
   }
}
