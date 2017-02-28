Both these tests identical to OpenNMS tests in graphml module:

GraphMLReaderTest fails to verify that the file is the same

GraphMLWriterTest This test passes BUT there is no content in output.graphml

New tests in 
GraphMLReader2Test
in all cases look at the output(n).xml - 

The graphml is generated with no namespace definition and/or no content
<graphml xmlns:ns2="http://www.w3.org/1999/xlink">



