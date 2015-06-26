package org.opennms.features.eventgateway;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;

import org.opennms.core.utils.ConfigFileConstants;
import org.opennms.netmgt.events.api.EventProxy;
import org.opennms.netmgt.model.events.EventUtils;
import org.opennms.netmgt.events.api.annotations.EventHandler;
import org.opennms.netmgt.events.api.annotations.EventListener;
import org.opennms.netmgt.xml.event.Event;
import org.opennms.features.eventgateway.jaxb.EventGatewayAuthKeyConfig;
import org.opennms.features.eventgateway.jaxb.EventGatewayConfig;
import org.opennms.features.eventgateway.jaxb.EventGatewayConfigurations;

@EventListener(name="ConfigLoader" , logPrefix="EventGatewayConfigLoader")
public class ConfigLoader {

	// used to prevent configuration being updated while being read
	private static final Object LOCK_1 = new Object() {};

	// these variables are static because it is the only way I can see to pass configuration into the jersey implementation from OSGi

	// The OpenNMS Event Proxy.
	private static EventProxy _eventProxy;

	// the configuration object for the gateway
	// this is read on every new message and so can be updated between received messages
	private static EventGatewayConfigObject _gatewayConfigObject =null;
	

	/**
	 * Constructor
	 * @param eventProxy the OpenNMS event proxy
	 */
	public ConfigLoader(EventProxy eventProxy) {

		//TODO DEBUG REMOVE
		System.out.println("DEBUG testing config loader log is printed");

		if (eventProxy == null)
			throw new RuntimeException("eventProxy cannot be null.");
		_eventProxy=eventProxy;

		// load gateway configuration file if not already loaded
		if (_gatewayConfigObject==null) synchronized(LOCK_1) { 
			if (_gatewayConfigObject==null) {
				loadEventGatewayConfigurations();
			}
		}
	}

	/**
	 * Loads event gateway configuration from configuration file
	 * <opennms-home>/etc/EventGatewayConfigurations.xml
	 */
	private void loadEventGatewayConfigurations(){

		// object to hold the configuration
		EventGatewayConfigurations eventGatewayConfigurations=null;

		try { 
			File configFile= new File(ConfigFileConstants.getHome() + "/etc/EventGatewayConfigurations.xml");
			if (!configFile.exists()){
				throw new FileNotFoundException("event gateway configuration file '"+configFile.getAbsolutePath()+"' does not exist.");
			}

			// get the class loader for the bundle
			ClassLoader cl = org.opennms.features.eventgateway.jaxb.ObjectFactory.class.getClassLoader();
			JAXBContext jaxbContext = JAXBContext.newInstance("org.opennms.features.eventgateway.jaxb",cl);
			Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
			Object o = jaxbUnMarshaller.unmarshal( configFile  );

			if (o instanceof EventGatewayConfigurations){
				eventGatewayConfigurations=(EventGatewayConfigurations) o;
			} else throw new UnmarshalException("cant unmarshal EventGatewayConfigurations object from file:"+configFile.getAbsolutePath());

			EventGatewayConfigObject gwConfigObject= new EventGatewayConfigObject(eventGatewayConfigurations);

			// configuration cannot be read while being updated
			synchronized(LOCK_1) { 
				_gatewayConfigObject = gwConfigObject;
			}
		}   catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Event handler for updating the configuration. It listens for 
	 * event uei.opennms.org/internal/reloadDaemonConfig and then checks if the
	 * event param daemonName is set to 'EventGateway'
	 * 
	 * @param e OpenNMS event
	 */
	@EventHandler(uei = "uei.opennms.org/internal/reloadDaemonConfig")
	public void handleReloadEvent(Event e) {
		//LOG.info("Received reload configuration event: {}", e);
		//TODO DEBUG
		System.out.println("DEBUG event gateway Received reload configuration event: {}"+ e);

		String daemonName = EventUtils.getParm(e, "daemonName");
		if ("EventGateway".equalsIgnoreCase(daemonName)) {
			loadEventGatewayConfigurations();
			//TODO DEBUG
			System.out.println("DEBUG event gateway reloaded configuration");
		}
	}

	/**
	 * 
	 * @return the OpenNMS event proxy 
	 */
	public static EventProxy getEventProxy() { 
			return _eventProxy;
	}

	/**
	 * @return the gateway configuration object. 
	 * Note that this object defines a given configuration and is immutable. However it may be replaced between calls
	 * to getGatewayConfigObject() if the configuration of the Event Gateway changes
	 */
	public static EventGatewayConfigObject getGatewayConfigObject() {
		synchronized(LOCK_1) { 
			return _gatewayConfigObject;
		}
	}

	public static void setGatewayConfigObject(EventGatewayConfigObject gatewayConfigObject) {
		synchronized(LOCK_1) { 
			ConfigLoader._gatewayConfigObject = gatewayConfigObject;
		}
	}

	/**
	 * Immutable class which contains the configuration for the Event Gateway at a given point in time
	 * @author Craig Gallen cgallen@opennms.org
	 *
	 */
	public class  EventGatewayConfigObject {

		private final EventGatewayConfigurations _eventGatewayConfigurations;

		private final HashMap<String,EventGatewayConfig> _configMap;
		
		private final HashMap<String,AutoAuthKeyGenerator> _authenticatorMap;

		/**
		 * Constructor for configuration object
		 * @param eventGatewayConfigurations
		 */
		public EventGatewayConfigObject(EventGatewayConfigurations eventGatewayConfigurations){
			_eventGatewayConfigurations=eventGatewayConfigurations;
			
			_configMap= new HashMap<String, EventGatewayConfig>();
			
			_authenticatorMap=new HashMap<String,AutoAuthKeyGenerator>();
			
			// set up configurations for rest event gateways
			List<EventGatewayConfig> eventGatewayConfigsList =_eventGatewayConfigurations.getEventGatewayConfigsList();
			for (EventGatewayConfig config: eventGatewayConfigsList){
				_configMap.put(config.getReference(), config);
			}

			// set up separate authkey authenticators for each gateway
			// where they are specified
			for (EventGatewayConfig config: eventGatewayConfigsList){
				EventGatewayAuthKeyConfig eventGatewayAuthKeyConfig = config.getAuthKeyConfig();
				if(eventGatewayAuthKeyConfig!=null){
					
					AutoAuthKeyGenerator autoAuthKeyGenerator= new AutoAuthKeyGenerator();
					autoAuthKeyGenerator.setAuthKeylength(eventGatewayAuthKeyConfig.getAuthKeylength());
					autoAuthKeyGenerator.setMaxCurrentAuthKeyAge(eventGatewayAuthKeyConfig.getMaxCurrentAuthKeyAge());
					autoAuthKeyGenerator.setMaxTimeKeepOldAuthKeys(eventGatewayAuthKeyConfig.getMaxTimeKeepOldAuthKeys());
					
					// must create a new key when autoAuthKeyGenerator first configured
					autoAuthKeyGenerator.createNewAuthKey();

					_authenticatorMap.put(config.getReference(), autoAuthKeyGenerator);
				}

			}
		}


		/**
		 * HashMap of event gateway configurations stored by gateway reference
		 * @return _configMap
		 */
		public HashMap<String, EventGatewayConfig> getConfigMap() {
			return _configMap;
		}
		
		/**
		 * HashMap of authenticators stored by gateway reference
		 * @return the _authenticatorMap
		 */
		public HashMap<String, AutoAuthKeyGenerator> getAuthenticatorMap() {
			return _authenticatorMap;
		}

	};


}
