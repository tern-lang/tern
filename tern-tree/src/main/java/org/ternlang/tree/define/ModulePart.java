package org.ternlang.tree.define;

import org.ternlang.core.Statement;
import org.ternlang.core.module.Module;

public interface ModulePart {
   Statement define(ModuleBody body, Module module) throws Exception;
}
