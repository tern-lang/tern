package tern.core.scope;

public class EmptyModel implements Model {

   @Override
   public Object getAttribute(String name) {
      return null;
   }

}