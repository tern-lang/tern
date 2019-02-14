package org.ternlang.compile;

public interface Compiler {
   Executable compile(String source) throws Exception;  
}