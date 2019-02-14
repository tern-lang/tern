package org.ternlang.core.module;

import static org.ternlang.core.ModifierType.MODULE;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ternlang.common.CompleteProgress;
import org.ternlang.common.Progress;
import org.ternlang.core.Context;
import org.ternlang.core.Reserved;
import org.ternlang.core.annotation.Annotation;
import org.ternlang.core.function.Function;
import org.ternlang.core.link.ImportManager;
import org.ternlang.core.property.Property;
import org.ternlang.core.scope.ModelScope;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Phase;
import org.ternlang.core.type.Type;

public class EmptyModule implements Module {

   private final List<Property> properties;
   private final List<Function> functions;
   private final Progress<Phase> progress;
   private final Context context;
   private final Scope scope;
   private final Type type;
   
   public EmptyModule(Context context) {
      this.progress = new CompleteProgress<Phase>();
      this.properties = new ArrayList<Property>();
      this.functions = new ArrayList<Function>();      
      this.scope = new ModelScope(null, this);
      this.type = new ModuleType(this);
      this.context = context;
   }
   
   @Override
   public Type getType() {
      return type;
   }

   @Override
   public Scope getScope() {
      return scope;
   }

   @Override
   public Context getContext() {
      return context;
   }
   
   @Override
   public Progress<Phase> getProgress() {
      return progress;
   }

   @Override
   public ImportManager getManager() {
      return null;
   }

   @Override
   public Type getType(Class type) {
      return null;
   }

   @Override
   public Type getType(String name) {
      return null;
   }

   @Override
   public Type addType(String name, int modifiers) {
      return null;
   }

   @Override
   public Module getModule(String module) {
      return null;
   }

   @Override
   public InputStream getResource(String path) {
      return null;
   }

   @Override
   public List<Annotation> getAnnotations() {
      return Collections.emptyList();
   }
   
   @Override
   public List<Property> getProperties() {
      return properties;
   }

   @Override
   public List<Function> getFunctions() {
      return functions;
   }

   @Override
   public List<Type> getTypes() {
      return Collections.emptyList();
   }

   @Override
   public String getName() {
      return null;
   }

   @Override
   public Path getPath() {
      return null;
   }
   
   @Override
   public int getModifiers() {
      return MODULE.mask;
   }

   @Override
   public int getOrder() {
      return 0;
   }
   
   @Override
   public String toString() {
      return Reserved.DEFAULT_PACKAGE;
   }
}