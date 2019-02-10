package tern.core.variable.bind;

import static tern.core.scope.index.AddressType.INSTANCE;

import java.util.Map;

import tern.core.constraint.Constraint;
import tern.core.convert.proxy.ProxyWrapper;
import tern.core.scope.index.Address;
import tern.core.variable.MapValue;
import tern.core.variable.Value;

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
