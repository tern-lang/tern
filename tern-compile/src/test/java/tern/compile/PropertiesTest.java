package tern.compile;

public class PropertiesTest extends ScriptTestCase {
   
   private static final String SOURCE =
   "let props: Properties = {'a': '2'};\n"+
   "let val: String = props.getProperty('a');\n"+
   "println(val);\n"+
   "assert val == '2';\n"+      
   "println(props);\n";
   
   public void testProperties() throws Exception {
      assertScriptExecutes(SOURCE);
   }
}
