package tern.core.resume;

public interface Answer {
   void success(Object value);
   void failure(Throwable cause);
}
