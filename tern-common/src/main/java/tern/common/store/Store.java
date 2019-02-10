package tern.common.store;

import java.io.InputStream;
import java.io.OutputStream;

public interface Store {
   InputStream getInputStream(String path);
   OutputStream getOutputStream(String path);
}