package tern.core.platform;

public enum PlatformType {
   STANDARD("java.awt.Graphics"),
   ANDROID("android.os.Build");
   
   private final String type;
   
   private PlatformType(String type) {
      this.type = type;
   }
   
   public static PlatformType resolveType() {
      PlatformType[] types = PlatformType.values();
      
      for(PlatformType type : types) {
         try {
            Class.forName(type.type); // check if this is android
         }catch(Exception e) {
            continue;
         }
         return type;
      }
      return STANDARD;
   }
}