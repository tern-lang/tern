package org.ternlang.core;

import java.io.InputStream;

public interface ResourceManager {
   byte[] getBytes(String path);
   String getString(String path);
   InputStream getInputStream(String path);
}