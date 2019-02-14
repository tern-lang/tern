package org.ternlang.core.function;

import org.ternlang.core.type.Type;
import org.ternlang.core.convert.Score;

public interface ArgumentConverter { 
   Score score(Type... list) throws Exception;
   Score score(Object... list) throws Exception;
   Object[] assign(Object... list) throws Exception;
   Object[] convert(Object... list) throws Exception;   
}