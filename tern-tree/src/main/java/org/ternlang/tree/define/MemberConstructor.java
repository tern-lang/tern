package org.ternlang.tree.define;

import static org.ternlang.core.scope.extract.ScopePolicy.COMPILE_MEMBER;

import java.util.List;

import org.ternlang.core.Statement;
import org.ternlang.core.function.Function;
import org.ternlang.core.function.FunctionBody;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeBody;
import org.ternlang.core.type.TypePart;
import org.ternlang.core.type.TypeState;
import org.ternlang.tree.ModifierList;
import org.ternlang.tree.annotation.AnnotationList;
import org.ternlang.tree.compile.FunctionScopeCompiler;
import org.ternlang.tree.constraint.ConstructorName;
import org.ternlang.tree.function.ParameterList;

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
      
      return new FunctionBodyCompiler(body, compiler);
   }
}