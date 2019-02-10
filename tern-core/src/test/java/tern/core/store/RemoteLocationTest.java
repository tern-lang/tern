package tern.core.store;

import java.net.URI;

import tern.common.store.RemoteLocation;

import junit.framework.TestCase;

public class RemoteLocationTest extends TestCase {

   public void testRelativePath() throws Exception {
      URI root1 = new URI("http://domain.com:77/resource/");
      RemoteLocation location1 = new RemoteLocation(root1);
      
      assertEquals(location1.createRelative("/demo/script.snap").toString(), "http://domain.com:77/resource/demo/script.snap");
      assertEquals(location1.createRelative("/script.snap").toString(), "http://domain.com:77/resource/script.snap");
      assertEquals(location1.createRelative("/").toString(), "http://domain.com:77/resource/");
      assertEquals(location1.createRelative("file.snap").toString(), "http://domain.com:77/resource/file.snap");
 
      URI root2 = new URI("http://domain.com:77");
      RemoteLocation location2 = new RemoteLocation(root2);
      
      assertEquals(location2.createRelative("/demo/script.snap").toString(), "http://domain.com:77/demo/script.snap");
      assertEquals(location2.createRelative("/script.snap").toString(), "http://domain.com:77/script.snap");
      assertEquals(location2.createRelative("/").toString(), "http://domain.com:77/");
      assertEquals(location2.createRelative("file.snap").toString(), "http://domain.com:77/file.snap");
   }
}
