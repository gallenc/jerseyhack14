package org.opennms.karaf.licencemanager.generate;

import org.junit.Test;

public class GenerateLicenceArtifactsTest {

	private String basePackage="org.opennms.karaf.licencemanager.testbundle";
	private String productId="org.opennms/org.opennms.karaf.licencemanager.testbundle/1.0-SNAPSHOT";
	private String outputDirectory="target/test-output/generated-licence";
	
	@Test
	public void generateLicenceArtifacts(){
		System.out.println("@Test LicenceArtifacts Start");
		
		LicenceArtifactsGenerator licenceArtifactsGenerator = new LicenceArtifactsGenerator(productId, basePackage, outputDirectory);

		System.out.println("@Test LicenceArtifacts End");
	}
}
