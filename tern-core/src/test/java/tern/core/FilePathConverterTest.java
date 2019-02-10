package tern.core;

import tern.core.module.FilePathConverter;
import tern.core.module.PathConverter;

import junit.framework.TestCase;

public class FilePathConverterTest extends TestCase {
   
   public void testPath() throws Exception {
      PathConverter parser = new FilePathConverter();
      
      assertEquals("game.tetris", parser.createModule("/game/tetris.snap"));
      assertEquals("game.tetris", parser.createModule("/game/tetris.snap"));
      assertEquals("game.tetris", parser.createModule("game/tetris.snap"));
      assertEquals("game.tetris", parser.createModule("game\\tetris.snap"));
      assertEquals("game.tetris", parser.createModule("\\game\\tetris.snap"));
      assertEquals("game.tetris", parser.createModule("game.tetris"));
      assertEquals("game.tetris", parser.createModule("game.tetris"));
      assertEquals("test", parser.createModule("test.snap"));
      assertEquals("test", parser.createModule("test.snap"));
      assertEquals("test", parser.createModule("/test.snap"));
      assertEquals("test", parser.createModule("/test.snap"));
      assertEquals("test", parser.createModule("\\test.snap"));
      assertEquals("test", parser.createModule("test"));
      assertEquals("some.package", parser.createModule("/some/package/Builder.snap"));
      assertEquals("some.package", parser.createModule("some/package/Builder.snap"));
      assertEquals("some.package", parser.createModule("some.package.Builder"));
      
      assertEquals("/test.snap", parser.createPath("test").getPath());
      assertEquals("/game/tetris.snap", parser.createPath("game.tetris").getPath());
      assertEquals("/test.snap", parser.createPath("/test.snap").getPath());
      assertEquals("/some/package/Builder.snap", parser.createPath("/some/package/Builder.snap").getPath());
      assertEquals("/some/package/Builder.snap", parser.createPath("some.package.Builder").getPath());
      assertEquals("/some/package/Builder/Foo.snap", parser.createPath("some.package.Builder.Foo").getPath());
   }

}
