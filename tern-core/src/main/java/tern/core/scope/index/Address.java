package tern.core.scope.index;

public class Address {  

   private final AddressType type;
   private final String name;
   private final int offset;
   
   public Address(AddressType type, String name, int offset) {
      this.offset = offset;
      this.type = type;    
      this.name = name;
   }
   
   public AddressType getType() {
      return type;
   }
   
   public String getName() {
      return name;
   }
   
   public int getOffset() {
      return offset;
   }
   
   @Override
   public String toString() {
      return String.format("%s:%s@%s", type, name, offset);
   }
}
