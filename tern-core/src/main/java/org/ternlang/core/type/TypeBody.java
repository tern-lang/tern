package org.ternlang.core.type;

import org.ternlang.core.result.Result;
import org.ternlang.core.scope.Scope;

public interface TypeBody {
   void allocate(Scope scope, Type type) throws Exception; // static stuff   
   Result execute(Scope scope, Type type) throws Exception; // instance stuff
}

