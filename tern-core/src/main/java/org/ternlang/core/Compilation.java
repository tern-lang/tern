package org.ternlang.core;

import org.ternlang.core.module.Module;
import org.ternlang.core.module.Path;

public interface Compilation {
   Object compile(Module module, Path path, int line) throws Exception;
}