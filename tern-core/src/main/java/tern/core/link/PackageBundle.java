package tern.core.link;

import java.util.List;

public class PackageBundle {

   private final List<Package> packages;
   
   public PackageBundle(List<Package> packages) {
      this.packages = packages;
   }
   
   public List<Package> getPackages() {
      return packages;
   }
}