package org.ternlang.compile;

public class AwaitCascadeTest extends ScriptTestCase {

   private static final String SOURCE_1 =
   "async func one(){\n"+
   "   println(`one(): ` +Thread.currentThread().getName());\n"+
   "   let a1 = await two();\n"+
   "   let b1 = await two();\n"+
   "   return `one: ${a1}---${b1}`;\n"+
   "}\n"+
   "async func two(){\n"+
   "   println(`two(): ` +Thread.currentThread().getName());\n"+
   "   let a2 = await three();\n"+
   "   return `two: ${a2}`;\n"+
   "}\n"+
   "async func three(){\n"+
   "   println(`three(): ` +Thread.currentThread().getName());\n"+
   "   let a3 = await 'foo'.toUpperCase();\n"+
   "   return `three: ${a3}`;\n"+
   "}\n"+
   "one().join().success(this::println);\n"+
   "one().join().success(result -> {\n"+
   "   assert result == 'one: two: three: FOO---two: three: FOO';\n"+
   "});\n";

   private static final String SOURCE_2 =
   "async func one(n){\n"+
   "   if(n > 0) {\n"+
   "      println(`two(${n}): ` +Thread.currentThread().getName());\n"+
   "      await two(n-1);\n"+
   "   }\n"+
   "   return 'one';\n"+
   "}\n"+
   "\n"+
   "async func two(n){\n"+
   "   if(n > 0) {\n"+
   "      println(`one(${n}): ` +Thread.currentThread().getName());\n"+
   "      await one(n-1);\n"+
   "   }\n"+
   "   return 'two';\n"+
   "}\n"+
   "one(100).join().success(this::println);\n";

   private static final String SOURCE_3 =
   "async func one(n){\n"+
   "   if(n > 0) {\n"+
   "      println(`two(${n}): ` +Thread.currentThread().getName());\n"+
   "      await two(n-1);\n"+
   "   }\n"+
   "   return 'one';\n"+
   "}\n"+
   "\n"+
   "async func two(n){\n"+
   "   if(n > 0) {\n"+
   "      println(`one(${n}): ` +Thread.currentThread().getName());\n"+
   "      await one(n-1);\n"+
   "   }\n"+
   "   throw 'x';\n"+
   "}\n"+
   "let list = [];\n"+
   "one(1000).join().failure(e -> list.add(e));\n"+
   "println(list);\n"+
   "println(list.length);\n"+
   "println(list[0]);\n"+
   "println(list[0].class);\n"+
   "assert list.length == 1;\n"+
   "assert list[0] == 'x';\n";

   private static final String SOURCE_4 =
   "async func one(n){\n"+
   "   if(n > 0) {\n"+
   "      println(`two(${n}): ` +Thread.currentThread().getName());\n"+
   "      await two(n-1);\n"+
   "   }\n"+
   "   return 'one';\n"+
   "}\n"+
   "\n"+
   "async func two(n){\n"+
   "   if(n > 0) {\n"+
   "      println(`one(${n}): ` +Thread.currentThread().getName());\n"+
   "      await one(n-1);\n"+
   "   }\n"+
   "   throw 'x';\n"+
   "}\n"+
   "let failure = false;\n"+
   "try {\n"+
   "   one(100).value;\n"+
   "} catch(e){\n"+
   "   e.printStackTrace();\n"+
   "   failure = true;\n"+
   "}\n"+
   "assert failure;\n";

   private static final String SOURCE_5 =
   "async func one(n){\n"+
   "   try {\n"+
   "      if(n > 0) {\n"+
   "         println(`two(${n}): ` +Thread.currentThread().getName());\n"+
   "         await two(n-1);\n"+
   "      }\n"+
   "      return 'one';\n"+
   "   } finally {\n"+
   "      println('finally ' + n);\n"+
   "   }\n"+
   "}\n"+
   "\n"+
   "async func two(n){\n"+
   "   if(n > 0) {\n"+
   "      println(`one(${n}): ` +Thread.currentThread().getName());\n"+
   "      await one(n-1);\n"+
   "   }\n"+
   "   throw 'two';\n"+
   "}\n"+
   "one(100).join().success(this::println);\n";

   private static final String SOURCE_6 =
   "let x = 0;\n"+
   "async func one(){\n"+
   "   x += await two();\n"+
   "   x += await two();\n"+
   "   x += await three();\n"+
   "}\n"+
   "async func two(){\n"+
   "   return await Math.max(1,2);\n"+
   "}\n"+
   "async func three(){\n"+
   "   throw 'x';\n"+
   "}\n"+
   "let failure = false;\n"+
   "try {\n"+
   "   one().value();\n"+
   "}catch(e){\n"+
   "   e.printStackTrace();\n"+
   "   failure = true;\n"+
   "}\n"+
   "println(failure);\n"+
   "println(x);\n"+
   "assert failure;\n"+
   "assert x == 4;\n";

   private static final String SOURCE_7 =
   "let x = 0;\n"+
   "let finals = 0;\n"+
   "async func one(){\n"+
   "   try {\n"+
   "      x += await two();\n"+
   "      x += await two();\n"+
   "      x *= await three();\n"+
   "   }finally{\n"+
   "      finals++;\n"+
   "   }\n"+
   "}\n"+
   "async func two(){\n"+
   "   return await Math.max(1,2);\n"+
   "}\n"+
   "async func three(){\n"+
   "   return await Math.max(10, 1);\n"+
   "}\n"+
   "let failure = false;\n"+
   "try {\n"+
   "   one().value();\n"+
   "}catch(e){\n"+
   "   e.printStackTrace();\n"+
   "   failure = true;\n"+
   "}\n"+
   "println(finals);\n"+
   "println(x);\n"+
   "assert !failure;\n"+
   "assert finals == 1;\n"+
   "assert x == 40;\n";

   private static final String SOURCE_8 =
   "let x = 0;\n"+
   "let finals = 0;\n"+
   "async func one(){\n"+
   "   for(i in 0 to 2) {\n"+
   "      try {\n"+
   "         x += await two();\n"+
   "         x += await two();\n"+
   "         x *= await three();\n"+
   "      }finally{\n"+
   "         finals++;\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "async func two(){\n"+
   "   return await Math.max(1,2);\n"+
   "}\n"+
   "async func three(){\n"+
   "   return await Math.max(10, 1);\n"+
   "}\n"+
   "let failure = false;\n"+
   "try {\n"+
   "   one().value();\n"+
   "}catch(e){\n"+
   "   e.printStackTrace();\n"+
   "   failure = true;\n"+
   "}\n"+
   "println(finals);\n"+
   "println(x);\n"+
   "assert !failure;\n"+
   "assert finals == 3;\n"+
   "assert x == 4440;\n";

   private static final String SOURCE_9 =
   "let x = 0;\n"+
   "let finals = 0;\n"+
   "async func one(){\n"+
   "   for(i in 0 to 2) {\n"+
   "      try {\n"+
   "         x += await two();\n"+
   "         x += await two();\n"+
   "         x *= await three();\n"+
   "      }finally{\n"+
   "         finals++;\n"+
   "      }\n"+
   "   }\n"+
   "}\n"+
   "async func two(){\n"+
   "   return await Math.max(1,2);\n"+
   "}\n"+
   "async func three(){\n"+
   "   throw 'x';\n"+
   "}\n"+
   "let failure = false;\n"+
   "try {\n"+
   "   one().value();\n"+
   "}catch(e){\n"+
   "   e.printStackTrace();\n"+
   "   failure = true;\n"+
   "}\n"+
   "println(finals);\n"+
   "println(failure);\n"+
   "assert failure;\n"+
   "assert finals == 1;\n"+
   "assert x == 4;\n";

   private static final String SOURCE_10 =
   "let x = 0;\n"+
   "let exceptions = 0;\n"+
   "async func one(){\n"+
   "   try {\n"+
   "      x += await two();\n"+
   "      println(`resume`);\n"+
   "      x *= await three();\n"+ // resuming an exception can execute the catch block
   "   }catch(e){\n"+
   "      println(`exceptions=${exceptions} ${e.message}`);\n"+
   "      exceptions++;\n"+
   "   }\n"+
   "}\n"+
   "async func two(){\n"+
   "   return await Math.max(1,2);\n"+
   "}\n"+
   "async func three(){\n"+
   "   println('three()');\n"+
   "   throw new Exception('error');\n"+
   "}\n"+
   "let failure = false;\n"+
   "try {\n"+
   "   one().value();\n"+
   "}catch(e){\n"+
   "   e.printStackTrace();\n"+
   "   failure = true;\n"+
   "}\n"+
   "println(exceptions);\n"+
   "println(x);\n"+
   "assert !failure;\n"+
   "assert exceptions == 1;\n"+
   "assert x == 2;\n";

   private static final String SOURCE_11 =
   "let x = 0;\n"+
    "let exceptions = 0;\n"+
    "async func one(){\n"+
    "   for(i in 0 to 2) {\n"+
    "      try {\n"+
    "         x += await two();\n"+
    "         println(`resume where i=${i}`);\n"+
    "         x *= await three();\n"+
    "         println('this line should never appear');\n"+
    "         x += await two();\n"+
    "      }catch(e){\n"+
    "         println(`${exceptions} i=${i}`);\n"+
    "         exceptions++;\n"+
    "      }\n"+
    "   }\n"+
    "}\n"+
    "async func two(){\n"+
    "   return await Math.max(1,2);\n"+
    "}\n"+
    "async func three(){\n"+
    "   throw 'x';\n"+
    "}\n"+
    "let failure = false;\n"+
    "try {\n"+
    "   one().value();\n"+
    "}catch(e){\n"+
    "   e.printStackTrace();\n"+
    "   failure = true;\n"+
    "}\n"+
    "println(exceptions);\n"+
    "println(x);\n"+
    "assert !failure;\n"+
    "assert exceptions == 3;\n"+
    "assert x == 6;\n";

   private static final String SOURCE_12 =
   "let exceptions = 0;\n"+
   "async func one(n){\n"+
   "   println(n);\n"+
   "   if(n <= 0) {\n"+
   "      throw new Exception(`error ${n}`);\n"+
   "   }\n"+
   "   println(`one(${n})`);\n"+
   "   let x = 0;\n"+
   "\n"+
   "   try {\n"+
   "      x += await two();\n"+
   "      x += await two();\n"+
   "      x *= await one(n-1);\n"+
   "   }catch(e){\n"+
   "      e.printStackTrace();\n"+
   "      exceptions++;\n"+
   "      println(`exceptions=${exceptions} ${e.message}`);\n"+
   "   }\n"+
   "   return 1;\n"+
   "}\n"+
   "async func two(){\n"+
   "   return await Math.max(1,2);\n"+
   "}\n"+
   "let failure = false;\n"+
   "try {\n"+
   "   one(2).value();\n"+
   "}catch(e){\n"+
   "   e.printStackTrace();\n"+
   "   failure = true;\n"+
   "}\n"+
   "println(`count of exceptions ${exceptions}`);\n"+
   "assert !failure;\n"+
   "assert exceptions == 1;\n";

   private static final String SOURCE_13 =
   "let exceptions = 0;\n"+
   "async func one(n){\n"+
   "   println(n);\n"+
   "   if(n <= 0) {\n"+
   "      throw new Exception(`error ${n}`);\n"+
   "   }\n"+
   "   println(`one(${n})`);\n"+
   "   let x = 0;\n"+
   "\n"+
   "   try {\n"+
   "      x += await two();\n"+
   "      x += await two();\n"+
   "      x *= await one(n-1);\n"+
   "   }catch(e){\n"+
   "      e.printStackTrace();\n"+
   "      exceptions++;\n"+
   "      println(`exceptions=${exceptions} ${e.message} and throwing again`);\n"+
   "      throw e;\n"+
   "   }\n"+
   "}\n"+
   "async func two(){\n"+
   "   return await Math.max(1,2);\n"+
   "}\n"+
   "let failure = false;\n"+
   "try {\n"+
   "   one(2).value();\n"+
   "}catch(e){\n"+
   "   e.printStackTrace();\n"+
   "   failure = true;\n"+
   "}\n"+
   "println(`count of exceptions ${exceptions}`);\n"+
   "println(failure);\n"+
   "assert failure;\n"+
   "assert exceptions == 2;\n";

   private static final String SOURCE_14 =
   "let finals = 0;\n"+
   "async func one(n){\n"+
   "   if(n == 0) {\n"+
   "      throw 'x';\n"+
   "   }\n"+
   "   let x = 0;\n"+
   "\n"+
   "   try {\n"+
   "      x += await two();\n"+
   "      x += await two();\n"+
   "      x *= await one(n-1);\n"+
   "   }finally{\n"+
   "      finals++;\n"+
   "   }\n"+
   "}\n"+
   "async func two(){\n"+
   "   return await Math.max(1,2);\n"+
   "}\n"+
   "let failure = false;\n"+
   "try {\n"+
   "   one(2).value();\n"+
   "}catch(e){\n"+
   "   e.printStackTrace();\n"+
   "   failure = true;\n"+
   "}\n"+
   "println(`finals=${finals}`);\n"+
   "assert failure;\n"+
   "assert finals == 2;\n";

   private static final String SOURCE_15 =
   "async func foo(n){\n"+
   "   if(n <= 0){\n"+
   "      throw new Exception('err');\n"+
   "   }\n"+
   "   println(n);\n"+
   "  // let fun = async  -> foo(n-1);\n"+
   "  // return await fun();\n"+
   "  return await foo(n-1);\n"+
   "}\n"+
   "let failure = false;\n"+
   "try{\n"+
   "   foo(1000).value();\n"+
   "}catch(e){\n"+
   "   e.printStackTrace();\n"+
   "   failure = true;\n"+
   "}\n"+
   "println(failure);\n"+
   "assert failure;\n";

   private static final String SOURCE_16 =
   "async func foo(n){\n"+
   "   if(n <= 0){\n"+
   "      return 'finished';\n"+
   "   }\n"+
   "   println(n);\n"+
   "  // let fun = async  -> foo(n-1);\n"+
   "  // return await fun();\n"+
   "  return await foo(n-1);\n"+
   "}\n"+
   "let failure = false;\n"+
   "let x = null;\n"+
   "try{\n"+
   "   x = foo(1000).value();\n"+
   "}catch(e){\n"+
   "   e.printStackTrace();\n"+
   "   failure = true;\n"+
   "}\n"+
   "println('x='+x);\n"+
   "assert !failure;\n"+
   "assert x == 'finished';\n";

   public void testAwaitCascade() throws Exception {
      assertScriptExecutes(SOURCE_1);
      assertScriptExecutes(SOURCE_2);
      assertScriptExecutes(SOURCE_3);
      assertScriptExecutes(SOURCE_4);
      assertScriptExecutes(SOURCE_5);
      assertScriptExecutes(SOURCE_6);
      assertScriptExecutes(SOURCE_7);
      assertScriptExecutes(SOURCE_8);
      assertScriptExecutes(SOURCE_9);
      assertScriptExecutes(SOURCE_10);
      assertScriptExecutes(SOURCE_11);
      assertScriptExecutes(SOURCE_12);
      assertScriptExecutes(SOURCE_13);
      assertScriptExecutes(SOURCE_14);
      assertScriptExecutes(SOURCE_15);
      assertScriptExecutes(SOURCE_16);
   }

   @Override
   public boolean isThreadPool() {
      return true;
   }
}
