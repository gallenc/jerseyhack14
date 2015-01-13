package org.opennms.karaf.licencemgr.cmd;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.LicenceService;
import org.opennms.karaf.licencemgr.metadata.Licence;

@Command(scope = "licence-mgr", name = "addlicence", description="adds licence for productId")
public class AddLicenceCommand extends OsgiCommandSupport {

	private LicenceService _licenceService;

	public LicenceService getLicenceService() {
		return _licenceService;
	}

	public void setLicenceService( LicenceService licenceService) {
		_licenceService = licenceService;
	}

	@Argument(index = 0, name = "licence", description = "encoded licence string including productId", required = true, multiValued = false)
	String licence = null;

	@Override
	protected Object doExecute() throws Exception {
		try{
			String productId = Licence.getUnverifiedMetadata(licence).getProductId();
			getLicenceService().addLicence(licence);
			System.out.println("Added licence ProductId='"+productId + "' licence='" + licence+"'");
		} catch (Exception e) {
			System.out.println("Error Adding licence. Exception="+e);
		}
		return null;
	}
}