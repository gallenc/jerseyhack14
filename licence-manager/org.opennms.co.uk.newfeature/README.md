# Example Test Project 

Test Project for creating Licence Specifications and Licence Authenticators

## overview
This is a pre-generated project which is very similar to that generated by the osgi.licencemanager-archetype
However this project also matches all the default entries in the test page which makes initial testing easier

http://localhost:8181/licencemgr/test/licence-mgr-rest-test.html

## Build
In base project use mvn clean install

## Deploy 
Take the target jars from the LicenceAuthenticator and LicenceSpecification projects and place them in the
/deploy directory of the running Karaf container where the LicenceManager has already been installed.

* org.opennms.co.uk.newfeature.LicenceAuthenticator-0.0.1-SNAPSHOT.jar

* org.opennms.co.uk.newfeature.LicenceSpec-0.0.1-SNAPSHOT.jar

## Test

1. Look in the Karaf consol. it should say

```
No licence installed for productId:'org.opennms.co.uk/org.opennms.co.uk.newfeature/0.0.1-SNAPSHOT'
```

2. Type list in the Karaf consol

The Karaf consol should say (the ID's will be different);

```
karaf@root> list
START LEVEL 100 , List Threshold: 50
   ID   State         Blueprint      Level  Name
[  54] [Active     ] [            ] [   80] jersey-core (1.18.0)
[  55] [Active     ] [            ] [   80] jersey-server (1.18.1)
[  56] [Active     ] [            ] [   80] jersey-servlet (1.18.0)
[  86] [Active     ] [Created     ] [   80] org.opennms.licencemanager (1.0.0.SNAPSHOT)
[  88] [Active     ] [Failure     ] [   80] LicenceAuthenticator for New Feature (0.0.1.SNAPSHOT)
[  89] [Active     ] [Created     ] [   80] LicenceSpec for New Feature (0.0.1.SNAPSHOT)

```

3. Generate and install a new licence for New Feature using the test page
(see separate instructions for a full explanation)

* navigate to the test page http://localhost:8181/licencemgr/test/licence-mgr-rest-test.html
* scroll down to the Licence Publisher section and press the *create licence* button
* copy the bytes in the reply string between &gt;licence&lt; and &gt;/licence&lt; 
* paste into to the *Licence Manager Add Licence* text area
* press the *Licence Manager Add Licence* button 

You should see a reply message Successfully added licence instance for productId=org.opennms.co.uk/org.opennms.co.uk.newfeature/0.0.1-SNAPSHOT
indicating that the licence has been correctly installed. You should now be able to restart hte failed Licence Authenticator



Licence Publisher

4. Restart the licence authenticator

find the ID for the failed Licence Authenticator and issue the command

osgi:restart -id-

The Karaf consol should say (the ID's will be different);

```
karaf@root> osgi:restart 88
Registered Product Specification for productId=org.opennms.co.uk/org.opennms.co.uk.newfeature/0.0.1-SNAPSHOT
BundleLicenceAuthenticator authenticated licence for productId=org.opennms.co.uk/org.opennms.co.uk.newfeature/0.0.1-SNAPSHOT
Licence Metadata xml=<?xml version="1.0" encoding="UTF-8" standalone="yes"?><licenceMetadata><productId>org.opennms.co.uk/org.opennms.co.uk.
newfeature/0.0.1-SNAPSHOT</productId><licensee>Mr Craig Gallen</licensee><licensor>OpenNMS UK</licensor><systemId>4ad72a34e3635c1b-99da3323<
/systemId><startDate>2015-01-07T15:10:45.138Z</startDate><expiryDate>2015-01-07T15:10:45.138Z</expiryDate><options><option><description>this
 is the description</description><name>option1</name><value>newvalue</value></option></options></licenceMetadata>
karaf@root>

```

5. Verify the service is now running 

type list in the Karaf consol

The Karaf consol should say (the ID's will be different);

```
karaf@root> list
START LEVEL 100 , List Threshold: 50
   ID   State         Blueprint      Level  Name
[  54] [Active     ] [            ] [   80] jersey-core (1.18.0)
[  55] [Active     ] [            ] [   80] jersey-server (1.18.1)
[  56] [Active     ] [            ] [   80] jersey-servlet (1.18.0)
[  86] [Active     ] [Created     ] [   80] org.opennms.licencemanager (1.0.0.SNAPSHOT)
[  87] [Active     ] [Created     ] [   80] org.opennms.co.uk.testfeature.LicenceSpec (0.1.0.SNAPSHOT)
[  88] [Active     ] [Created     ] [   80] LicenceAuthenticator for New Feature (0.0.1.SNAPSHOT)
[  89] [Active     ] [Created     ] [   80] LicenceSpec for New Feature (0.0.1.SNAPSHOT)

```

You can see that the licenced module is now running.

