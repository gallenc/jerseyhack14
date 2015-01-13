
package org.opennms.karaf.licencemgr.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;
import org.opennms.karaf.licencemgr.rest.LicencePublisherRest;
import org.opennms.karaf.licencemgr.rest.LicenceServiceRest;
import org.opennms.karaf.licencemgr.rest.ProductPublisherRest;
import org.opennms.karaf.licencemgr.rest.ProductRegisterRest;

public class LicenceManagerRestApplication extends Application {

	// doing this because the com.sun.ws.rest.api.core.PackagesResourceConfig 
	// class contains OSGi unfriendly classloader code
	@Override
	public Set<Class<?>> getClasses()
	{
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(LicenceServiceRest.class);
		s.add(LicencePublisherRest.class);
		s.add(ProductPublisherRest.class);
		s.add(ProductRegisterRest.class);
		return s;
	}

	// Enable LoggingFilter & output entity.   
	// only for java logging - no use :(
	// registerInstances(new LoggingFilter(Logger.getLogger(EventGatewayApplication.class.getName()), true));
	//	}

}
