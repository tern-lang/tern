package org.ternlang.core.resume;

public interface Answer {
   void success(Object value);
   void failure(Throwable cause);
}
