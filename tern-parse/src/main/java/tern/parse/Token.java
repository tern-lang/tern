package tern.parse;

public interface Token<T> {
   T getValue();
   Line getLine();
   short getType();
}