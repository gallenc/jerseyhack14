package org.opennms.karaf.licencemgr.cmd;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.LicenceService;


@Command(scope = "licence-mgr", name = "setSystemInstance", description="Set Instance ID of Karaf licence manager")
public class SetSystemInstanceCommand extends OsgiCommandSupport {

	private LicenceService _licenceService;

	public LicenceService getLicenceService() {
		return _licenceService;
	}

	public void setLicenceService( LicenceService licenceService) {
		_licenceService = licenceService;
	} 

	@Argument(index = 0, name = "systemInstance", description = "System Instance of licence manager ", required = true, multiValued = false)
	String systemInstance = null;
	
	
	@Override
	protected Object doExecute() throws Exception {
		getLicenceService().setSystemId(systemInstance);
		System.out.println("Licence System Instance set to='"+getLicenceService().getSystemId()+"'");
		return null;
	}
}