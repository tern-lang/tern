package org.ternlang.core.convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.ternlang.core.EntityCache;
import org.ternlang.core.convert.proxy.ProxyWrapper;
import org.ternlang.core.scope.Scope;
import org.ternlang.core.type.Type;
import org.ternlang.core.type.TypeExtractor;
import org.ternlang.core.type.TypeLoader;

public class ConstraintMatcher {
   
   private final EntityCache<ConstraintConverter> converters;
   private final ConstraintInspector inspector;
   private final ConstraintConverter converter;   
   private final TypeExtractor extractor;
   private final AliasResolver resolver;
   private final CastChecker checker;
   private final ProxyWrapper wrapper;
   
   public ConstraintMatcher(TypeLoader loader, ProxyWrapper wrapper) {
      this.converters = new EntityCache<ConstraintConverter>();
      this.extractor = new TypeExtractor(loader);
      this.checker = new CastChecker(this, extractor, loader);
      this.inspector = new ConstraintInspector(loader, checker);
      this.converter = new NullConverter();
      this.resolver = new AliasResolver();
      this.wrapper = wrapper;
   }
   
   public ConstraintConverter match(Type type) throws Exception { // type declared in signature
      if(type != null) {
         ConstraintConverter converter = converters.fetch(type);
         
         if(converter == null) {
            converter = resolve(type);
            converters.cache(type, converter);
         }
         return converter;
      }
      return converter;
   }
   
   private ConstraintConverter resolve(Type declare) throws Exception {
      Type type = resolver.resolve(declare);

      if(inspector.isSame(type, Object.class)) {
         return new AnyConverter(wrapper);
      }
      if(inspector.isSame(type, double.class)) {
         return new DoubleConverter(type);
      }
      if(inspector.isSame(type, float.class)) {
         return new FloatConverter(type);
      }
      if(inspector.isSame(type, int.class)) {
         return new IntegerConverter(type);
      }
      if(inspector.isSame(type, long.class)) {
         return new LongConverter(type);
      }
      if(inspector.isSame(type, short.class)) {
         return new ShortConverter(type);
      }
      if(inspector.isSame(type, byte.class)) {
         return new ByteConverter(type);
      }
      if(inspector.isSame(type, char.class)) {
         return new CharacterConverter(type);
      }
      if(inspector.isSame(type, boolean.class)) {
         return new BooleanConverter(type);
      }
      if(inspector.isSame(type, Number.class)) {
         return new NumberConverter(type);
      }
      if(inspector.isSame(type, Double.class)) {
         return new DoubleConverter(type);
      }
      if(inspector.isSame(type, Float.class)) {
         return new FloatConverter(type);
      }
      if(inspector.isSame(type, Integer.class)) {
         return new IntegerConverter(type);
      }
      if(inspector.isSame(type, Long.class)) {
         return new LongConverter(type);
      }
      if(inspector.isSame(type, Short.class)) {
         return new ShortConverter(type);
      }
      if(inspector.isSame(type, Byte.class)) {
         return new ByteConverter(type);
      }
      if(inspector.isSame(type, Character.class)) {
         return new CharacterConverter(type);
      }
      if(inspector.isSame(type, Boolean.class)) {
         return new BooleanConverter(type);
      }
      if(inspector.isSame(type, BigDecimal.class)) {
         return new BigDecimalConverter(type);
      }
      if(inspector.isSame(type, BigInteger.class)) {
         return new BigIntegerConverter(type);
      }
      if(inspector.isSame(type, AtomicLong.class)) {
         return new AtomicLongConverter(type);
      }
      if(inspector.isSame(type, AtomicInteger.class)) {
         return new AtomicIntegerConverter(type);
      }
      if(inspector.isSame(type, String.class)) {
         return new StringConverter();
      }
      if(inspector.isFunction(type)) {
         return new FunctionConverter(extractor, checker, wrapper, type);
      }
      if(inspector.isLike(type, Scope.class)) {
         return new ScopeConverter();
      }
      if(inspector.isLike(type, Enum.class)) {
         return new EnumConverter(type);
      }      
      if(inspector.isSame(type, Class.class)) {
         return new ClassConverter(type);
      }
      if(inspector.isSame(type, List.class)) {
         return new ListConverter(extractor, checker, wrapper, type);
      }
      if(inspector.isSame(type, Properties.class)) {
         return new PropertiesConverter(extractor, checker, wrapper, type);
      }
      if(inspector.isArray(type)) {
         return new ArrayConverter(this, checker, wrapper, type);
      }
      return new ObjectConverter(extractor, checker, wrapper, type);
   }
}