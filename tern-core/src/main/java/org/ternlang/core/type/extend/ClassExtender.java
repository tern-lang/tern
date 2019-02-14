package org.ternlang.core.type.extend;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.ternlang.core.function.Function;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.TypeLoader;

public class ClassExtender {
   
   private final ExtensionRegistry registry;
   private final AtomicBoolean done;
   
   public ClassExtender(TypeLoader loader) {
      this.registry = new ExtensionRegistry(loader);
      this.done = new AtomicBoolean();
   }
   
   public List<Function> extend(Class type){
      if(!done.get()) {
         registry.register(File.class, FileExtension.class);
         registry.register(Date.class, DateExtension.class);
         registry.register(Reader.class, ReaderExtension.class);
         registry.register(Writer.class, WriterExtension.class);
         registry.register(InputStream.class, InputStreamExtension.class);
         registry.register(OutputStream.class, OutputStreamExtension.class);
         registry.register(URLConnection.class, URLConnectionExtension.class);
         registry.register(URL.class, URLExtension.class);
         registry.register(Iterator.class, IteratorExtension.class);
         registry.register(Number.class, NumberExtension.class);
         registry.register(BigDecimal.class, BigDecimalExtension.class);
         registry.register(BigInteger.class, BigIntegerExtension.class);
         registry.register(AtomicLong.class, AtomicLongExtension.class);
         registry.register(AtomicInteger.class, AtomicIntegerExtension.class);
         registry.register(Integer.class, IntegerExtension.class);
         registry.register(Long.class, LongExtension.class);
         registry.register(Double.class, DoubleExtension.class);
         registry.register(Float.class, FloatExtension.class);
         registry.register(Byte.class, ByteExtension.class);
         registry.register(Short.class, ShortExtension.class);
         registry.register(Scope.class, ScopeExtension.class);
         done.set(true);
      }
      return registry.extract(type);
   }

}