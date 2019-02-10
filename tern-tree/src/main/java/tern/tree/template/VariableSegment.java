package tern.tree.template;

import java.io.Writer;

import tern.core.convert.proxy.ProxyWrapper;
import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.core.variable.bind.VariableFinder;
import tern.core.variable.index.LocalPointer;
import tern.core.variable.index.VariablePointer;

public class VariableSegment implements Segment {
   
   private final VariablePointer pointer;
   private final VariableFinder finder;
   private final ProxyWrapper wrapper;
   private final String variable;
   
   public VariableSegment(ProxyWrapper wrapper, char[] source, int off, int length) {
      this.variable = new String(source, off + 2, length - 3);
      this.finder = new VariableFinder(wrapper);
      this.pointer = new LocalPointer(finder, variable);
      this.wrapper = wrapper;         
   }
   
   @Override
   public void process(Scope scope, Writer writer) throws Exception {
      Value value = pointer.getValue(scope, null);
      
      if(value == null) {
         throw new InternalStateException("Variable '" + variable + "' not found");
      }
      Object token = value.getValue();
      Object object = wrapper.toProxy(token);
      String text = String.valueOf(object);
      
      writer.append(text);            
   }   
   
   @Override
   public String toString() {
      return variable;
   }
}