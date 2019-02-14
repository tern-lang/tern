package org.ternlang.compile.staticanalysis;

import org.ternlang.compile.ClassPathCompilerBuilder;
import org.ternlang.compile.Compiler;

import junit.framework.TestCase;

public class NavigateMapTest extends TestCase {

   private static final String JSON = "{\n"+
   "   \"glossary\": {\n"+
   "      \"title\": \"example glossary\",\n"+
   "      \"GlossDiv\": {\n"+
   "         \"title\": \"S\",\n"+
   "         \"GlossList\": {\n"+
   "            \"GlossEntry\": {\n"+
   "               \"ID\": \"SGML\",\n"+
   "               \"SortAs\": \"SGML\",\n"+
   "               \"GlossTerm\": \"Standard Generalized Markup Language\",\n"+
   "               \"Acronym\": \"SGML\",\n"+
   "               \"Abbrev\": \"ISO 8879:1986\",\n"+
   "               \"GlossDef\": {\n"+
   "                  \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\n"+
   "                  \"GlossSeeAlso\": [\n"+
   "                     \"GML\",\n"+
   "                     \"XML\"\n"+
   "                  ]\n"+
   "               },\n"+
   "               \"GlossSee\": \"markup\"\n"+
   "            }\n"+
   "         }\n"+
   "      }\n"+
   "   }\n"+
   "}\n";

   private static final String SOURCE_1 = 
   "const json = `" + JSON + "`;\n"+
   "const object =eval(json); // nested eval causes problems\n"+
   "assert object.glossary.title == `example glossary`;\n"+
   "assert object.glossary.GlossDiv.title == `S`;\n"+
   "assert object.glossary.GlossDiv.GlossList.GlossEntry.ID == `SGML`;\n";
   
   public void testNavigateMap() throws Exception {
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      System.err.println(SOURCE_1);
      compiler.compile(SOURCE_1).execute();
   }
}
