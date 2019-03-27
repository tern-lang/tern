package org.ternlang.compile;

public class StaticFieldTest extends ScriptTestCase {
   
   private static final String SOURCE = 
   "class Position {\n"+
   "   let column;\n"+
   "   let row;\n"+
   "\n"+
   "   new(row, column){\n"+
   "      this.column = column;\n"+
   "      this.row = row;\n"+
   "   }\n"+
   "\n"+
   "}\n"+
   "\n"+
   "class Direction {\n"+
   "   const static HORIZONTAL = Direction(0, 1);\n"+
   "   const static VERTICAL = Direction(1, 0);\n"+
   "   const static DIAGONAL_RIGHT_LEFT = Direction(-1, 1);\n"+
   "   const static DIAGONAL_LEFT_RIGHT = Direction(1, 1);\n"+
   "   const static ALL = [HORIZONTAL, VERTICAL, DIAGONAL_RIGHT_LEFT, DIAGONAL_LEFT_RIGHT];\n"+
   "\n"+
   "   let column;\n"+
   "   let row;\n"+
   "\n"+
   "   new(row, column) {\n"+
   "      this.column = column;\n"+
   "      this.row = row;\n"+
   "   }\n"+
   "\n"+
   "   opposite() {\n"+
   "      return Direction(-1 * row, -1 * column);\n"+
   "   }\n"+
   "\n"+
   "   next(M, position: Position) {\n"+
   "      let next = valueOf(M, position.row + row, position.column + column);\n"+
   "\n"+
   "      if(next == 1) {\n"+
   "         return Position(position.row + row, position.column + column);\n"+
   "      }\n"+
   "      return null;\n"+
   "   }\n"+
   "\n"+
   "   valueOf(M, row, column) {\n"+
   "      if(row >= M.length || row < 0) {\n"+
   "         return 0;\n"+
   "      }\n"+
   "      if(column >= M[row].length || column < 0) {\n"+
   "         return 0;\n"+
   "      }\n"+
   "      return M[row][column];\n"+
   "   }\n"+
   "}\n"+
   "Direction.ALL.iterator().forEachRemaining(direction -> println(`row=${direction.row} column=${direction.column}`));\n"+   
   "assert Direction.ALL.contains(Direction.HORIZONTAL);\n";         

   public void testStaticInit() throws Exception {
      assertScriptExecutes(SOURCE);
   }
}
