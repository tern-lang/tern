package tern.parse;

public interface SyntaxChecker extends TokenReader {
   void validate();
   void commit(int start, int grammar);
   int reset(int start, int grammar); 
   int mark(int grammar); 
   int position(); 
   int peek();
}