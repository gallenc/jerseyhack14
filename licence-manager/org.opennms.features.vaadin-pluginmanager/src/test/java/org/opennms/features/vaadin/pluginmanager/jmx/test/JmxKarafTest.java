package org.opennms.features.vaadin.pluginmanager.jmx.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
import org.apache.karaf.management.mbeans.config.ConfigMBean;
import org.osgi.jmx.framework.BundleStateMBean;
import org.osgi.jmx.framework.FrameworkMBean;
import org.junit.Test;


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

			String urlStr="service:jmx:rmi:///jndi/rmi://localhost:1099/karaf-opennms";
			
			System.out.println ("MbeanServerConnection connect to "+urlStr );
			url = new JMXServiceURL(urlStr);
			jmxc = JMXConnectorFactory.connect(url, environment);
			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
			System.out.println ("MbeanServerConnection connected" );
			
			// ***************************
			// creating osgi.core beans
			// ***************************
			
			
			// We should create osgiFrameworkProxy using org.osgi.jmx.framework.FrameworkMBean 
			// class to be able to add start and stop bundles: 
			System.out.println ("create osgiFrameworkProxy" );
			ObjectName mbeanNameFramework = new ObjectName("osgi.core:type=framework,version=1.5,name=opennms");
			FrameworkMBean osgiFrameworkProxy = JMX.newMBeanProxy(mbsc, mbeanNameFramework,	FrameworkMBean.class, false);
			System.out.println ("osgiFrameworkProxy created" );
			System.out.println ("mbeanNameFramework.getKeyPropertyListString():"+mbeanNameFramework.getKeyPropertyListString());

			// note bug - only works if uuid doesnt change 
			// https://issues.apache.org/jira/browse/ARIES-1056 Can't access MBeans by using the specified object name constants everything works fine. 
			// But only as long as the UUID of the MBean doesn't change
			//create bundleState proxy
//			System.out.println ("create bundleState proxy" );
//			ObjectName mbeanNameBundleState = new ObjectName("osgi.core:type=bundleState,version=1.7,framework=org.apache.felix.framework,uuid=cf457582-784c-486b-9106-baa4ca4a096b");
//			BundleStateMBean osgiBundleStateMBeanProxy = JMX.newMBeanProxy(mbsc, mbeanNameBundleState,	BundleStateMBean.class, false);
//			System.out.println ("bundleState proxy created" );
//			TabularData bundles = osgiBundleStateMBeanProxy.listBundles();
//			System.out.println("bundles:");
//			for (  Object object : bundles.values()) {
//				CompositeData cd=(CompositeData) object;
//				System.out.println("  "+cd.get("Identifier")+"  "+cd.get("SymbolicName")+"  "+cd.get("Version")+"   "+cd.get("State"));
//			}

			// *******************************
			// creating org.apache.karaf beans
			// *******************************

			
			//create BundlesMBean proxy 
			System.out.println ("create BundlesMBean proxy" );
			ObjectName mbeanNameBundles = new ObjectName("org.apache.karaf:type=bundles,name=opennms");
			BundlesMBean osgiBundlesMBeanProxy = JMX.newMBeanProxy(mbsc, mbeanNameBundles,	BundlesMBean.class, false);
			System.out.println ("BundlesMBean proxy created" );

			TabularData bundles2 = osgiBundlesMBeanProxy.getBundles();
			System.out.println("bundles:");
			for (  Object object : bundles2.values()) {
				CompositeData cd=(CompositeData) object;
				System.out.println("  "+cd.get("ID")+"  "+cd.get("Name")+"  "+cd.get("Version")+"  "+cd.get("State"));
			}
			
			//create ConfigMBean proxy 
			System.out.println ("create ConfigMBean proxy" );
			ObjectName mbeanNameConfig = new ObjectName("org.apache.karaf:type=config,name=opennms");
			ConfigMBean osgiConfigMBeanProxy = JMX.newMBeanProxy(mbsc, mbeanNameConfig,	ConfigMBean.class, false);
			System.out.println ("ConfigMBean proxy created" );

			List<String> configs = osgiConfigMBeanProxy.getConfigs();
			System.out.println("configs:");
			for (  String config: configs) {
				System.out.println("  "+config);
			}
			System.out.println("org.apache.karaf.log config:");
			Map<String, String> logProperties = osgiConfigMBeanProxy.listProperties("org.apache.karaf.log");
			for (  String key: logProperties.keySet()) {
				System.out.println("  key:"+key+"   value:"+logProperties.get(key));
			}
			
			// We should create featuresServiceMBeanProxy using
			// org.apache.karaf.features.management.FeaturesServiceMBean to be
			// able to add repositories, install and uninstall features:

			System.out.println ("create featuresServiceMBeanProxy" );
			ObjectName mbeanNameFeatures = new ObjectName("org.apache.karaf:type=features,name=opennms");
			FeaturesServiceMBean featuresServiceMBeanProxy = JMX.newMBeanProxy(
					mbsc, mbeanNameFeatures, FeaturesServiceMBean.class, true);
			System.out.println ("featuresServiceMBeanProxy created" );
			
			TabularData features = featuresServiceMBeanProxy.getFeatures();
			
			System.out.println("features:");
			for (  Object object : features.values()) {
				CompositeData cd=(CompositeData) object;
				System.out.println("  "+cd.get("Name")+"  "+cd.get("Version")+"  "+cd.get("Installed"));
				
			}
			System.out.println("info for feature: opennms-topology-runtime-simple");
			TabularData infoFeature = featuresServiceMBeanProxy.infoFeature("opennms-topology-runtime-simple");
			for (  Object object : infoFeature.values()) {
				CompositeData cd=(CompositeData) object;
				System.out.println("  "+cd.get("Name")+"  "+cd.get("Version")+"  "+cd.get("Installed")+"  "+cd.get("Configuration Files"));
			}

			TabularData repositories = featuresServiceMBeanProxy.getRepositories();
			System.out.println("repositories:");
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
