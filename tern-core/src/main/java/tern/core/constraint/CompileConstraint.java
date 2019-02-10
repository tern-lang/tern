package tern.core.constraint;

import static tern.core.type.Phase.DEFINE;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import tern.common.Progress;
import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.core.type.Phase;
import tern.core.type.Type;

public class CompileConstraint extends Constraint {
   
   private final AtomicBoolean defined;
   private final Constraint constraint;
   private final long duration;

   public CompileConstraint(Constraint constraint) {
      this(constraint, 10000);
   }
   
   public CompileConstraint(Constraint constraint, long duration) {
      this.defined = new AtomicBoolean();
      this.constraint = constraint;
      this.duration = duration;
   }
   
   @Override
   public String getName(Scope scope) {
      return constraint.getName(scope);
   }
   
   @Override
   public List<String> getImports(Scope scope) {
      return constraint.getImports(scope);
   }
   
   @Override
   public List<Constraint> getGenerics(Scope scope) {
      if(!defined.get()) {
         Type result = constraint.getType(scope);
         Progress<Phase> progress = result.getProgress();
         
         if(!progress.wait(DEFINE, duration)) {
            throw new InternalStateException("Type '" + result + "' not defined");
         }
         defined.set(true);
      }
      return constraint.getGenerics(scope);
   }
   
   @Override
   public Type getType(Scope scope) {
      if(!defined.get()) {
         Type result = constraint.getType(scope);
         Progress<Phase> progress = result.getProgress();
         
         if(!progress.wait(DEFINE, duration)) {
            throw new InternalStateException("Type '" + result + "' not defined");
         }
         defined.set(true);
      }
      return constraint.getType(scope);
   }

   @Override
   public String toString(){
      return String.valueOf(constraint);
   }
}