package org.ternlang.tree;

import org.ternlang.common.io.PropertyReader;
import org.ternlang.core.error.InternalStateException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class InstructionReader extends PropertyReader<Set<Instruction>>{

   private static final String USE = "use";

   public InstructionReader(String file) {
      super(file);
   }

   @Override
   protected Set<Instruction> create(String name, char[] data, int off, int length, int line) {
      Set<Instruction> instructions = new LinkedHashSet<>();
      Set<String> done = new HashSet<>();

      if(!name.startsWith(USE)) {
         throw new InternalStateException("Invalid statement from '" + file + "' at line " + line);
      }
      int size = USE.length();
      String namespace = name.substring(size).trim();

      if(namespace.isEmpty()) {
         throw new InternalStateException("Invalid package name from '" + file + "' at line " + line);
      }
      Deque<String> tokens = createImports(data, off, length, line);

      while(!tokens.isEmpty()) {
         String type = tokens.pollFirst();
         String key = tokens.pollFirst();

         if(!done.add(key)) {
            throw new InternalStateException("Duplicate '" + key + "' from '" + file + "' at line " + line);
         }
         Instruction instruction = new Instruction(namespace + "." + type, key);
         instructions.add(instruction);
      }
      return instructions;
   }

   private Deque<String> createImports(char[] data, int off, int length, int line) {
      Deque<String> imports = new ArrayDeque<>();
      int count = length + off - 1;

      for(int i = count; i >= 0; i--) {
         char next = data[i];

         if(delimiter(next)) {
            if(count > i) {
               String token = format(data, i+1, count-i);
               imports.add(token);
               count = i-1;
            }
         }
         if(group(next)) {
            int size = imports.size();

            if(size % 2 != 0) {
               throw new InternalStateException("Invalid bindings from '" + file + "' at line " + line);
            }
            return imports;
         }
      }
      throw new InternalStateException("Invalid bindings from '" + file + "' at line " + line);
   }

   private boolean group(char value) {
      return value == '{';
   }

   private boolean delimiter(char value) {
      return value == ',' || value == '{' || value == '=';
   }

   @Override
   protected boolean separator(char value) {
      return value == '{';
   }

   @Override
   protected boolean terminal(char value) {
      return value == '}';
   }
}