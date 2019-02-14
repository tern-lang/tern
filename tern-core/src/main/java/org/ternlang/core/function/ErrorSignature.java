package org.ternlang.core.function;

import static org.ternlang.core.function.Origin.ERROR;

import java.lang.reflect.Member;
import java.util.Collections;
import java.util.List;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.NoArgumentConverter;
import org.ternlang.core.type.Type;

public class ErrorSignature implements Signature {

   private final ArgumentConverter converter;
   
   public ErrorSignature() {
      this.converter = new NoArgumentConverter();
   }
   
   @Override
   public ArgumentConverter getConverter() {
      return converter;
   }
   
   @Override
   public List<Constraint> getGenerics() {
      return Collections.emptyList();
   }

   @Override
   public List<Parameter> getParameters() {
      return Collections.emptyList();
   }

   @Override
   public Type getDefinition() {
      return null;
   }

   @Override
   public Member getSource() {
      return null;
   }
   
   @Override
   public Origin getOrigin() {
      return ERROR;
   }

   @Override
   public boolean isVariable() {
      return false;
   }

   @Override
   public boolean isAbsolute() {
      return true;
   }
   
   @Override
   public String toString() {
      return "()";
   }
}