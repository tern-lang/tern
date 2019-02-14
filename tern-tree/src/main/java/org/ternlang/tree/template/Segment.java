package org.ternlang.tree.template;

import java.io.Writer;

import org.ternlang.core.scope.Scope;

public interface Segment {
   void process(Scope scope, Writer writer) throws Exception;
}