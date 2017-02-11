package org.opennms.plugins.graphml.asset;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.graphdrawing.graphml.xmlns.GraphmlType;
import org.graphdrawing.graphml.xmlns.ObjectFactory;
import org.opennms.netmgt.events.api.EventIpcManager;
import org.opennms.netmgt.events.api.EventListener;
import org.opennms.netmgt.xml.event.Event;
import org.opennms.netmgt.xml.event.Parm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class AssetPluginController  implements EventListener{

	public static final String ASSET_TOPOLOGY_FOLDER = "etc/assettopology/";
	public static final String ASSET_TOPOLOGY_FILE = "AssetTopologyFile.xml"; //TODO WILL BE XML
	public static final String ASSET_TOPOLOGY_FILE_TMP = "AssetTopologyFile.txt"; //TODO WILL BE XML
	public static final String ASSET_TOPOLOGY_NAME="assetTopology";

	public static final String CREATE_ASSET_TOPOLOGY = "uei.opennms.plugins/assettopology/create"; 
	public static final String INSTALL_ASSET_TOPOLOGY = "uei.opennms.plugins/assettopology/install"; 
	public static final String UNINSTALL_ASSET_TOPOLOGY = "uei.opennms.plugins/assettopology/uninstall"; 

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

		Event event = new Event();
		event.setUei(CREATE_ASSET_TOPOLOGY);
		onEvent(event);

		LOG.info("Asset Topology Plugin registered for events");

	}

	private void writeTopologyFileToDisk(String topologyString, String topologyFilename){
		File topologyFolder = new File(ASSET_TOPOLOGY_FOLDER);
		File file = new File(topologyFolder, topologyFilename);
		LOG.info("writing to file:"+file.getAbsolutePath());
		try{
			topologyFolder.mkdirs();
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			writer.println(topologyString);
			writer.close();
		} catch (IOException e) {
			LOG.error("problem writing file:"+file.getAbsolutePath(),e);
		}
	}

	private String readTopologyFileFromDisk(String topologyFilename){
		File topologyFolder = new File(ASSET_TOPOLOGY_FOLDER);
		File graphmlfile = new File(topologyFolder, topologyFilename);
		LOG.info("reading from file:"+graphmlfile.getAbsolutePath());

		JAXBContext ctx;
		JAXBElement<GraphmlType> jaxbgraph=null;
		GraphmlType graph=null;
		try {
			//ctx = JAXBContext.newInstance("org.graphdrawing.graphml.xmlns");
			ctx = JAXBContext.newInstance(org.graphdrawing.graphml.xmlns.ObjectFactory.class);
			Unmarshaller jaxbUnmarshaller = ctx.createUnmarshaller();
			jaxbgraph =  (JAXBElement<GraphmlType>) jaxbUnmarshaller.unmarshal(graphmlfile);
			graph = jaxbgraph.getValue();
			return graphmlTypeToString(graph);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			LOG.error("problem reading file:"+graphmlfile.getAbsolutePath(),e);
			throw new RuntimeException("problem reading file:"+graphmlfile.getAbsolutePath(),e);
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
		if(event==null) throw new RuntimeException("onEvent(event) event must not be null");
		if(CREATE_ASSET_TOPOLOGY.equals(event.getUei())){
			LOG.info("CREATE_ASSET_TOPOLOGY event received. Creating topology file from Node Database");

			nodeInfoRepository.initialiseNodeInfo();
			LOG.info("Asset Topology Plugin loaded node info ");
			writeTopologyFileToDisk(nodeInfoRepository.nodeInfoToString(), ASSET_TOPOLOGY_FILE_TMP);

		}else if(INSTALL_ASSET_TOPOLOGY.equals(event.getUei())){
			Parm topFileNameParm=event.getParm("topologyFilename");
			String topologyFilename=ASSET_TOPOLOGY_FILE;
			if(topFileNameParm!=null){
				topologyFilename = topFileNameParm.getValue().getContent();
			}
			Parm topologyNameParm=event.getParm("topologyName");
			String topologyName=ASSET_TOPOLOGY_NAME;
			if(topologyNameParm!=null){
				topologyName = topologyNameParm.getValue().getContent();
			}
			LOG.info("INSTALL_ASSET_TOPOLOGY event received. Installing topology topologyFilename="+topologyFilename+" topologyName="+topologyName);
			try{
				String readFile = readTopologyFileFromDisk(topologyFilename);
				LOG.info("topology:"+readFile);
				//TODO push file to opennms
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

			//TODO read file from opennms

		}

	}


	@Override
	public String getName() {
		return "AssetTopologyPlugin";
	}


	public String graphmlTypeToString(GraphmlType graphmlType){
		try {

			// Marshal graphmlType
			ObjectFactory objectFactory = new ObjectFactory();
			JAXBElement<GraphmlType> je =  objectFactory.createGraphml(graphmlType);

			StringWriter sw = new StringWriter();
			//JAXBContext ctx = JAXBContext.newInstance("org.graphdrawing.graphml.xmlns");  // classloader issues
			JAXBContext ctx = JAXBContext.newInstance(org.graphdrawing.graphml.xmlns.ObjectFactory.class);

			Marshaller marshaller = ctx.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(je, sw);

			return sw.toString();
		} catch (Exception e) {
			LOG.error("graphmlTypeToString problem creating result", e);
		}
		return null;
	}


}
