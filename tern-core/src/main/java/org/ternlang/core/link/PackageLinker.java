package org.ternlang.core.link;

import org.ternlang.core.module.Path;

public interface PackageLinker {  
   Package link(Path path, String source) throws Exception;
   Package link(Path path, String source, String grammar) throws Exception;
}