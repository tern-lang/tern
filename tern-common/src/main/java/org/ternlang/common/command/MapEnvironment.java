package org.ternlang.common.command;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapEnvironment implements Environment {

   private final Map<String, String> variables;

   public MapEnvironment(Map<String, String> variables) {
      this.variables = variables;
   }

   @Override
   public ProcessBuilder createProcess(String... command) {
      return createProcess(Arrays.asList(command));
   }

   @Override
   public ProcessBuilder createProcess(List<String> command) {
      ProcessBuilder builder = new ProcessBuilder(command);

      if (!variables.isEmpty()) {
         Set<String> names = variables.keySet();

         for (String name : names) {
            String value = variables.get(name);
            builder.environment().put(name, value);
         }
      }
      return builder;
   }
   
   @Override
   public String toString() {
      return String.valueOf(variables);
   }

}