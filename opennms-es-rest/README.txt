README.txt

GENERATED PROJECT
-----------------
project groupId: org.opennms.plugins
project name:    opennms-es-rest
version:         1.0-SNAPSHOT

Description
-----------
This project sends opennms data to elastic search using the Jest REST library
(https://github.com/searchbox-io/Jest)

NEXT STEPS
----------


open karaf command prompt using
ssh -p 8101 admin@localhost

(or ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no if no host checking wanted)

to install the feature in karaf use
karaf@root> features:addurl mvn:org.opennms.plugins/opennms-es-rest/1.0-SNAPSHOT/xml/features
karaf@root> features:install opennms-es-rest

(or features:install opennms-es-rest/1.0-SNAPSHOT for a specific version of the feature)


To install on OpenNMS (Tested on OpenNMS 18.0.0)
------------------------------------------------

1. You must add the jar httpasyncclient-osgi-4.0.2.jar to <opennmshome>/lib

(from http://repo1.maven.org/maven2/org/apache/httpcomponents/httpasyncclient-osgi/4.0.2/)

and modify <opennmshome>/etc/custom.properties to add the following lines to org.osgi.framework.system.packages.extra

        org.apache.http.nio.conn;version=4.0.2,\
        org.apache.http.nio.conn.ssl;version=4.0.2,\
        org.apache.http.nio.conn.scheme;version=4.0.2,\
        org.apache.http.impl.nio.client;version=4.3.3,\
        org.apache.http.impl.nio.conn;version=4.0.2,\

2. You need to add the repo where the feature is installed to the opennms karaf configuration.
Obviously this could point at a remote repository
However if you have built on your local machine, add the local repo as follows;

sudo vi /opt/opennms/org.ops4j.pax.url.mvn.cfg

change the following property to add file:/home/admin/.m2/repository@snapshots@id=localrepo 
where /home/admin/.m2/repository is the location of your local (user account) maven repository

org.ops4j.pax.url.mvn.repositories= \
    http://repo1.maven.org/maven2@id=central, \
    http://svn.apache.org/repos/asf/servicemix/m2-repo@id=servicemix, \
    http://repository.springsource.com/maven/bundles/release@id=springsource.release, \
    http://repository.springsource.com/maven/bundles/external@id=springsource.external, \
    https://oss.sonatype.org/content/repositories/releases/@id=sonatype, \
    file:/home/admin/.m2/repository@snapshots@id=localrepo



