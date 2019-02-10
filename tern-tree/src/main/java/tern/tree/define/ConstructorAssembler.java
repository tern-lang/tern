package tern.tree.define;

import static tern.core.Reserved.TYPE_CLASS;

import java.util.List;

import tern.core.Statement;
import tern.core.constraint.Constraint;
import tern.core.function.Signature;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeBody;
import tern.core.type.TypePart;
import tern.core.type.TypeState;
import tern.tree.function.ParameterList;

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