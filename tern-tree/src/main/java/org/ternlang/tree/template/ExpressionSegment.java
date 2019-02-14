package org.ternlang.tree.template;

import static org.ternlang.core.Reserved.TYPE_NULL;

import java.io.Writer;

import org.ternlang.core.ExpressionEvaluator;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;

public class ExpressionSegment implements Segment {
   
   private final ExpressionEvaluator evaluator;
   private final ProxyWrapper wrapper;
   private final String expression;
   
   public ExpressionSegment(ExpressionEvaluator evaluator, ProxyWrapper wrapper, char[] source, int off, int length) {
      this.expression = new String(source, off + 2, length - 3);
      this.evaluator = evaluator;
      this.wrapper = wrapper;       
   }
   
   @Override
   public void process(Scope scope, Writer writer) throws Exception {
      Module module = scope.getModule();
      String name = module.getName();
      Object value = evaluator.evaluate(scope, expression, name);
      
      if(value == null) {
         writer.write(TYPE_NULL);
      } else {
         Object object = wrapper.toProxy(value);
         String text = String.valueOf(object);
         
         writer.append(text);            
      }
   }   
   
   @Override
   public String toString() {
      return expression;
   }
}