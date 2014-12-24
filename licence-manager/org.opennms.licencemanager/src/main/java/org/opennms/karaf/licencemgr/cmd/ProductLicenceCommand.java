package org.opennms.karaf.licencemgr.cmd;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.LicenceService;

@Command(scope = "licence-mgr", name = "product-licence", description="returns licence set for productId")
public class ProductLicenceCommand extends OsgiCommandSupport {

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
		try{
			String licence = getLicenceService().getLicence(productId);
			System.out.println("productId='" + productId+"' licence="+licence+"'");
		} catch (Exception e) {
			System.out.println("getting licence for productId. Exception="+e);
		}
		return null;
	}
}