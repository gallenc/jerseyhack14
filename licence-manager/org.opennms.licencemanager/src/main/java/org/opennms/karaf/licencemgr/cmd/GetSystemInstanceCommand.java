package org.opennms.karaf.licencemgr.cmd;

import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.LicenceService;


@Command(scope = "licence-mgr", name = "getSystemInstance", description="Get Instance ID of Karaf licence manager")
public class GetSystemInstanceCommand extends OsgiCommandSupport {

	private LicenceService _licenceService;

	public LicenceService getLicenceService() {
		return _licenceService;
	}

	public void setLicenceService( LicenceService licenceService) {
		_licenceService = licenceService;
	} 

	@Override
	protected Object doExecute() throws Exception {
		System.out.println("Licence System Instance ID='"+getLicenceService().getSystemId()+"'");
		return null;
	}
}