package org.ternlang.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateFormatter {
   
   private static final DateFormatThreadLocal LOCAL = new DateFormatThreadLocal();
   
   public static String format(String format, Object date) {
      try {
         return LOCAL.get().create(format).format(date);
      }catch(Exception e) {
         throw new IllegalStateException("Could not format " + date, e);
      }
   }
   
   public static Date parse(String format, String date) {
      try {
         return LOCAL.get().create(format).parse(date);
      }catch(Exception e) {
         throw new IllegalStateException("Could not parse " + date, e);
      }
   }
   
   private static class DateFormatThreadLocal extends ThreadLocal<DateFormatBuilder> {
      
      @Override
      protected DateFormatBuilder initialValue() {
         return new DateFormatBuilder();
      }
   }
   
   private static class DateFormatBuilder {
      
      private Map<String, DateFormat> formats;
      
      public DateFormatBuilder() {
         this.formats = new HashMap<String, DateFormat>();
      }
      
      public DateFormat create(String expression) {
         DateFormat format = formats.get(expression);
         
         if(format == null) {
            format = new SimpleDateFormat(expression);
            formats.put(expression, format);
         }
         return format;
      }
   }
}
