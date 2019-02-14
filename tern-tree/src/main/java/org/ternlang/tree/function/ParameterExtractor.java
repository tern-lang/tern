package org.ternlang.tree.function;

import java.util.List;

import org.ternlang.core.ModifierType;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.convert.TypeInspector;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.function.Parameter;
import org.ternlang.core.function.Signature;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.ScopeState;
import org.ternlang.core.scope.index.Address;
import org.ternlang.core.scope.index.AddressCache;
import org.ternlang.core.scope.index.Local;
import org.ternlang.core.scope.index.ScopeIndex;
import org.ternlang.core.scope.index.ScopeTable;
import org.ternlang.core.type.Type;

public class ParameterExtractor {

   private final TypeInspector inspector;
   private final Signature signature;
   private final int modifiers;
   
   public ParameterExtractor(Signature signature) {
      this(signature, 0);
   }
   
   public ParameterExtractor(Signature signature, int modifiers) {
      this.inspector = new TypeInspector();
      this.signature = signature;
      this.modifiers = modifiers;
   }
   
   public void define(Scope scope) throws Exception {
      List<Parameter> parameters = signature.getParameters();
      int required = parameters.size();

      if(required > 0) {     
         ScopeIndex index = scope.getIndex();
         
         for(int i = 0; i < required; i++) {
            Parameter parameter = parameters.get(i);
            Address address = parameter.getAddress();
            String name = parameter.getName();
            
            if(!index.contains(name)) {
               Address created = index.index(name);
               int actual = created.getOffset();
               int require = address.getOffset();
               
               if(actual != require) {
                  throw new InternalStateException("Parameter '" + name + "' has an invalid address");
               }
            }
         }
      }
   }

   public Scope extract(Scope scope, Object[] arguments) throws Exception {
      List<Parameter> parameters = signature.getParameters();
      List<Constraint> generics = signature.getGenerics();
      Scope inner = scope.getStack();    
      int required = parameters.size();
      int optional = generics.size();

      if(optional + required > 0) {  
         ScopeTable table = inner.getTable();
         ScopeState state = inner.getState();
         
         for(int i = 0; i < optional; i++) {
            Constraint constraint = generics.get(i);
            Address address = AddressCache.getAddress(i);
            
            table.addConstraint(address, constraint);
         }
         for(int i = 0; i < required; i++) {
            Parameter parameter = parameters.get(i);
            Address address = parameter.getAddress();
            String name = parameter.getName();
            Object argument = arguments[i];
            Local local = create(inner, argument, i);
            
            if(ModifierType.isClosure(modifiers)) {
               state.addValue(name, local); 
            }
            table.addValue(address, local);
         }   
      }
      return inner;
   }

   private Local create(Scope scope, Object value, int index) throws Exception {
      List<Parameter> parameters = signature.getParameters();
      Parameter parameter = parameters.get(index);
      int length = parameters.size();
      
      if(index >= length -1) {
         if(signature.isVariable()) {
            Constraint constraint = parameter.getConstraint();
            Type type = constraint.getType(scope);
            String name = parameter.getName();
            Object[] list = (Object[])value;
            
            for(int i = 0; i < list.length; i++) {
               Object entry = list[i];
               
               if(!inspector.isCompatible(type,  entry)) {
                  throw new InternalStateException("Parameter '" + name + "...' does not match constraint '" + type + "'");
               }
            }
            return create(scope, value, parameter);  
         }
      }
      return create(scope, value, parameter);   
   }

   private Local create(Scope scope, Object value, Parameter parameter) throws Exception {
      Constraint constraint = parameter.getConstraint();
      Type type = constraint.getType(scope);
      String name = parameter.getName();
      
      if(parameter.isConstant()) {
         return Local.getConstant(value, name, constraint);
      }
      return Local.getReference(value, name, constraint);       
   }
}