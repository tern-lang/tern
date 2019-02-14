package org.ternlang.tree.literal;

import static org.ternlang.core.variable.Constant.BYTE;
import static org.ternlang.core.variable.Constant.DOUBLE;
import static org.ternlang.core.variable.Constant.FLOAT;
import static org.ternlang.core.variable.Constant.INTEGER;
import static org.ternlang.core.variable.Constant.LONG;
import static org.ternlang.core.variable.Constant.NUMBER;
import static org.ternlang.core.variable.Constant.SHORT;

import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.variable.Value;
import org.ternlang.parse.NumberToken;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.operation.SignOperator;

public class NumberLiteral extends Literal {
   
   private final SignOperator operator;
   private final NumberToken token;

   public NumberLiteral(NumberToken token) {
      this(null, token);
   }
   
   public NumberLiteral(StringToken sign, NumberToken token) {
      this.operator = SignOperator.resolveOperator(sign);
      this.token = token;
   }

   @Override
   protected LiteralValue create(Scope scope) throws Exception {
      Number number = token.getValue();
      
      if(number == null) {
         throw new InternalStateException("Number value was null");
      }
      Value value = operator.operate(number);
      Number result = value.getValue();
      
      return create(scope, result);
   }

   private LiteralValue create(Scope scope, Number result) throws Exception {
      Class type = result.getClass();
      
      if(type == Integer.class) {
         return new LiteralValue(result, INTEGER);
      }
      if(type == Double.class) {
         return new LiteralValue(result, DOUBLE);
      }
      if(type == Float.class) {
         return new LiteralValue(result, FLOAT);
      }
      if(type == Byte.class) {
         return new LiteralValue(result, BYTE);
      }
      if(type == Short.class) {
         return new LiteralValue(result, SHORT);
      }
      if(type == Long.class) {
         return new LiteralValue(result, LONG);
      }
      return new LiteralValue(result, NUMBER);
   }
}