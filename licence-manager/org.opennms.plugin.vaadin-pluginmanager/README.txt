to build
--------

mvn clean install

to install in opennms
---------------------

open ssh session to karaf
 ssh -p 8101 admin@localhost
 
 (note change the following versions to suit 16.0.0 / 17.0.0-SNAPSHOT etc)
 
install maven module with osgi
opennms> osgi:install -s  mvn:org.opennms.plugins/org.opennms.plugin.vaadin-pluginmanager/16.0.0

install maven module as a feature
opennms> features:addurl mvn:org.opennms.plugins/org.opennms.plugin.vaadin-pluginmanager/16.0.0/xml/features
opennms> features:install org.opennms.plugin.vaadin-pluginmanager

(note you will need to add local repo to /opt/opennms/etc/org.ops4j.pax.url.mvn.cfg

org.ops4j.pax.url.mvn.localRepository=/home/admin/.m2/repository

also add to force updates on snapshots
org.ops4j.pax.url.mvn.globalUpdatePolicy=always
)

to view page
------------
log into web consol and navigate to 
http://localhost:8980/opennms/admin/plugin-manager