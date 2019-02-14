package org.ternlang.core;

import org.ternlang.core.module.FilePathConverter;
import org.ternlang.core.module.PathConverter;

import junit.framework.TestCase;

public class FilePathConverterTest extends TestCase {
   
   public void testPath() throws Exception {
      PathConverter parser = new FilePathConverter();
      
      assertEquals("game.tetris", parser.createModule("/game/tetris.tern"));
      assertEquals("game.tetris", parser.createModule("/game/tetris.tern"));
      assertEquals("game.tetris", parser.createModule("game/tetris.tern"));
      assertEquals("game.tetris", parser.createModule("game\\tetris.tern"));
      assertEquals("game.tetris", parser.createModule("\\game\\tetris.tern"));
      assertEquals("game.tetris", parser.createModule("game.tetris"));
      assertEquals("game.tetris", parser.createModule("game.tetris"));
      assertEquals("test", parser.createModule("test.tern"));
      assertEquals("test", parser.createModule("test.tern"));
      assertEquals("test", parser.createModule("/test.tern"));
      assertEquals("test", parser.createModule("/test.tern"));
      assertEquals("test", parser.createModule("\\test.tern"));
      assertEquals("test", parser.createModule("test"));
      assertEquals("some.package", parser.createModule("/some/package/Builder.tern"));
      assertEquals("some.package", parser.createModule("some/package/Builder.tern"));
      assertEquals("some.package", parser.createModule("some.package.Builder"));
      
      assertEquals("/test.tern", parser.createPath("test").getPath());
      assertEquals("/game/tetris.tern", parser.createPath("game.tetris").getPath());
      assertEquals("/test.tern", parser.createPath("/test.tern").getPath());
      assertEquals("/some/package/Builder.tern", parser.createPath("/some/package/Builder.tern").getPath());
      assertEquals("/some/package/Builder.tern", parser.createPath("some.package.Builder").getPath());
      assertEquals("/some/package/Builder/Foo.tern", parser.createPath("some.package.Builder.Foo").getPath());
   }

}
