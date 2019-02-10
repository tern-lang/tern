package tern.core.convert;

import java.util.Set;

import tern.core.Context;
import tern.core.module.Module;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeExtractor;

public class InstanceOfChecker {

   private final PrimitivePromoter promoter;
   private final AliasResolver resolver;

   public InstanceOfChecker() {
      this.promoter = new PrimitivePromoter();
      this.resolver = new AliasResolver();
   }
   
   public boolean isInstanceOf(Scope scope, Type instance, Type constraint) {
      if (constraint != null && instance != null) {
         try {
            Type actual = resolver.resolve(constraint);
            Module module = scope.getModule();
            Context context = module.getContext();
            TypeExtractor extractor = context.getExtractor();
            Set<Type> types = extractor.getTypes(instance);

            if (!types.contains(actual)) {
               Class actualType = instance.getType();
               Class requireType = actual.getType();

               return isInstanceOf(scope, actualType, requireType);
            }
            return true;
         } catch (Exception e) {
            return false;
         }
      }
      return constraint == null;
   }
   
   public boolean isInstanceOf(Scope scope, Object instance, Object constraint) {
      if (constraint != null && instance != null) {
         try {
            Module module = scope.getModule();
            Context context = module.getContext();
            TypeExtractor extractor = context.getExtractor();
            Type actual = extractor.getType(instance);
            Type require = (Type) constraint;

            return isInstanceOf(scope, actual, require);
         } catch (Exception e) {
            return false;
         }
      }
      return false;
   }

   private boolean isInstanceOf(Scope scope, Class instance, Class constraint) {
      if (constraint != null && instance != null) {
         try {
            Class instanceEntry = instance.getComponentType();
            Class constraintEntry = constraint.getComponentType();
            
            if(instanceEntry != null && constraintEntry != null) {
               return isInstanceOf(scope, instanceEntry, constraintEntry);
            }
            Class instanceType = promoter.promote(instance);
            Class constraintType = promoter.promote(constraint);

            if(instanceType == constraintType) {
               return true;
            }
         } catch (Exception e) {
            return false;
         }
      }
      return constraint == Object.class;
   }   
}