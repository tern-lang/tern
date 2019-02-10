package tern.core.function;

import java.lang.reflect.Member;
import java.util.List;

import tern.core.constraint.Constraint;
import tern.core.module.Module;
import tern.core.type.Type;

public class FunctionSignature implements Signature {
   
   private final List<Parameter> parameters;
   private final List<Constraint> constraints;
   private final SignatureDescription description;
   private final SignatureMatcher matcher;   
   private final Type definition;
   private final Member source;
   private final Origin origin;
   private final boolean absolute;
   private final boolean variable;

   public FunctionSignature(List<Parameter> parameters, List<Constraint> constraints, Module module, Member source, Origin origin, boolean absolute){
      this(parameters, constraints, module, source, origin, absolute, false);
   }
   
   public FunctionSignature(List<Parameter> parameters, List<Constraint> constraints, Module module, Member source, Origin origin, boolean absolute, boolean variable){
      this.description = new SignatureDescription(this, module);
      this.matcher = new SignatureMatcher(this, module);
      this.definition = new FunctionType(this, module);
      this.constraints = constraints;
      this.parameters = parameters;
      this.absolute = absolute;
      this.variable = variable;
      this.source = source;
      this.origin = origin;
   }
   
   @Override
   public ArgumentConverter getConverter() {
      return matcher.getConverter();
   }
   
   @Override
   public List<Constraint> getGenerics() {
      return constraints;
   }
   
   @Override
   public List<Parameter> getParameters(){
      return parameters;
   }
   
   @Override
   public Type getDefinition() {
      return definition;
   }
   
   @Override
   public Member getSource() {
      return source;
   }
   
   @Override
   public Origin getOrigin() {
      return origin;
   }
   
   @Override
   public boolean isVariable() {
      return variable;
   }
   
   @Override
   public boolean isAbsolute() {
      return absolute; // array parameters are not absolute
   }
   
   @Override
   public String toString() {
      return description.toString();
   }
}