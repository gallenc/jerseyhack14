package org.opennms.karaf.licencemgr.cmd;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.LicenceService;

@Command(scope = "licence-mgr", name = "product", description="lists installed licence-mgrs")
public class RemoveLicenceCommand extends OsgiCommandSupport {

	private LicenceService _licenceService;

	public LicenceService getLicenceService() {
		return _licenceService;
	}

	public void setLicenceService( LicenceService licenceService) {
		_licenceService = licenceService;
	} 

	@Argument(index = 0, name = "productId", description = "Product Id to which licence-mgr applies", required = true, multiValued = false)
	String productId = null;

	@Override
	protected Object doExecute() throws Exception {

		if (getLicenceService().removeLicence(productId)){
			System.out.println("Removed licence for productId=" + productId);
		}else {
			System.out.println("No licence installed for productId='" + productId+"'");
		}

		return null;
	}
}