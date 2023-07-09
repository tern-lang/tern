https://central.sonatype.org/publish/requirements/#releasing-to-central

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_181
set Path=C:\Program Files\Java\jdk1.8.0_181\bin;%Path%

# snapshot or release
mvn clean deploy

<!-- mvn clean deploy -P release -->

# release it
mvn nexus-staging:release
 
# rollback
mvn nexus-staging:drop

# release manually
https://s01.oss.sonatype.org/#welcome