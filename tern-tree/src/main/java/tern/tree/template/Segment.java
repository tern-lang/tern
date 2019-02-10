package tern.tree.template;

import java.io.Writer;

import tern.core.scope.Scope;

public interface Segment {
   void process(Scope scope, Writer writer) throws Exception;
}