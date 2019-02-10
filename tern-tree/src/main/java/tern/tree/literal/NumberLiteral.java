package tern.tree.literal;

import static tern.core.variable.Constant.BYTE;
import static tern.core.variable.Constant.DOUBLE;
import static tern.core.variable.Constant.FLOAT;
import static tern.core.variable.Constant.INTEGER;
import static tern.core.variable.Constant.LONG;
import static tern.core.variable.Constant.NUMBER;
import static tern.core.variable.Constant.SHORT;

import tern.core.error.InternalStateException;
import tern.core.scope.Scope;
import tern.core.variable.Value;
import tern.parse.NumberToken;
import tern.parse.StringToken;
import tern.tree.operation.SignOperator;

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