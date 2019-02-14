package org.ternlang.tree.define;

import static org.ternlang.core.Reserved.TYPE_CLASS;

import java.util.List;

import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Signature;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeBody;
import org.ternlang.core.type.TypePart;
import org.ternlang.core.type.TypeState;
import org.ternlang.tree.function.ParameterList;

public class ConstructorAssembler {

   private final ConstructorSelector delegate; // this() or super()
   private final ParameterList parameters;
   private final Statement statement;

   public ConstructorAssembler(ParameterList parameters, TypePart part, Statement statement){  
      this.delegate = new ConstructorSelector(part);
      this.parameters = parameters;
      this.statement = statement;
   } 
   
   public ConstructorBuilder assemble(TypeBody body, Type type, Scope scope) throws Exception {
      TypeState internal = delegate.define(body, type, scope);
      List<Constraint> generics = type.getGenerics();
      Signature signature = parameters.create(scope, generics, TYPE_CLASS);
      
      return new ConstructorBuilder(internal, signature, statement);
   }
}