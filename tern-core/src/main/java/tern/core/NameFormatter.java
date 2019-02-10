package tern.core;

import tern.core.error.InternalArgumentException;

public class NameFormatter {
   
   private static final String[] DIMENSIONS = {"", "[]", "[][]", "[][][]" };     
   private static final String DIMENSION = "[]";
   
   public NameFormatter(){
      super();
   }
   
   public String formatFullName(Class type) {
      Class entry = type.getComponentType();
      
      if(entry != null) {
         return formatFullName(entry) + DIMENSION;
      }
      return type.getName();
   }
   
   public String formatShortName(Class type) {
      Class entry = type.getComponentType();
      
      if(entry != null) {
         return formatShortName(entry) + DIMENSION;
      }
      String name = type.getName();
      int index = name.lastIndexOf('.');
      int length = name.length();
      
      if(index > 0) {
         return name.substring(index+1, length);
      }
      return name;
   }
   
   public String formatShortName(String type) {
      int index = type.lastIndexOf('.');
      int length = type.length();
      
      if(index > 0) {
         return type.substring(index+1, length);
      }
      return type;
   }
   
   public String formatFullName(String module, String name) {
      if(name == null) {
         return module;
      }
      if(module != null) { // is a null module legal?
         int index = module.lastIndexOf(".");
         char first = module.charAt(index == -1 ? 0 : index + 1);
         
         if(Character.isUpperCase(first)) {
            return module + '$' + name; 
         }         
         return module + "." + name;
      }
      return name;
   }
   
   public String formatArrayName(String type, int size) {
      int limit = DIMENSIONS.length;
      
      if(size >= DIMENSIONS.length) {
         throw new InternalArgumentException("Maximum of " + limit + " dimensions exceeded");
      }
      return type + DIMENSIONS[size];
   }
   
   public String formatArrayName(String module, String name, int size) {
      int limit = DIMENSIONS.length;
      
      if(size >= DIMENSIONS.length) {
         throw new InternalArgumentException("Maximum of " + limit + " dimensions exceeded");
      }
      String bounds = DIMENSIONS[size];
      String type = formatFullName(module, name);
            
      return type + bounds;
   }
   
   public String formatOuterName(String type) {
      if(type != null) {
         int index = type.lastIndexOf('$');
         
         if(index > 0) {
            return type.substring(0, index);
         }
         return type;
      }
      return null;
   } 
   
   public String formatOuterName(String module, String name) {
      if(name != null) {
         int index = name.lastIndexOf('$');
         
         if(index > 0) {
            String parent = name.substring(0, index);
            int length = parent.length();
            
            if(length > 0) {
               return formatFullName(module, parent);
            }
         }
      }
      return null;
   }   
   
   public String formatInnerName(String type) {
      if(type != null) {
         String name = formatShortName(type);
         int index = name.lastIndexOf('$');
         int length = name.length();
         
         if(index > 0) {
            return name.substring(index + 1, length);
         }
         return name;
      }
      return null;
   }
   
   public String formatLocalName(String type) {
      if(type != null) {
         String name = formatTopName(type);
         int index = name.lastIndexOf('.');
         int length = name.length();
         
         if(index > 0) {
            return name.substring(index+1, length);
         }
         return name;
      }
      return null;
   }
   
   public String formatTopName(String type) {
      if(type != null) {
         int index = type.indexOf('$');
         
         if(index > 0) {
            return type.substring(0, index);
         }
         return type;
      }
      return null;
   }
   
   public String formatTopName(String module, String name) {
      if(name != null) {
         int index = name.indexOf('$');
         
         if(index > 0) {
            String parent = name.substring(0, index);
            int length = parent.length();
            
            if(length > 0) {
               return formatFullName(module, parent);
            }
         }
         return formatFullName(module, name);
      }
      return null;
   }
}