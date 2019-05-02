package org.ternlang.core.type;

public enum Phase {
   CREATE, // Create types and functions
   DEFINE, // Define methods and properties
   RESOLVE, // Resolve types and properties
   COMPILE, // Perform static analysis
   EXECUTE // Execute the program
}