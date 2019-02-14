package org.ternlang.common;

public interface Progress<T extends Enum> {
   boolean done(T phase);
   boolean wait(T phase);
   boolean wait(T phase, long duration);
   boolean pass(T phase);
}