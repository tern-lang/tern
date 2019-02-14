package org.ternlang.tree.define;

import static org.ternlang.core.scope.extract.ScopePolicy.COMPILE_MEMBER;

import java.util.List;

import org.ternlang.core.ModifierType;
import org.ternlang.core.ModifierValidator;
import org.ternlang.core.Statement;
import org.ternlang.core.constraint.Constraint;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.FunctionBody;
import org.ternlang.core.module.Module;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeBody;
import org.ternlang.core.type.TypePart;
import org.ternlang.core.type.TypeState;
import org.ternlang.tree.ModifierList;
import org.ternlang.tree.annotation.AnnotationList;
import org.ternlang.tree.compile.FunctionScopeCompiler;
import org.ternlang.tree.constraint.FunctionName;
import org.ternlang.tree.function.ParameterList;

public class MemberFunction extends TypePart {
   
   protected final MemberFunctionAssembler assembler;
   protected final FunctionScopeCompiler compiler;
   protected final ModifierValidator validator;
   protected final AnnotationList annotations;
   protected final FunctionName identifier;
   protected final Statement statement;
   
   public MemberFunction(AnnotationList annotations, ModifierList modifiers, FunctionName identifier, ParameterList parameters){
      this(annotations, modifiers, identifier, parameters, null, null);
   }
   
   public MemberFunction(AnnotationList annotations, ModifierList modifiers, FunctionName identifier, ParameterList parameters, Constraint constraint){
      this(annotations, modifiers, identifier, parameters, constraint, null);
   }
   
   public MemberFunction(AnnotationList annotations, ModifierList modifiers, FunctionName identifier, ParameterList parameters, Statement body){
      this(annotations, modifiers, identifier, parameters, null, body);
   }
   
   public MemberFunction(AnnotationList annotations, ModifierList modifiers, FunctionName identifier, ParameterList parameters, Constraint constraint, Statement statement){
      this.assembler = new MemberFunctionAssembler(modifiers, identifier, parameters, constraint, statement);
      this.compiler = new FunctionScopeCompiler(identifier, COMPILE_MEMBER);
      this.validator = new ModifierValidator();
      this.annotations = annotations;
      this.identifier = identifier;
      this.statement = statement;
   } 

   @Override
   public TypeState define(TypeBody parent, Type type, Scope scope) throws Exception {
      return assemble(parent, type, scope, 0);
   }

   protected TypeState assemble(TypeBody parent, Type type, Scope scope, int mask) throws Exception {
      Scope composite = compiler.define(scope, type);
      MemberFunctionBuilder builder = assembler.assemble(composite, mask);
      FunctionBody body = builder.create(parent, composite, type);
      Function function = body.create(composite);
      List<Function> functions = type.getFunctions();
      int modifiers = function.getModifiers();

      if(ModifierType.isStatic(modifiers)) {
         Module module = scope.getModule();
         List<Function> list = module.getFunctions();
         
         list.add(function); // This is VERY STRANGE!!! NEEDED BUT SHOULD NOT BE HERE!!!
      }      
      validator.validate(type, function, modifiers);
      annotations.apply(composite, function);
      functions.add(function);
      body.define(composite); // count stacks
      
      return new FunctionBodyCompiler(identifier, body);
   }
}