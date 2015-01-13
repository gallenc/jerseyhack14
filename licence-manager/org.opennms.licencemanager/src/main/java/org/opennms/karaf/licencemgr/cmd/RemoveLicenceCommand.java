package org.opennms.karaf.licencemgr.cmd;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.LicenceService;

@Command(scope = "licence-mgr", name = "removelicence", description="Removes licence for selected productId")
public class RemoveLicenceCommand extends OsgiCommandSupport {

	private LicenceService _licenceService;

	public LicenceService getLicenceService() {
		return _licenceService;
	}

	public void setLicenceService( LicenceService licenceService) {
		_licenceService = licenceService;
	} 

	@Argument(index = 0, name = "productId", description = "ProductId to remove licence", required = true, multiValued = false)
	String productId = null;

	@Override
	protected Object doExecute() throws Exception {
		try{
			if (getLicenceService().removeLicence(productId)){
				System.out.println("Removed licence for productId=" + productId);
			}else {
				System.out.println("No licence installed for productId='" + productId+"'");
			}
		} catch (Exception e) {
			System.out.println("error removing licence for productId. Exception="+e);
		}

		return null;
	}
}