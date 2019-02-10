package tern.core;

import tern.core.module.Module;
import tern.core.module.Path;

public interface Compilation {
   Object compile(Module module, Path path, int line) throws Exception;
}