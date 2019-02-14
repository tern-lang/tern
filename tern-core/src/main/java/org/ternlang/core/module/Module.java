package org.ternlang.core.module;

import java.io.InputStream;
import java.util.List;

import org.ternlang.core.Context;
import org.ternlang.core.Entity;
import org.ternlang.core.annotation.Annotation;
import org.ternlang.core.function.Function;
import org.ternlang.core.link.ImportManager;
import org.ternlang.core.property.Property;
import org.ternlang.core.type.Type;

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