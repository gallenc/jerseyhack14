# OSGi Licence Manager System

## Overview
This project aims to create a licence management system for OSGi plugins targeted initially at the 
Apache Karaf OSGi container (http://karaf.apache.org/). The intention is to support the complete
lifecycle of developing, selling and deploying a product based upon OSGi modules. 

The OSGi licence manager provides ReST api's which can be used with any shopping cart. However we have also
provided a shopping cart solution for Wordpress which demonstrates the use of the API and give a good workable 
solution out of the box.


# The OSGi Licence Manager  org.opennms.licencemanager

This project builds an OSGi licence manager bundle which can be deployed in a Karaf container.

The OSGi Licence Manager provides services to make it easy for a vendor/developer to use a shopping cart
 to create licences for a customer account. It also provides services to make it easy for
 customer/cient OSGi modules to request and verify a locally stored licence.
 
# Wordpress Easy Digital Downloads plugin edd-downloads-as-osgi

This project is a PHP plugin for Wordpress which extends the Easy Digital Downloads plugin
(https://easydigitaldownloads.com/)

This plugin allows users to purchase the right to generate software licences for their OSGi system 
in the same way they can purchase downloads. By extending this great EDD plugin, we benifit from using 
a well supported commercial gateway which integrates with paypal and has a lot of theme support in Wordpress.

The edd-downloads-as-osgi plugin uses the web services (ReST) inteface from the OSGi licence manager running in Karaf
as a backend to generate the licences.



==Licence==

All of the Java / OSGi modules in this project are licensed under the Apache 2 Licence
(This is to make the library widely usable with multiple OSGi projects and shopping carts)

All the Wordpress Plugins created in this project  are licensed under GPL V2+ 
(in accordance with Wordpress)