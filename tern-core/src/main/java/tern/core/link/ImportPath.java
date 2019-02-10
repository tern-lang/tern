package tern.core.link;

import java.util.Map;
import java.util.Set;

public interface ImportPath {
   Map<String, Set<String>> getAliases();
   Map<String, Set<String>> getTypes();
   Set<String> getDefaults();
}