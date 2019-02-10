package tern.core.platform;

public interface Bridge {
   <T> T getInstance();
   void setInstance(Object object);
}