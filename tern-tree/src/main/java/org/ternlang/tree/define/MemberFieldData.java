package org.ternlang.tree.define;

import org.ternlang.core.Evaluation;
import org.ternlang.core.constraint.Constraint;

public class MemberFieldData {

   private final Constraint constraint;
   private final Evaluation value;
   private final String alias;
   private final String name;
   
   public MemberFieldData(String name, String alias, Constraint constraint, Evaluation value) {
      this.constraint = constraint;
      this.value = value;
      this.alias = alias;
      this.name = name;
   }

   public String getName() {
      return name;
   }
   
   public String getAlias(){
      return alias;
   }

   public Evaluation getValue() {
      return value;
   }

   public Constraint getConstraint() {
      return constraint;
   }
}