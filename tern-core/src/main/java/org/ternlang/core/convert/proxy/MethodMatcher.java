package org.ternlang.core.convert.proxy;

import java.lang.reflect.Method;

import org.ternlang.core.function.Invocation;

public interface MethodMatcher {
   Invocation match(Object proxy, Method method, Object[] arguments) throws Throwable;
}
