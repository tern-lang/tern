package org.ternlang.core.variable.bind;

import static org.ternlang.core.scope.index.AddressType.INSTANCE;

import java.util.Map;

import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.scope.index.Address;
import org.ternlang.core.variable.MapValue;
import org.ternlang.core.variable.Value;

public class MapResult implements VariableResult<Map> {
   
   private final Constraint constraint;
   private final ProxyWrapper wrapper;
   private final String name;
   
   public MapResult(ProxyWrapper wrapper, Constraint constraint, String name){      
      this.constraint = constraint;
      this.wrapper = wrapper;
      this.name = name;
   }  
   
   @Override
   public Address getAddress(int offset) {
      return INSTANCE.getAddress(name, offset);
   }

   @Override
   public Constraint getConstraint(Constraint left) {
      return constraint;
   }

   @Override
   public Value getValue(Map left) {      
      return new MapValue(wrapper, left, name);
   }

}
