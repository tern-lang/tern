package tern.core.type.index;

public interface MethodCall<T> {
   Object call(T object, Object[] arguments) throws Exception;
}