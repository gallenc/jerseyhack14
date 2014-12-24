package org.opennms.karaf.licencemgr.cmd;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.LicenceService;

@Command(scope = "licence-mgr", name = "checksumForString", description="creates and adds a checksum to a given string")
public class ChecksumForStringCommand extends OsgiCommandSupport {

	private LicenceService _licenceService;

	public LicenceService getLicenceService() {
		return _licenceService;
	}

	public void setLicenceService( LicenceService licenceService) {
		_licenceService = licenceService;
	}

	@Argument(index = 0, name = "string", description = "simple string to add checksum.", required = true, multiValued = false)
	String string = null;


	@Override
	protected Object doExecute() throws Exception {
		try{
			String stringPlusChecksum =  getLicenceService().checksumForString(string);
			System.out.println("string plus checksum='"+stringPlusChecksum+"'");
		} catch (Exception e) {
			System.out.println("Error Adding Checksum. Exception="+e);
		}

		return null;
	}
}