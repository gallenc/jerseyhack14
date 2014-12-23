package org.opennms.karaf.licencemgr.cmd;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.LicenceService;


@Command(scope = "licence-mgr", name = "makeSystemInstance", description="Make a new SystemInstance id for Karaf licence manager")
public class MakeSystemInstanceCommand extends OsgiCommandSupport {

	private LicenceService _licenceService;

	public LicenceService getLicenceService() {
		return _licenceService;
	}

	public void setLicenceService( LicenceService licenceService) {
		_licenceService = licenceService;
	} 


	@Override
	protected Object doExecute() throws Exception {
		String systemId = getLicenceService().makeSystemInstance();
		System.out.println("Made new Licence System Instance set to='"+systemId+"'");
		return null;
	}
}