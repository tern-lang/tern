package tern.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tern.core.ModifierType;
import tern.core.function.Function;
import tern.core.property.Property;
import tern.core.type.Type;

public class StaticImportMatcher {

   public StaticImportMatcher() {
      super();
   }

   public List<Function> matchFunctions(Type type, String prefix) throws Exception {
      List<Function> functions = type.getFunctions();

      if (!functions.isEmpty()) {
         List<Function> matches = new ArrayList<Function>();

         for (Function function : functions) {
            int modifiers = function.getModifiers();

            if (ModifierType.isStatic(modifiers) && ModifierType.isPublic(modifiers)) {
               String name = function.getName();

               if (prefix == null || prefix.equals(name)) {
                  matches.add(function);
               }
            }
         }
         return matches;
      }
      return Collections.emptyList();
   }

   public List<Property> matchProperties(Type type, String prefix) throws Exception {
      List<Property> properties = type.getProperties();

      if (!properties.isEmpty()) {
         List<Property> matches = new ArrayList<Property>();

         for (Property property : properties) {
            int modifiers = property.getModifiers();

            if (ModifierType.isStatic(modifiers) && ModifierType.isPublic(modifiers)) {
               String name = property.getName();

               if (prefix == null || prefix.equals(name)) {
                  matches.add(property);
               }
            }
         }
         return matches;
      }
      return Collections.emptyList();
   }
}
