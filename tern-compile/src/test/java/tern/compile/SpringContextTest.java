package tern.compile;

import junit.framework.TestCase;

import tern.common.store.ClassPathStore;
import tern.common.store.Store;
import tern.core.Context;
import tern.core.ExpressionEvaluator;
import tern.core.scope.Model;

import java.util.HashMap;
import java.util.Map;

public class SpringContextTest extends TestCase {

    public static interface ApplicationContext {
        <T> T getBean(String name);
    }

    public static class MapApplicationContext implements ApplicationContext {

        private final Map<String, Object> beans;

        public MapApplicationContext(Map<String, Object> beans) {
            this.beans = beans;
        }

        @Override
        public <T> T getBean(String name) {
            return (T)beans.get(name);
        }
    }

    public static class ApplicationContextModel implements Model {

        private final ApplicationContext context;

        public ApplicationContextModel(ApplicationContext context) {
            this.context = context;
        }

        @Override
      public Object getAttribute(String name) {
            return context.getBean(name);
        }
    }

    public static class Foo {

        public String callFoo(Bar bar, int value) {
            return "bar=" + bar + " value="+value;
        }

        public String doFunc(FuncInterface func, Bar bar) {
            return func.callFunc(11, "blah", bar);
        }
    }

    public static class BarFactory {

        public Bar createBar(String name, int value) {
            return new Bar(name, value);
        }
    }

    public static interface FuncInterface{
        String callFunc(int value, String text, Bar bar);
    }

    public static class Bar {

        private final String name;
        private final int value;

        public Bar(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName(){
            return name;
        }

        public int getValue(){
            return value;
        }

        @Override
        public String toString(){
            return String.format("(%s, %s)", name, value);
        }
    }

    public static ApplicationContext createMockApplicationContext(){
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("foo", new Foo());
        beans.put("barFactory", new BarFactory());
        beans.put("x", 22); // put in a simple variable

        return new MapApplicationContext(beans);
    }

    public static Model createModelAdapter(ApplicationContext context) {
        return new ApplicationContextModel(context);
    }

    public static ExpressionEvaluator createEvaluator() {
        Store store = new ClassPathStore();
        Context context = new StoreContext(store);
        return context.getEvaluator();
    }

    public void testContextModel() throws Exception {
        ApplicationContext context = createMockApplicationContext();
        ExpressionEvaluator evaluator = createEvaluator();
        Model model = createModelAdapter(context); // provide an adapter to the Spring context

        // basic bean references and math
        assertEquals("bar=(blah, 33) value=44", evaluator.evaluate(model, "foo.callFoo(barFactory.createBar('blah', 11 + x), 44f)"));
        assertEquals("bar=(xx, 3456) value=-94", evaluator.evaluate(model, "foo.callFoo(barFactory.createBar('xx', Math.round(3456.33)), -94)"));

        // function interfaces
        assertEquals("a=11, b=blah, c=(xx, 3456)", evaluator.evaluate(model, "foo.doFunc((a, b, c) -> \"a=${a}, b=${b}, c=${c}\", barFactory.createBar('xx', Math.round(3456.33)))"));
        assertEquals("a=11, b=blah, c=(xx, 3456)", evaluator.evaluate(model,
                "foo.doFunc((a, b, c) -> {\n"+
                "   for(var i in 0..10){\n"+
                "        println(\"i=${i} a=${a}\");\n"+
                "   }\n"+
                "   var bar = barFactory.createBar('nee', 5);\n"+
                "   println('bar='+bar);\n"+
                "   return \"a=${a}, b=${b}, c=${c}\";\n"+
                "}, barFactory.createBar('xx', Math.round(3456.33)))"));
    }
}
