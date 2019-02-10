package tern.core.platform;

import static tern.core.Reserved.IMPORT_TERN;

public class PlatformNameBuilder {
   
   private static final String DEFAULT_SUFFIX = "Platform";
   private static final String DEFAULT_QUALIFIER = "platform";
   
   private final String qualifier;
   private final String suffix;
   
   public PlatformNameBuilder() {
      this(DEFAULT_QUALIFIER, DEFAULT_SUFFIX);
   }
   
   public PlatformNameBuilder(String qualifier, String suffix) {
      this.qualifier = qualifier;
      this.suffix = suffix;
   }

   public String createFullName(PlatformType platform) {
      String module = createPackage(platform);
      String name = createClassName(platform);
      
      return module + "." + name;
   }
   
   private String createClassName(PlatformType platform) {
      String name = platform.name();
      String token = name.toLowerCase();
      String prefix = token.substring(1);
      char first = name.charAt(0);
      
      return first + prefix + suffix;
   }
   
   private String createPackage(PlatformType platform) {
      String name = platform.name();
      String token = name.toLowerCase();
      
      return IMPORT_TERN + qualifier + "." + token;
   }
}