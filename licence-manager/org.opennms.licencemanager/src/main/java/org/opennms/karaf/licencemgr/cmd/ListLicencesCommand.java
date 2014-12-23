package org.opennms.karaf.licencemgr.cmd;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.LicenceService;

@Command(scope = "licence-mgr", name = "list", description="lists installed licences")
public class ListLicencesCommand extends OsgiCommandSupport {

	private LicenceService _licenceService;

	public LicenceService getLicenceService() {
		return _licenceService;
	}

	public void setLicenceService( LicenceService licenceService) {
		_licenceService = licenceService;
	} 

	@Override
	protected Object doExecute() throws Exception {
		System.out.println("list of licences");

		Map<String, String> licenceMap = getLicenceService().getLicenceMap();
		for (Entry<String, String> entry : licenceMap.entrySet()){
			System.out.println("   productId='"+entry.getKey()+"' licence='"+entry.getValue()+"'");
		}
		return null;
	}


}