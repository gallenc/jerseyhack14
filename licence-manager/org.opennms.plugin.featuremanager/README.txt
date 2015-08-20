to build

karaf@root> mvn clean install

to install in karaf:

1. install feature url

karaf@root> features:addurl mvn:org.opennms.plugins/org.opennms.featuremanager.feature/1.0-SNAPSHOT/xml/featuresopennms.featuremanager.feature

check the repository is installed using
karaf@root> features:listurl

confirm the following entry in present
 true    mvn:org.opennms.plugins/org.opennms.featuremanager.feature/1.0-SNAPSHOT/xml/features

karaf@root> features:listrepositories

confirm the following entry in present
org.opennms.featuremanager.feature 


2. install feature manager feature

karaf@root> features:install org.opennms.featuremanager.feature/1.0-SNAPSHOT

3. check that the feature manager and jersey dependencies are installed
karaf@root> list
START LEVEL 100 , List Threshold: 50
   ID   State         Blueprint      Level  Name
[ 273] [Active     ] [Created     ] [   80] org.opennms.featuremanager (1.0.0.SNAPSHOT)
[ 274] [Active     ] [            ] [   80] jersey-server (1.18.1)
[ 275] [Active     ] [            ] [   80] jersey-servlet (1.18.0)
[ 276] [Active     ] [            ] [   80] jersey-core (1.18.0)
[ 277] [Active     ] [            ] [   80] jersey-client (1.18.0)


