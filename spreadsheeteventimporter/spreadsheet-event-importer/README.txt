OpenNMS Spreadsheet Event Importer
----------------------------------

This tool helps create an eventConfig.xml file for OpenNMS
based upon an imported spreadsheet

to build 
cd to project directory and type
mvn clean install

to test
cd to <project directory>/target and type
java -jar spreadsheet-event-importer-0.0.1-SNAPSHOT.one-jar.jar -b workbook.xlsx -s testsheet -e testeventfileOUT.xml -w

to test generating a test script
spreadsheet-event-importer-1.0-SNAPSHOT.one-jar.jarjava -jar spreadsheet-event-importer-1.0-SNAPSHOT.one-jar.jar --eventfile testeventfileOUT.xml --testfile EventsTest.sh --totestfile

