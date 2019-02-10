package tern.core;

import java.io.InputStream;
import java.util.List;

import tern.common.Progress;
import tern.core.annotation.Annotation;
import tern.core.function.Function;
import tern.core.link.ImportManager;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.property.Property;
import tern.core.scope.Scope;
import tern.core.type.Phase;
import tern.core.type.Type;

public class MockModule implements Module {

   @Override
   public Progress<Phase> getProgress() {
      return null;
   }

   @Override
   public Scope getScope() {
      return null;
   }

   @Override
   public String getName() {
      return null;
   }

   @Override
   public int getModifiers() {
      return 0;
   }

   @Override
   public int getOrder() {
      return 0;
   }

   @Override
   public Context getContext() {
      return null;
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
      return null;
   }

   @Override
   public List<Property> getProperties() {
      return null;
   }

   @Override
   public List<Function> getFunctions() {
      return null;
   }

   @Override
   public List<Type> getTypes() {
      return null;
   }

   @Override
   public Type getType() {
      return null;
   }

   @Override
   public Path getPath() {
      return null;
   }

}
