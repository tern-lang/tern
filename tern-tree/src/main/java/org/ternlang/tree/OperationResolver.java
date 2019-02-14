package org.ternlang.tree;

public interface OperationResolver {
   Operation resolve(String name) throws Exception;
}