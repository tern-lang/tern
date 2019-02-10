package tern.compile.define;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import tern.compile.ClassPathCompilerBuilder;
import tern.compile.Compiler;
import tern.core.scope.MapModel;
import tern.core.scope.Model;
import tern.core.variable.Value;

public class CreateClassTest extends TestCase{
   private static final String SOURCE_1=
   "class Test{\n"+
   "  var x;\n"+
   "  var y;\n"+
   "\n"+
   "  new(){\n"+
   "     this.x=x;\n"+
   "  }\n"+
   "  fo(){\n"+
   "     out.println('fun');\n"+
   "  }\n"+
   "  foo(){\n"+
   "     return x;\n"+
   "  }\n"+
   "  x(){\n"+
   "     x++;\n"+
   "  }\n"+
   "}\n";
   private static final String SOURCE_2=
   "class Point{\n"+
   "  var x;\n"+
   "  var y;\n"+
   "\n"+
   "  new(){\n"+
   "     this.x=1;\n"+
   "  }\n"+
   "  show(){\n"+
   "     printf('x=%s y=%s%n',x,y);\n"+
   "  }\n"+
   "}\n"+
   "var p1 = new Point();\n"+
   "var p2 = p1;\n"+
   "\n"+
   "p2.show();";
   private static final String SOURCE_3=
   "class A{\n"+
   "   var text;\n"+
   "   new(a:String,b:String){\n"+
   "      out.println('new A');\n"+
   "      this.text = a+' '+b;\n"+
   "   }\n"+
   "   new(a:String){\n"+
   "      this.text = a;\n"+
   "   }\n"+
   "   dump(){\n"+
   "      out.println('In A, text='+text);\n"+
   "   }\n"+
   "}\n"+
   "class B extends A{\n"+
   "   var a;\n"+
   "   var b;\n"+
   "   new(text:String):super(text,'blah'){\n"+
   "      out.println('new B');\n"+
   "      this.a='a->\"'+text+'\"';\n"+
   "      this.b='b->\"'+text+'\"';\n"+
   "   }\n"+
   "   dump(){\n"+
   "      out.println('In B, a='+a+' b='+b);\n"+
   "   }\n"+
   "}\n"+
   "var b = new B('create B');\n"+
   "b.dump();\n"+
   "\n";
   private static final String SOURCE_4=
   "class Special extends Base{\n"+
   "   var a;\n"+
   "   var b;\n"+
   "   new(text:String):super(text,'blah'){\n"+
   "      out.println('new Special');\n"+
   "      this.a='a->\"'+text+'\"';\n"+
   "      this.b='b->\"'+text+'\"';\n"+
   "   }\n"+
   "   dump(){\n"+
   "      out.println('In Special, a='+a+' b='+b);\n"+
   "   }\n"+
   "}\n"+
   "class Base{\n"+
   "   var text;\n"+
   "   new(a:String,b:String){\n"+
   "      out.println('new Base');\n"+
   "      this.text = a+' '+b;\n"+
   "   }\n"+
   "   new(a:String){\n"+
   "      this.text = a;\n"+
   "   }\n"+
   "   dump(){\n"+
   "      out.println('In Base, text='+text);\n"+
   "   }\n"+
   "}\n"+   
   "var b = new Special('create Special');\n"+
   "b.dump();\n"+
   "\n";   
   private static final String ERROR_1=
   "class X{\n"+  
   "   new(a:String,b:String){\n"+
   "      out.println('new X');\n"+  
   "   }\n"+
   "   dump(){\n"+
   "      out.println('X.dump()');\n"+
   "   }\n"+   
   "}\n"+
   "class Y extends X{\n"+
   "   new(a:String){\n"+
   "      out.println('new Y');\n"+
   "   }\n"+
   "   dump(){\n"+
   "      out.println('Y.dump()');\n"+
   "   }\n"+     
   "}\n"+
   "var y = new Y('create Y');\n"+
   "y.dump();\n"+
   "\n";
   public static void main(String[] l)throws Exception{
      new CreateClassTest().testClasses();
   }
   public void testClasses() throws Exception {
      boolean failure = false;
      //execute(SOURCE_1);
      execute(SOURCE_2);
//      execute(SOURCE_3);
//      execute(SOURCE_4);
//
//      try{
//         execute(ERROR_1);
//      }catch(Exception e){
//         e.printStackTrace();
//         failure=true;
//      }
//      assertTrue(failure);
   }
   
   private void execute(String source) throws Exception {
      Map map = new HashMap<String,Value>();
      map.put("out",System.out);
      Model s = new MapModel(map);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();;
      boolean failure=false;
      System.err.println(source);
      compiler.compile(source).execute(s);
      System.err.println();
   }
}
