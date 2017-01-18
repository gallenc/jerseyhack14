package org.opennms.plugins.graphml.asset;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.opennms.netmgt.events.api.EventIpcManager;
import org.opennms.netmgt.events.api.EventListener;
import org.opennms.netmgt.xml.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class AssetPluginController  implements EventListener{
	
	public static final String ASSET_TOPOLOGY_FOLDER = "etc/assettopology/";
	public static final String ASSET_TOPOLOGY_FILE = "testTopologyFile1.txt";

	private static final Logger LOG = LoggerFactory.getLogger(AssetPluginController.class);

	private NodeInfoRepository nodeInfoRepository=null;
	
	private EventIpcManager eventIpcManager=null;
	
	public NodeInfoRepository getNodeInfoRepository() {
		return nodeInfoRepository;
	}

	public void setNodeInfoRepository(NodeInfoRepository nodeInfoRepository) {
		this.nodeInfoRepository = nodeInfoRepository;
	}

	public EventIpcManager getEventIpcManager() {
		return eventIpcManager;
	}

	public void setEventIpcManager(EventIpcManager eventIpcManager) {
		this.eventIpcManager = eventIpcManager;
	}

	public void init() {
		Assert.notNull(eventIpcManager, "eventIpcManager must not be null");
		Assert.notNull(nodeInfoRepository, "nodeInfoRepository must not be null");
		
		installMessageSelectors();
		LOG.info("Asset Topology Plugin registered for events");
	
		nodeInfoRepository.initialiseNodeInfo();
		LOG.info("Asset Topology Plugin loaded node info ");
		writeFileToDisk(nodeInfoRepository.nodeInfoToString());
			
	}
	
	public void writeFileToDisk(String s){
		File topologyFolder = new File(ASSET_TOPOLOGY_FOLDER);
		File file = new File(topologyFolder, ASSET_TOPOLOGY_FILE);
		LOG.info("writing to file:"+file.getAbsolutePath());
		try{
			topologyFolder.mkdirs();
		    PrintWriter writer = new PrintWriter(file, "UTF-8");
		    writer.println(s);
		    writer.close();
		} catch (IOException e) {
		   LOG.error("problem writing file:"+file.getAbsolutePath(),e);
		}
	}

	private void installMessageSelectors() {
		getEventIpcManager().addEventListener(this);
	}

	public void destroy() {
		uninstallMessageSelectors();
		LOG.info("Asset Topology Plugin unregisted for events");
	}

	private void uninstallMessageSelectors() {
		if (eventIpcManager!=null)
			eventIpcManager.removeEventListener(this);
	}

	/**
	 * {@inheritDoc}
	 *
	 * This method is invoked by the JMS topic session when a new event is
	 * available for processing. Currently only text based messages are
	 * processed by this callback. Each message is examined for its Universal
	 * Event Identifier and the appropriate action is taking based on each
	 * UEI.
	 */
	@Override
	public void onEvent(final Event event) {

		//eventForwarder.sendNow(event);
	}


	@Override
	public String getName() {
		return "AssetTopologyPlugin";
	}
	
	
}
