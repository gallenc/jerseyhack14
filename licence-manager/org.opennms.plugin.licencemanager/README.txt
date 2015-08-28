to build

karaf@root> mvn clean install

to install in karaf:

1. install feature url

karaf@root> features:addurl mvn:org.opennms.plugins/org.opennms.plugin.licencemanager.feature/1.0-SNAPSHOT/xml/feature

check the repository is installed using
karaf@root> features:listurl

confirm the following entry in present
 true    mvn:org.opennms.plugins/org.opennms.plugin.licencemanager.feature/1.0-SNAPSHOT/xml/features

karaf@root> features:listrepositories

confirm the following entry in present
org.opennms.plugin.licencemanager.feature 

2. install feature manager feature

karaf@root> features:install org.opennms.plugin.licencemanager.feature/1.0-SNAPSHOT

3. check that the feature manager and jersey dependencies are installed

karaf@root> list
START LEVEL 100 , List Threshold: 50
   ID   State         Blueprint      Level  Name
[ 273] [Active     ] [Created     ] [   80] org.opennms.plugin.licencemanager (1.0.0.SNAPSHOT)
[ 274] [Active     ] [            ] [   80] jersey-server (1.18.1)
[ 275] [Active     ] [            ] [   80] jersey-servlet (1.18.0)
[ 276] [Active     ] [            ] [   80] jersey-core (1.18.0)
[ 277] [Active     ] [            ] [   80] jersey-client (1.18.0)

4. test feature manager using diagnostics page
http://localhost:8181/featuremgr/diagnostics/feature-mgr-rest-diagnostics.html

To run on opennms
-----------------
You need to add the repo where the feature is installed. If you have built on your local machine, add the local repo

sudo vi /opt/opennms/org.ops4j.pax.url.mvn.cfg

change the following property to add file:/home/admin/.m2/repository@snapshots@id=localrepo 
where /home/admin/.m2/repository is the location of local maven repository

org.ops4j.pax.url.mvn.repositories= \
    http://repo1.maven.org/maven2@id=central, \
    http://svn.apache.org/repos/asf/servicemix/m2-repo@id=servicemix, \
    http://repository.springsource.com/maven/bundles/release@id=springsource.release, \
    http://repository.springsource.com/maven/bundles/external@id=springsource.external, \
    https://oss.sonatype.org/content/repositories/releases/@id=sonatype, \
    file:/home/admin/.m2/repository@snapshots@id=localrepo

The diagnostics page can be found at
http://localhost:8980/opennms/licencemgr/diagnostics/licence-mgr-rest-diagnostics.html

You can run the tests against a licence manager installed on opennms by 
copying 
/src/main/resources/licenceServiceTestKaraf.properties 
to
/src/main/resources/licenceServiceTest.properties



