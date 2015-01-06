package org.opennms.karaf.licencepub.cmd;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.LicenceService;
import org.opennms.karaf.licencemgr.metadata.LicenceMetadata;
import org.opennms.karaf.licencepub.LicencePublisher;
import org.opennms.karaf.licencepub.LicenceSpecification;

@Command(scope = "licence-pub", name = "list", description="lists installed licence specifications")
public class ListLicenceSpecsCommand extends OsgiCommandSupport {

	private LicencePublisher _licencePublisher;

	public LicencePublisher getLicencePublisher() {
		return _licencePublisher;
	}

	public void setLicencePublisher(LicencePublisher _licencePublisher) {
		this._licencePublisher = _licencePublisher;
	}

	@Override
	protected Object doExecute() throws Exception {
		try {
			System.out.println("list of licence specifications");

			Map<String, LicenceSpecification> licenceSpecMap = getLicencePublisher().getLicenceSpecMap();
			for (Entry<String, LicenceSpecification> entry : licenceSpecMap.entrySet()){
				
				LicenceSpecification licenceSpecification = entry.getValue();
				LicenceMetadata licenceMetadata = licenceSpecification.getLicenceMetadataSpec();
				
				System.out.println("  productId='"+entry.getKey()+"'\n"
						+ "      licenceMetadata='"+licenceMetadata.toXml()+"'\n"
				        + "      licenceSpecification secret key String='"+licenceSpecification.getAesSecretKeyStr()+"'\n"
				        + "      licenceSpecification public key String='"+licenceSpecification.getPublicKeyStr()+"'\n");
			}
		} catch (Exception e) {
			System.out.println("Error getting list of installed licence specifications. Exception="+e);
		}
		return null;
	}


}