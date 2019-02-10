package tern.compile;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import tern.common.store.ClassPathStore;
import tern.common.store.Store;
import tern.core.Context;
import tern.tree.template.Segment;
import tern.tree.template.SegmentIterator;

public class SegmentIteratorTest extends TestCase {

   public void testTemplate() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      List<String> list = Arrays.asList(
            "a=${$aa",
            "arr=${arr}",
            "arr1=$${arr1}",
            "arr[1]=${arr[1]}",
            "arr[1]=$$${arr[1]}",
            "array[1]=$$$${array[1]}");
      
      for(String template: list){
         SegmentIterator iterator = new SegmentIterator(context.getEvaluator(), context.getWrapper(), template.toCharArray());
         System.err.println("["+template+"]");
         int index=0;
         
         while(iterator.hasNext()) {
            Segment segment = iterator.next();
            System.err.println(index+"=["+segment+"]-->"+segment.getClass().getSimpleName());
            index++;
         }
         System.err.println();
      }
   }
}
