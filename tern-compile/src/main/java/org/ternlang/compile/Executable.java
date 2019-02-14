package org.ternlang.compile;

import org.ternlang.core.Any;
import org.ternlang.core.scope.Model;

public interface Executable extends Any {   
   void execute() throws Exception;
   void execute(Model model) throws Exception;
   void execute(Model model, boolean test) throws Exception;
}