package tern.parse;

import java.util.List;

public interface SyntaxNode {
   List<SyntaxNode> getNodes();
   String getGrammar();
   Token getToken();
   Line getLine();
   int getStart();
}