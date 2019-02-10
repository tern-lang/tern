package tern.compile;

public interface Compiler {
   Executable compile(String source) throws Exception;  
}