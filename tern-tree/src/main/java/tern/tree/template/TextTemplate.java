package tern.tree.template;

import static tern.core.constraint.Constraint.STRING;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import tern.core.Compilation;
import tern.core.Context;
import tern.core.Evaluation;
import tern.core.ExpressionEvaluator;
import tern.core.constraint.Constraint;
import tern.core.convert.proxy.ProxyWrapper;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.scope.Scope;
import tern.core.scope.extract.EvaluationExtractor;
import tern.core.variable.Value;
import tern.parse.StringToken;

public class TextTemplate implements Compilation {
   
   private final StringToken template;
   
   public TextTemplate(StringToken template) {
      this.template = template;
   }

   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      ExpressionEvaluator evaluator = context.getEvaluator();
      String text = template.getValue();
      char[] source = text.toCharArray();
      
      return new CompileResult(evaluator, wrapper, source);
   }
   
   private static class CompileResult extends Evaluation {

      private EvaluationExtractor extractor;
      private SegmentIterator iterator;
      private List<Segment> segments;

      public CompileResult(ExpressionEvaluator evaluator, ProxyWrapper wrapper, char[] source) {
         this.iterator = new SegmentIterator(evaluator, wrapper, source);
         this.extractor = new EvaluationExtractor();
         this.segments = new ArrayList<Segment>();
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         List<Segment> list = new ArrayList<Segment>();
         
         while(iterator.hasNext()) {
            Segment token = iterator.next();
            
            if(token != null) {
               list.add(token);  
            }
         }
         segments = list;
         return STRING;
      }
   
      @Override
      public Value evaluate(Scope scope, Value left) throws Exception {
         StringWriter writer = new StringWriter();
         
         if(!segments.isEmpty()) {
            Scope capture = extractor.extract(scope);
            
            for(Segment segment : segments) {
               segment.process(capture, writer);
            }
         }
         String result = writer.toString();
         return Value.getTransient(result);
      }
   }

}