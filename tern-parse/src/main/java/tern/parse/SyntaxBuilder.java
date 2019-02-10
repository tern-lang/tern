package tern.parse;

public interface SyntaxBuilder extends TokenReader {
   SyntaxBuilder mark(int grammar); 
   int position(); 
   void commit();
   int reset();
   int peek();
}