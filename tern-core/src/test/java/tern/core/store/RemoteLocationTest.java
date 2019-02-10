package tern.core.store;

import java.net.URI;

import tern.common.store.RemoteLocation;

import junit.framework.TestCase;

public class RemoteLocationTest extends TestCase {

   public void testRelativePath() throws Exception {
      URI root1 = new URI("http://domain.com:77/resource/");
      RemoteLocation location1 = new RemoteLocation(root1);
      
      assertEquals(location1.createRelative("/demo/script.tern").toString(), "http://domain.com:77/resource/demo/script.tern");
      assertEquals(location1.createRelative("/script.tern").toString(), "http://domain.com:77/resource/script.tern");
      assertEquals(location1.createRelative("/").toString(), "http://domain.com:77/resource/");
      assertEquals(location1.createRelative("file.tern").toString(), "http://domain.com:77/resource/file.tern");
 
      URI root2 = new URI("http://domain.com:77");
      RemoteLocation location2 = new RemoteLocation(root2);
      
      assertEquals(location2.createRelative("/demo/script.tern").toString(), "http://domain.com:77/demo/script.tern");
      assertEquals(location2.createRelative("/script.tern").toString(), "http://domain.com:77/script.tern");
      assertEquals(location2.createRelative("/").toString(), "http://domain.com:77/");
      assertEquals(location2.createRelative("file.tern").toString(), "http://domain.com:77/file.tern");
   }
}
