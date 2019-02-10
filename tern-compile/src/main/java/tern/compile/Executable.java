package tern.compile;

import tern.core.Any;
import tern.core.scope.Model;

public interface Executable extends Any {   
   void execute() throws Exception;
   void execute(Model model) throws Exception;
   void execute(Model model, boolean test) throws Exception;
}