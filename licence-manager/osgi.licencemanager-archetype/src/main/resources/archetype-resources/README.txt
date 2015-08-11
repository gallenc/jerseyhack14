#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
README.txt

GENERATED PROJECT
-----------------
project groupId: ${groupId}
project name:    ${artifactId}
version:         ${version}

NEXT STEPS
----------

Once the archetype has completed you must remove the <modules> section from the parent pom.
This is generated automatically by the archetype but is not needed as modules sections are
included in the default profile.

The first time you build the project you should generate a new licence using
mvn clean install -P generateLicence

Once a licence is generated you only need to build the project using
mvn clean install

This avoids having to install new licences with every build.

open karaf command prompt using
ssh -p 8101 admin@localhost

to install the feature in karaf use
karaf@root> features:addurl mvn:${groupId}/${artifactId}.Feature/${version}/xml/features
karaf@root> features:install ${artifactId}.Feature

to install the licence specification in karaf use
karaf@root>  osgi:install -s  mvn:${groupId}/${artifactId}.LicenceSpec/${version}



