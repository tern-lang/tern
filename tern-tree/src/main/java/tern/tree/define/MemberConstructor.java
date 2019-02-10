package tern.tree.define;

import static tern.core.scope.extract.ScopePolicy.COMPILE_MEMBER;

import java.util.List;

import tern.core.Statement;
import tern.core.function.Function;
import tern.core.function.FunctionBody;
import tern.core.scope.Scope;
import tern.core.type.Type;
import tern.core.type.TypeBody;
import tern.core.type.TypePart;
import tern.core.type.TypeState;
import tern.tree.ModifierList;
import tern.tree.annotation.AnnotationList;
import tern.tree.compile.FunctionScopeCompiler;
import tern.tree.constraint.ConstructorName;
import tern.tree.function.ParameterList;

public abstract class MemberConstructor extends TypePart {
   
   private final FunctionScopeCompiler compiler;
   private final ConstructorAssembler assembler;
   private final ConstructorName identifier;
   private final AnnotationList annotations;
   private final ModifierList list;
   
   public MemberConstructor(AnnotationList annotations, ModifierList list, ParameterList parameters, Statement body){  
      this(annotations, list, parameters, null, body);
   }  
   
   public MemberConstructor(AnnotationList annotations, ModifierList list, ParameterList parameters, TypePart part, Statement body){  
      this.assembler = new ConstructorAssembler(parameters, part, body);
      this.identifier = new ConstructorName();
      this.compiler = new FunctionScopeCompiler(identifier, COMPILE_MEMBER);
      this.annotations = annotations;
      this.list = list;
   } 
   
   protected TypeState assemble(TypeBody parent, Type type, Scope scope, boolean compile) throws Exception {
      int modifiers = list.getModifiers();
      Scope composite = compiler.define(scope, type);
      ConstructorBuilder builder = assembler.assemble(parent, type, composite);
      FunctionBody body = builder.create(parent, type, modifiers, compile);
      Function constructor = body.create(composite);
      List<Function> functions = type.getFunctions();
      
      annotations.apply(composite, constructor);
      functions.add(constructor);
      body.define(composite);
      
      return new FunctionBodyCompiler(identifier, body);
   }
}