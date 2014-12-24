package org.opennms.karaf.licencemgr.cmd;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.LicenceService;

@Command(scope = "licence-mgr", name = "add", description="adds licence for productId")
public class AddLicenceCommand extends OsgiCommandSupport {

	private LicenceService _licenceService;

	public LicenceService getLicenceService() {
		return _licenceService;
	}

	public void setLicenceService( LicenceService licenceService) {
		_licenceService = licenceService;
	}

	@Argument(index = 0, name = "productId", description = "Product Id to which licence applies", required = true, multiValued = false)
	String productId = null;

	@Argument(index = 1, name = "licence", description = "licence string for productId", required = true, multiValued = false)
	String licence = null;

	@Override
	protected Object doExecute() throws Exception {
		try{
			getLicenceService().addLicence(productId, licence);
			System.out.println("Added licence ProductId='"+productId + "' licence='" + licence+"'");
		} catch (Exception e) {
			System.out.println("Error Adding licence. Exception="+e);
		}
		return null;
	}
}