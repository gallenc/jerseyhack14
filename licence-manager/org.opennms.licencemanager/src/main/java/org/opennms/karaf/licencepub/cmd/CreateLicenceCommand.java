package org.opennms.karaf.licencepub.cmd;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.LicenceService;
import org.opennms.karaf.licencemgr.metadata.Licence;
import org.opennms.karaf.licencemgr.metadata.LicenceMetadata;
import org.opennms.karaf.licencepub.LicencePublisher;

@Command(scope = "licence-pub", name = "createLicence", description="creates a new licence for given metadata xml (surround xml in 'quotes')")
public class CreateLicenceCommand extends OsgiCommandSupport {

	private LicencePublisher _licencePublisher;

	public LicencePublisher getLicencePublisher() {
		return _licencePublisher;
	}

	public void setLicencePublisher(LicencePublisher _licencePublisher) {
		this._licencePublisher = _licencePublisher;
	}


	@Argument(index = 0, name = "licenceMetadataXml", description = "xml encoded licence metadata including productId", required = true, multiValued = false)
	String licenceMetadataXml = null;

	@Override
	protected Object doExecute() throws Exception {
		try{
			String licenceInstanceStr = getLicencePublisher().createLicenceInstanceStr(licenceMetadataXml);
			System.out.println("Created licence instance. licenceInstanceStr='" + licenceInstanceStr+"'");
		} catch (Exception e) {
			System.out.println("Error Creating licence. Exception="+e);
		}
		return null;
	}
}