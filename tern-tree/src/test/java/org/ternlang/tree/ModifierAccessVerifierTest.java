package org.ternlang.tree;

import junit.framework.TestCase;

import org.ternlang.core.Context;
import org.ternlang.core.module.EmptyModule;
import org.ternlang.core.module.Module;

public class ModifierAccessVerifierTest extends TestCase {
   
   public void testAccess() throws Exception {
      ModifierAccessVerifier verifier = new ModifierAccessVerifier();
      Context context = new MockContext();
      Module module = new EmptyModule(context);
      MockType grandChild = new MockType(module, "Root$Child$GrandChild", null, null);
      MockType child = new MockType(module, "Root$Child", null, null);
      MockType root = new MockType(module, "Root", null, null);
      MockType similarRoot = new MockType(module, "RootX", null, null);
      MockType similarChild = new MockType(module, "RootXChild", null, null);
      
      assertTrue(verifier.isAccessible(root, child));
      assertTrue(verifier.isAccessible(child, root));

      assertTrue(verifier.isAccessible(root, grandChild));
      assertTrue(verifier.isAccessible(grandChild, root));
      
      assertTrue(verifier.isAccessible(root, root));
      assertTrue(verifier.isAccessible(root, root));
      
      assertTrue(verifier.isAccessible(child, grandChild));
      assertTrue(verifier.isAccessible(grandChild, child));
      
      assertFalse(verifier.isAccessible(root, similarRoot));
      assertFalse(verifier.isAccessible(child, similarRoot));
      assertFalse(verifier.isAccessible(grandChild, similarRoot));
      
      assertFalse(verifier.isAccessible(similarRoot, root));
      assertFalse(verifier.isAccessible(similarRoot, child));
      assertFalse(verifier.isAccessible(similarRoot, grandChild));
      
      assertFalse(verifier.isAccessible(root, similarChild));
      assertFalse(verifier.isAccessible(child, similarChild));
      assertFalse(verifier.isAccessible(grandChild, similarChild));
      
      assertFalse(verifier.isAccessible(similarChild, root));
      assertFalse(verifier.isAccessible(similarChild, child));
      assertFalse(verifier.isAccessible(similarChild, grandChild));

   }

}
