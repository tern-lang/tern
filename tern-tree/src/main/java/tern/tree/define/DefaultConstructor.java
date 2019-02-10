package tern.tree.define;

import static tern.core.Reserved.TYPE_CONSTRUCTOR;
import static tern.core.type.Category.OTHER;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import tern.core.NoStatement;
import tern.core.Statement;
import tern.core.function.Function;
import tern.core.scope.Scope;
import tern.core.type.Category;
import tern.core.type.Type;
import tern.core.type.TypeBody;
import tern.core.type.TypeState;
import tern.tree.ModifierList;
import tern.tree.annotation.AnnotationList;
import tern.tree.function.ParameterList;

public class DefaultConstructor extends TypeState {
   
   private final AtomicReference<TypeState> reference;
   private final MemberConstructor constructor;
   private final AnnotationList annotations;
   private final ParameterList parameters;
   private final ModifierList modifiers;
   private final Statement statement;
   private final TypeBody body;
   private final boolean compile;

   public DefaultConstructor(TypeBody body){
      this(body, true);
   }
   
   public DefaultConstructor(TypeBody body, boolean compile) {
      this.reference = new AtomicReference<TypeState>();
      this.annotations = new AnnotationList();
      this.parameters = new ParameterList();
      this.modifiers = new ModifierList();
      this.statement = new NoStatement();
      this.constructor = new ClassConstructor(annotations, modifiers, parameters, statement);
      this.compile = compile;
      this.body = body;
   } 

   @Override
   public Category define(Scope scope, Type type) throws Exception {
      List<Function> functions = type.getFunctions();
      
      for(Function function : functions) {
         String name = function.getName();
         
         if(name.equals(TYPE_CONSTRUCTOR)) {
            return OTHER;
         }
      }
      TypeState allocation = constructor.assemble(body, type, scope, compile);
      
      if(allocation != null) {
         reference.set(allocation);
      }
      return OTHER;
   }
   
   @Override
   public void compile(Scope scope, Type type) throws Exception {
      TypeState state = reference.get();
      
      if(state != null) {
         state.compile(scope, type);
      }
   }
}