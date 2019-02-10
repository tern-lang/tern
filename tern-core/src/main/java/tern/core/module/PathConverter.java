package tern.core.module;

public interface PathConverter {
   Path createPath(String resource);
   String createModule(String resource);
}