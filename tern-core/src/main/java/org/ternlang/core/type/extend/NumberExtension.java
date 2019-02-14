package org.ternlang.core.type.extend;

public interface NumberExtension<T extends Number> {
   Number abs(T number);
   Number ceil(T number);
   Number floor(T number);
   Number round(T number);
   Number round(T number, int places);
}
