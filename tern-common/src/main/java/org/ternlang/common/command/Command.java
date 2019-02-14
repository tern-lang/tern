package org.ternlang.common.command;

public interface Command {
   Console execute(Environment environment) throws Exception;
}