package tern.tree.define;

import tern.core.Statement;
import tern.core.module.Module;

public interface ModulePart {
   Statement define(ModuleBody body, Module module) throws Exception;
}
