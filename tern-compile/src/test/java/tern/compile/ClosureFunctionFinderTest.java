package tern.compile;

import static java.util.Collections.EMPTY_LIST;
import static tern.core.function.Origin.DEFAULT;

import java.util.Arrays;
import java.util.Collections;

import junit.framework.TestCase;

import tern.common.store.ClassPathStore;
import tern.common.store.Store;
import tern.core.Context;
import tern.core.constraint.Constraint;
import tern.core.convert.ConstraintMatcher;
import tern.core.convert.FunctionComparator;
import tern.core.function.ClosureFunctionFinder;
import tern.core.function.Function;
import tern.core.function.FunctionSignature;
import tern.core.function.InvocationFunction;
import tern.core.function.Parameter;
import tern.core.function.Signature;
import tern.core.module.ContextModule;
import tern.core.module.Module;
import tern.core.module.Path;
import tern.core.type.Type;
import tern.core.type.TypeExtractor;
import tern.core.type.TypeLoader;

public class ClosureFunctionFinderTest extends TestCase {

   public void testFunctionFinder() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      TypeLoader loader = context.getLoader();
      TypeExtractor extractor = context.getExtractor();
      ConstraintMatcher matcher = context.getMatcher();
      Path path = new Path("/");
      FunctionComparator comparator = new FunctionComparator(matcher);
      Module module = new ContextModule(context, null, path, "yy", "", 1);
      ClosureFunctionFinder finder = new ClosureFunctionFinder(comparator, extractor, loader);
      Parameter parameter = new Parameter("n", Constraint.STRING, 0, false);
      Signature signature = new FunctionSignature(Arrays.asList(parameter), EMPTY_LIST, module, null, DEFAULT, false);
      Type type = new InvocationFunction(signature, null, null, null, "xx").getHandle();
      Function function = finder.findFunctional(type);
      
      assertNotNull(function);
   }
}
