package org.ternlang.core.type;

import org.ternlang.core.ResourceManager;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.link.ImportScanner;
import org.ternlang.core.link.Package;
import org.ternlang.core.link.PackageLinker;
import org.ternlang.core.link.PackageLoader;
import org.ternlang.core.link.PackageManager;
import org.ternlang.core.module.ModuleRegistry;
import org.ternlang.core.platform.PlatformProvider;
import org.ternlang.core.stack.ThreadStack;
import org.ternlang.core.type.extend.ClassExtender;
import org.ternlang.core.type.index.TypeIndexer;

public class CacheTypeLoader implements TypeLoader {
   
   private final ImportScanner scanner;
   private final TypeExtractor extractor;
   private final ClassExtender extender;
   private final PlatformProvider provider;
   private final PackageManager manager;
   private final PackageLoader loader;
   private final TypeIndexer indexer;
   private final TypeCache cache;
   
   public CacheTypeLoader(PackageLinker linker, ModuleRegistry registry, ResourceManager manager, ProxyWrapper wrapper, ThreadStack stack){
      this.extractor = new TypeExtractor(this);
      this.provider = new PlatformProvider(extractor, wrapper, stack);
      this.scanner = new ImportScanner(manager);
      this.extender = new ClassExtender(this);
      this.indexer = new TypeIndexer(registry, scanner, extender, provider);
      this.loader = new PackageLoader(linker, manager);
      this.manager = new PackageManager(loader, scanner);
      this.cache = new TypeCache(indexer);
   }
   
   @Override
   public Package importPackage(String module)  {
      return manager.importPackage(module);
   }   
   
   @Override
   public Package importType(String type) {
      return manager.importType(type);  // import a runtime
   }
   
   @Override
   public Package importType(String module, String name) {
      return manager.importType(module, name); 
   }
   
   @Override
   public Type defineType(String module, String name, int modifiers) {
      return indexer.defineType(module, name, modifiers);
   }
   
   @Override
   public Type loadType(String module, String name) {
      return cache.fetch(module, name);
   }
   
   @Override
   public Type loadArrayType(String module, String name, int size) {
      return indexer.loadArrayType(module, name, size); // no cache
   }
   
   @Override
   public Type loadType(String type) {
      return cache.fetch(type);
   }
   
   @Override
   public Type loadType(Class type) {
      return cache.fetch(type);
   } 
}