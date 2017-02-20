package org.opennms.plugins.graphml.asset;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.graphdrawing.graphml.xmlns.GraphmlType;
import org.opennms.netmgt.events.api.EventIpcManager;
import org.opennms.netmgt.events.api.EventListener;
import org.opennms.netmgt.xml.event.Event;
import org.opennms.netmgt.xml.event.Parm;
import org.opennms.plugins.graphml.client.GraphMLRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class AssetPluginController  implements EventListener{

	public static final String ASSET_TOPOLOGY_FOLDER = "etc/assettopology/"; // folder created in OpenNMS to store asset topology before installation
	public static final String ASSET_TOPOLOGY_FILE = "AssetTopologyFile.xml"; 
	public static final String ASSET_LIST_FILE = "AssetListFile.txt"; // file generated for debugging
	public static final String ASSET_LIST_XML_FILE = "AssetListFile.xml"; // file generated for debugging
	public static final String ASSET_TOPOLOGY_NAME="assetTopology"; // topology name used when client creates topology

	//onms events usedc to generate, install and uninstall topologies
	public static final String CREATE_ASSET_TOPOLOGY = "uei.opennms.plugins/assettopology/create"; 
	public static final String INSTALL_ASSET_TOPOLOGY = "uei.opennms.plugins/assettopology/install"; 
	public static final String UNINSTALL_ASSET_TOPOLOGY = "uei.opennms.plugins/assettopology/uninstall"; 

	private static final Logger LOG = LoggerFactory.getLogger(AssetPluginController.class);

	private NodeInfoRepository nodeInfoRepository=null;

	private AssetTopologyMapper assetTopologyMapper=null;

	private GraphMLRestClient graphMLRestClient=null;

	private EventIpcManager eventIpcManager=null;

	private boolean writeAssetListDebugFile=false;

	public NodeInfoRepository getNodeInfoRepository() {
		return nodeInfoRepository;
	}

	public void setNodeInfoRepository(NodeInfoRepository nodeInfoRepository) {
		this.nodeInfoRepository = nodeInfoRepository;
	}

	public GraphMLRestClient getGraphMLRestClient() {
		return graphMLRestClient;
	}

	public void setGraphMLRestClient(GraphMLRestClient graphMLRestClient) {
		this.graphMLRestClient = graphMLRestClient;
	}

	public EventIpcManager getEventIpcManager() {
		return eventIpcManager;
	}

	public void setEventIpcManager(EventIpcManager eventIpcManager) {
		this.eventIpcManager = eventIpcManager;
	}


	public AssetTopologyMapper getAssetTopologyMapper() {
		return assetTopologyMapper;
	}


	public void setAssetTopologyMapper(AssetTopologyMapper assetTopologyMapper) {
		this.assetTopologyMapper = assetTopologyMapper;
	}

	public boolean getWriteAssetListDebugFile() {
		return writeAssetListDebugFile;
	}

	public void setWriteAssetListDebugFile(boolean writeAssetListDebugFile) {
		this.writeAssetListDebugFile = writeAssetListDebugFile;
	}

	public void init() {
		Assert.notNull(eventIpcManager, "eventIpcManager must not be null");
		Assert.notNull(nodeInfoRepository, "nodeInfoRepository must not be null");

		installMessageSelectors();

		Event event = new Event();
		event.setUei(CREATE_ASSET_TOPOLOGY);
		onEvent(event);

		LOG.info("Asset Topology Plugin registered for events");

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
		if(event==null) throw new RuntimeException("onEvent(event) event must not be null");
		if(graphMLRestClient==null) throw new RuntimeException("graphMLRestClient must not be null");

		if(CREATE_ASSET_TOPOLOGY.equals(event.getUei())){
			LOG.info("CREATE_ASSET_TOPOLOGY event received. Creating topology file from Node Database");

			nodeInfoRepository.initialiseNodeInfo();
			LOG.info("Asset Topology Plugin loaded node info ");

			if(writeAssetListDebugFile){
				Utils.writeFileToDisk(nodeInfoRepository.nodeInfoToString(), ASSET_LIST_FILE, ASSET_TOPOLOGY_FOLDER );

				Utils.writeFileToDisk(NodeInfoRepositoryXML.nodeInfoToXML(nodeInfoRepository.getNodeInfo()), ASSET_LIST_XML_FILE, ASSET_TOPOLOGY_FOLDER );
			}
			
			//create unique menu label for graph
			SimpleDateFormat ft = new SimpleDateFormat ("E dd.MM.yyyy_hh:mm:ss");
			String menuLabelStr = "Asset Topology "+ft.format(new Date());
			assetTopologyMapper.setMenuLabelStr(menuLabelStr);
			
			// create graphml from topology information
			GraphmlType graph = assetTopologyMapper.nodeInfoToTopology(nodeInfoRepository);

			String assetTopologyStr = Utils.graphmlTypeToString(graph);

			//writeTopologyFileToDisk(assetTopologyStr, ASSET_TOPOLOGY_FILE);

			Utils.writeFileToDisk(assetTopologyStr, ASSET_TOPOLOGY_FILE, ASSET_TOPOLOGY_FOLDER );


		} else if(INSTALL_ASSET_TOPOLOGY.equals(event.getUei())){
			Parm topologyFileNameParm=event.getParm("topologyFilename");
			String topologyFilename=ASSET_TOPOLOGY_FILE;
			if(topologyFileNameParm!=null){
				topologyFilename = topologyFileNameParm.getValue().getContent();
			}
			Parm topologyNameParm=event.getParm("topologyName");
			String topologyName=ASSET_TOPOLOGY_NAME;
			if(topologyNameParm!=null){
				topologyName = topologyNameParm.getValue().getContent();
			}
			LOG.info("INSTALL_ASSET_TOPOLOGY event received. Installing topology topologyFilename="+topologyFilename+" topologyName="+topologyName);
			try{
				
				GraphmlType graph = Utils.readTopologyFileFromDisk(ASSET_TOPOLOGY_FOLDER, topologyFilename);
				LOG.debug("topology to install topology:"+Utils.graphmlTypeToString(graph));

				//post file to opennms
				graphMLRestClient.createGraph(topologyName, graph);

			} catch (Exception e){
				LOG.error("INSTALL_ASSET_TOPOLOGY event received. unable to install topologyFilename="+topologyFilename+" topologyName="+topologyName, e);
			}

		}else if(UNINSTALL_ASSET_TOPOLOGY.equals(event.getUei())){
			Parm topologyNameParm=event.getParm("topologyName");
			String topologyName=ASSET_TOPOLOGY_NAME;
			if(topologyNameParm!=null){
				topologyName = topologyNameParm.getValue().getContent();
			}
			LOG.info("UNINSTALL_ASSET_TOPOLOGY event received. Uninstalling topologyName="+topologyName);
			try{
				graphMLRestClient.deleteGraph(topologyName);
			} catch (Exception e){
				LOG.error("UNINSTALL_ASSET_TOPOLOGY event received. unable to uninstall  topologyName="+topologyName, e);
			}
		}

	}


	@Override
	public String getName() {
		return "AssetTopologyPlugin";
	}


}
