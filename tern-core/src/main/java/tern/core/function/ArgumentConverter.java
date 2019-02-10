package tern.core.function;

import tern.core.type.Type;
import tern.core.convert.Score;

public interface ArgumentConverter { 
   Score score(Type... list) throws Exception;
   Score score(Object... list) throws Exception;
   Object[] assign(Object... list) throws Exception;
   Object[] convert(Object... list) throws Exception;   
}