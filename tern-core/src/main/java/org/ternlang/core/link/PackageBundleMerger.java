package org.ternlang.core.link;

import java.util.List;

public class PackageBundleMerger {

   private final Package empty;
   
   public PackageBundleMerger() {
      this.empty = new NoPackage();
   }
   
   public Package merge(PackageBundle bundle) {
      List<Package> packages = bundle.getPackages();
      
      if(!packages.isEmpty()) {
         int size = packages.size();
         
         if(size > 1) {
            return new PackageList(packages);
         }
         return packages.get(0);
      }
      return empty;
   }
}