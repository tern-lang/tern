package tern.core.module;

import java.io.InputStream;
import java.util.List;

import tern.core.Context;
import tern.core.Entity;
import tern.core.annotation.Annotation;
import tern.core.function.Function;
import tern.core.link.ImportManager;
import tern.core.property.Property;
import tern.core.type.Type;

public interface Module extends Entity{
   Context getContext();
   ImportManager getManager();
   Type getType(Class type);   
   Type getType(String name);
   Type addType(String name, int modifiers);
   Module getModule(String module); 
   InputStream getResource(String path);
   List<Annotation> getAnnotations();
   List<Property> getProperties();
   List<Function> getFunctions();
   List<Type> getTypes();
   Type getType();
   Path getPath();
}