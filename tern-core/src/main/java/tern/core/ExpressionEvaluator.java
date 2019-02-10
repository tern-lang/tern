package tern.core;

import tern.core.scope.Model;
import tern.core.scope.Scope;

public interface ExpressionEvaluator {
   <T> T evaluate(Model model, String source) throws Exception;
   <T> T evaluate(Model model, String source, String module) throws Exception;
   <T> T evaluate(Scope scope, String source) throws Exception;
   <T> T evaluate(Scope scope, String source, String module) throws Exception;
}