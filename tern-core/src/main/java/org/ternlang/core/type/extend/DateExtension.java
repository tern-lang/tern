package org.ternlang.core.type.extend;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateExtension {

   public DateExtension(){
      super();
   }

   public int getYear(Date date) {      
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return calendar.get(Calendar.YEAR);
   }

   public int getYear(Date date, TimeZone zone) {      
      Calendar calendar = Calendar.getInstance(zone);
      calendar.setTime(date);
      return calendar.get(Calendar.YEAR);
   }
   
   public int getMonth(Date date) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return 1 + calendar.get(Calendar.MONTH);
   }
   
   public int getMonth(Date date, TimeZone zone) {
      Calendar calendar = Calendar.getInstance(zone);
      calendar.setTime(date);
      return 1 + calendar.get(Calendar.MONTH);
   }

   public int getDay(Date date) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return calendar.get(Calendar.DAY_OF_MONTH);
   }
   
   public int getDay(Date date, TimeZone zone) {
      Calendar calendar = Calendar.getInstance(zone);
      calendar.setTime(date);
      return calendar.get(Calendar.DAY_OF_MONTH);
   }

   public int getHour(Date date) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return calendar.get(Calendar.HOUR_OF_DAY);
   }
   
   public int getHour(Date date, TimeZone zone) {
      Calendar calendar = Calendar.getInstance(zone);
      calendar.setTime(date);
      return calendar.get(Calendar.HOUR_OF_DAY);
   }

   public int getMinute(Date date) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return calendar.get(Calendar.MINUTE);
   }
   
   public int getMinute(Date date, TimeZone zone) {
      Calendar calendar = Calendar.getInstance(zone);
      calendar.setTime(date);
      return calendar.get(Calendar.MINUTE);
   }

   public int getSecond(Date date) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return calendar.get(Calendar.SECOND);
   }
   
   public int getSecond(Date date, TimeZone zone) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return calendar.get(Calendar.SECOND);
   }

   public Date minusSeconds(Date date, int seconds) {
      return addSeconds(date, -seconds);
   }

   public Date minusMinutes(Date date, int minutes) {
      return addMinutes(date, -minutes);
   }

   public Date minusHours(Date date, int hours) {
      return addHours(date, -hours);
   }

   public Date minusDays(Date date, int days) {
      return addDays(date, -days);
   }

   public Date minusMonths(Date date, int months) {
      return addMonths(date, -months);
   }

   public Date minusYears(Date date, int years) {
      return addDays(date, -years);
   }

   public Date addSeconds(Date date, int seconds) {
      return add(date, seconds, Calendar.SECOND);
   }

   public Date addMinutes(Date date, int minutes) {
      return add(date, minutes, Calendar.MINUTE);
   }

   public Date addHours(Date date, int hours) {
      return add(date, hours, Calendar.HOUR);
   }

   public Date addDays(Date date, int days) {
      return add(date, days, Calendar.DAY_OF_YEAR);
   }

   public Date addMonths(Date date, int months) {
      return add(date, months, Calendar.MONTH);
   }

   public Date addYears(Date date, int years) {
      return add(date, years, Calendar.YEAR);
   }

   public Date add(Date date, int count, int unit) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.add(unit, count);
      return calendar.getTime();
   }
   
   public String format(Date date, String pattern) {
      return format(date, pattern, null);
   }
   
   public String format(Date date, String pattern, TimeZone zone) {
      DateFormat format = new SimpleDateFormat(pattern);
      
      if(zone != null) {
         format.setTimeZone(zone);
      }
      return format.format(date);
   }
}