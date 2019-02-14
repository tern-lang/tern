package org.ternlang.core.type;

import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.result.Result;

public interface TypeBody {
   void allocate(Scope scope, Type type) throws Exception; // static stuff   
   Result execute(Scope scope, Type type) throws Exception; // instance stuff
}

