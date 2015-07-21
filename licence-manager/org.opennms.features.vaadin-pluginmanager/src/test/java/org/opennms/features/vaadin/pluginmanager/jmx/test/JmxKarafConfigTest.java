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


public class JmxKarafConfigTest {

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
			// connecting to config service
			// ***************************
			String LICENCE_MGR_CONFIG_PID="org.opennms.features.licencemgr.config";
			
			//create ConfigMBean proxy 
			System.out.println ("create ConfigMBean proxy" );
			ObjectName mbeanNameConfig = new ObjectName("org.apache.karaf:type=config,name=opennms");
			ConfigMBean osgiConfigMBeanProxy = JMX.newMBeanProxy(mbsc, mbeanNameConfig,	ConfigMBean.class, false);
			System.out.println ("ConfigMBean proxy created" );
			
			//check if config file exists
			List<String> configs = osgiConfigMBeanProxy.getConfigs();
			
			System.out.println("configs:");
			for (  String config: configs) {
				System.out.println("  "+config);
			}
			
			if (!configs.contains(LICENCE_MGR_CONFIG_PID)){
				osgiConfigMBeanProxy.create(LICENCE_MGR_CONFIG_PID);
			}

			System.out.println ("added new config" );
			
			System.out.println("configs:");
			for (  String config: configs) {
				System.out.println("  "+config);
			}
			
			
			osgiConfigMBeanProxy.setProperty(LICENCE_MGR_CONFIG_PID, "licenceServerURL","http:\\licence.opennms.org");
			osgiConfigMBeanProxy.setProperty(LICENCE_MGR_CONFIG_PID, "licenceServerUserName","licenceusername");
			osgiConfigMBeanProxy.setProperty(LICENCE_MGR_CONFIG_PID, "licenceServerPassword","licencepw");
			
			System.out.println(LICENCE_MGR_CONFIG_PID+" config:");
			Map<String, String> logProperties2 = osgiConfigMBeanProxy.listProperties(LICENCE_MGR_CONFIG_PID);
			System.out.println(logProperties2.size());
			for (  String key: logProperties2.keySet()) {
				System.out.println("  key:"+key+"   value:"+logProperties2.get(key));
			}
			
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
