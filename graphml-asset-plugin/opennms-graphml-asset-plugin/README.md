## OpenNMS Elasticsearch 2 ReST Plugin

### Maven Project
~~~~
project groupId: org.opennms.plugins
project name:    opennms-es-rest
version:         19.0.0-SNAPSHOT
~~~~

### Description

This project creates a graphml mapping from an opennms asset table
Presently experimental

### To install in OpenNMS 

Open the karaf command prompt using
~~~~
ssh -p 8101 admin@localhost

(or ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no if no host checking wanted)
~~~~

To install the feature in karaf use

~~~~

karaf@root> features:addurl mvn:org.opennms.plugins/opennms-graphml-asset-plugin/19.0.0-SNAPSHOT/xml/features
karaf@root> features:install opennms-graphml-asset-plugin

(or features:install opennms-es-rest/19.0.0-SNAPSHOT for a specific version of the feature)
~~~~

