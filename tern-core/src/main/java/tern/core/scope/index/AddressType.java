package tern.core.scope.index;

public enum AddressType {
   LOCAL,
   INSTANCE,
   STATIC,
   TYPE,
   MODULE;

   public Address getAddress(String name, int offset) {
      return new Address(this, name, offset);
   }
}
