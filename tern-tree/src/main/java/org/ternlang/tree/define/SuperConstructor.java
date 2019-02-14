package org.ternlang.tree.define;

import org.ternlang.core.ModifierType;
import org.ternlang.core.error.InternalStateException;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.scope.instance.SuperExtractor;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeBody;
import org.ternlang.core.type.TypePart;
import org.ternlang.core.type.TypeState;
import org.ternlang.parse.StringToken;
import org.ternlang.tree.ArgumentList;

public class SuperConstructor extends TypePart {
   
   private final SuperConstructorAssembler assembler;
   private final SuperExtractor extractor;
   private final TypePart constructor;
   
   public SuperConstructor() {
      this(null, null);
   }
   
   public SuperConstructor(StringToken token) {
      this(token, null);
   }
   
   public SuperConstructor(ArgumentList arguments) {
      this(null, arguments);
   }
   
   public SuperConstructor(StringToken token, ArgumentList arguments) {
      this.assembler = new SuperConstructorAssembler(arguments);
      this.constructor = new AnyConstructor();
      this.extractor = new SuperExtractor();
   }

   @Override
   public TypeState define(TypeBody body, Type type, Scope scope) throws Exception {
      Type base = extractor.extractor(type);
      
      if(base == null) {
         throw new InternalStateException("No super type for '" + type + "'");
      }     
      return assemble(body, base, scope);
   }

   protected TypeState assemble(TypeBody body, Type type, Scope scope) throws Exception {
      int modifiers = type.getModifiers();
      
      if(ModifierType.isAny(modifiers)) {      
         return constructor.define(body, type, scope);
      }
      return assembler.assemble(body, type, scope);
   }
   
}