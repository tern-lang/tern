package tern.core.type;

import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.result.Result;

public interface TypeBody {
   void allocate(Scope scope, Type type) throws Exception; // static stuff   
   Result execute(Scope scope, Type type) throws Exception; // instance stuff
}

