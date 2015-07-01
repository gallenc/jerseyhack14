package org.opennms.features.vaadin.pluginmanager.jmx.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.karaf.features.management.FeaturesServiceMBean;
import org.apache.karaf.management.mbeans.bundles.BundlesMBean;
import org.junit.Test;
import org.osgi.jmx.framework.BundleStateMBean;
import org.osgi.jmx.framework.FrameworkMBean;

public class JmxKarafTest {

	// see http://www.talendforge.org/wiki/doku.php?id=how-to-karaf-jmx-remote-management
	@Test
	public void test() {
		System.out.println ("start of test" );
		HashMap<String, String[]> environment = new HashMap<String, String[]>();
		String username = "admin";
		String password = "admin";
		String[] credentials = new String[] { username, password };
		environment.put("jmx.remote.credentials", credentials);

		JMXServiceURL url;
		JMXConnector jmxc=null;;
		try {
			
			///from org.apache.karaf.management.cfg in OpenNMS
			//service:jmx:rmi://${rmiServerHost}:${rmiServerPort}/jndi/rmi://${rmiRegistryHost}:${rmiRegistryPort}/karaf-${karaf.name}
			// in system.properties karaf.name=opennms
			url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/karaf-opennms");
			jmxc = JMXConnectorFactory.connect(url, environment);
			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

			
			// We should create osgiFrameworkProxy using org.osgi.jmx.framework.FrameworkMBean 
			// class to be able to add start and stop bundles: 
			ObjectName mbeanNameFramework = new ObjectName("osgi.core:type=framework,version=1.5,name=opennms");
			FrameworkMBean osgiFrameworkProxy = JMX.newMBeanProxy(mbsc, mbeanNameFramework,	FrameworkMBean.class, false);

			//create bundleState proxy
			ObjectName mbeanNameBundleState = new ObjectName("osgi.core:type=bundleState,version=1.5,name=opennms");
			BundleStateMBean osgiBundleStateMBeanProxy = JMX.newMBeanProxy(mbsc, mbeanNameBundleState,	BundleStateMBean.class, false);

			//create BundlesMBean proxy proxy
//			ObjectName mbeanNameBundles = new ObjectName("org.apache.karaf:type=bundles,version=1.5,name=opennms");
//			BundlesMBean osgiBundlesMBeanProxy = JMX.newMBeanProxy(mbsc, mbeanNameBundles,	BundlesMBean.class, false);
//
//			TabularData bundles = osgiBundlesMBeanProxy.getBundles();
//			
//			System.out.println("bundles:");
//			for (  Object object : bundles.values()) {
//				CompositeData cd=(CompositeData) object;
//				System.out.println("  "+cd.get("ID")+"  "+cd.get("Name")+"  "+cd.get("Version"));
//			}
//			
			
			
			// We should create featuresServiceMBeanProxy using
			// org.apache.karaf.features.management.FeaturesServiceMBean to be
			// able to add repositories, install and uninstall features:

						
			ObjectName mbeanNameFeatures = new ObjectName("org.apache.karaf:type=features,name=opennms");
			
			
			FeaturesServiceMBean featuresServiceMBeanProxy = JMX.newMBeanProxy(
					mbsc, mbeanNameFeatures, FeaturesServiceMBean.class, true);
			
			TabularData features = featuresServiceMBeanProxy.getFeatures();
			
			System.out.println("features:");
			for (  Object object : features.values()) {
				CompositeData cd=(CompositeData) object;
				System.out.println("  "+cd.get("Name")+"  "+cd.get("Version"));
				
			}
			
			System.out.println("repositories:");
			TabularData repositories = featuresServiceMBeanProxy.getRepositories();
			for (  Object object : repositories.values()) {
				CompositeData cd=(CompositeData) object;
				System.out.println("  "+cd.get("Name")+"  "+cd.get("Uri") );
			}
			
			
			
			
			//List<JobFacade> result=new ArrayList<JobFacade>();
			  
			  //for (  Object object : features.values()) {
				  //Collection<CompositeData> compdata = (Collection<CompositeData>) object;
				  //System.out.println(compdata);
			  //  CompositeData cd=(CompositeData)object;
			    
//			    Map map= new HashMap();
//			    Collection<CompositeData> values=(Collection<CompositeData>)features.values();
//			    for (    CompositeData compositeData : values) {
//			      Object key=compositeData.get("key");
//			      Object value=compositeData.get("value");
//			      map.put(key,value);
//			    }
//
//			    System.out.println(map);

			 // }
			  //return result;

			// We should create osgiFrameworkProxy using
			// org.osgi.jmx.framework.FrameworkMBean class to be able to add
			// start and stop bundles:

			//ObjectName featuresMbeanName = new ObjectName(
			//		"osgi.core:type=framework,version=1.5");
			//FrameworkMBean osgiFrameworkProxy = JMX.newMBeanProxy(mbsc,
			//		features, FrameworkMBean.class, false);

			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedObjectNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		} finally{
			if (jmxc!=null)
				try {
					jmxc.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		System.out.println ("test finished" );
	}

}
