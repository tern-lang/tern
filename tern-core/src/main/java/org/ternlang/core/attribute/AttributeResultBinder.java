package org.ternlang.core.attribute;

import java.util.concurrent.atomic.AtomicReference;

import org.ternlang.core.scope.Scope;

public class AttributeResultBinder {
   
   private final AtomicReference<AttributeResult> reference;
   private final AttributeResultBuilder resolver;
   
   public AttributeResultBinder(Attribute attribute) {
      this.reference = new AtomicReference<AttributeResult>();
      this.resolver = new AttributeResultBuilder(attribute);
   }
   
   public AttributeResult bind(Scope scope) {
      AttributeResult result = reference.get();
      
      if(result == null) {
         result = resolver.create(scope);
         reference.set(result);
      }
      return result;
   }



}
