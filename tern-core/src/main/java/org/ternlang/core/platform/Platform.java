package org.ternlang.core.platform;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.ternlang.core.type.Type;
import org.ternlang.core.function.Invocation;

public interface Platform {
   Invocation createSuperConstructor(Type type, Type base);
   Invocation createSuperMethod(Type type, Method method);
   Invocation createConstructor(Type type, Constructor constructor);
   Invocation createMethod(Type type, Method method);
}