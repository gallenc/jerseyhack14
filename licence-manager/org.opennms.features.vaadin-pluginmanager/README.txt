to build
--------

mvn clean install

to install in opennms
---------------------

open ssh session to karaf
 ssh -p 8101 admin@localhost
 
install maven module
opennms> osgi:install -s  mvn:org.opennms.features/org.opennms.features.vaadin-pluginmanager/16.0.0

to view page
------------
log into web consol and navigate to 
http://localhost:8980/opennms/admin/plugin-manager