package tern.common.command;

public interface Command {
   Console execute(Environment environment) throws Exception;
}