package org.opennms.plugins.graphml.asset;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.graphdrawing.graphml.xmlns.DataType;
import org.graphdrawing.graphml.xmlns.EdgeType;
import org.graphdrawing.graphml.xmlns.GraphEdgedefaultType;
import org.graphdrawing.graphml.xmlns.GraphType;
import org.graphdrawing.graphml.xmlns.KeyForType;
import org.graphdrawing.graphml.xmlns.KeyType;
import org.graphdrawing.graphml.xmlns.KeyTypeType;
import org.graphdrawing.graphml.xmlns.NodeType;
import org.graphdrawing.graphml.xmlns.GraphmlType;
import org.graphdrawing.graphml.xmlns.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssetTopologyMapperImpl implements AssetTopologyMapper {

	private static final Logger LOG = LoggerFactory.getLogger(AssetTopologyMapperImpl.class);

	private ObjectFactory of = new ObjectFactory();

	private static final String[] defaultHierarchy= {NodeParamLabels.ASSET_REGION,
		NodeParamLabels.ASSET_BUILDING,
		NodeParamLabels.ASSET_RACK};

	List<String> layerHierarchy  = Arrays.asList(defaultHierarchy);

	public List<String> getLayerHierarchy() {
		return layerHierarchy;
	}

	public void setLayerHierarchy(List<String> layerHierarchy) {
		this.layerHierarchy = layerHierarchy;
	}


	//TODO REMOVE
	public GraphmlType oldNodeInfoToTopology(NodeInfoRepository nodeInfoRepository) {

		//create unique menu label for graph
		SimpleDateFormat ft = new SimpleDateFormat ("E dd.MM.yyyy_hh:mm:ss");
		String menuLabelStr = "Asset Topology "+ft.format(new Date());
		GraphmlType graphmlType = createGraphML(menuLabelStr);

		GraphType graph = createGraphInGraphmlType(graphmlType, "all_nodes");

		Map<String, Map<String, String>> nodeInfo = nodeInfoRepository.getNodeInfo();
		addOpenNMSNodes(graph, nodeInfo);

		return graphmlType;
	}

	@Override
	public GraphmlType nodeInfoToTopology(NodeInfoRepository nodeInfoRepository) {

		//create unique menu label for graph
		SimpleDateFormat ft = new SimpleDateFormat ("E dd.MM.yyyy_hh:mm:ss");
		String menuLabelStr = "Asset Topology "+ft.format(new Date());

		// print log info for graph definition
		StringBuffer msg = new StringBuffer("creating topology "+menuLabelStr+" for layerHierarchy :");
		if(layerHierarchy.size()==0){
			msg.append("EMPTY");
		} else {
			for(String layer:layerHierarchy){
				msg.append(layer+",");
			}
		}
		LOG.info(msg.toString());

		GraphmlType graphmlType = createGraphML(menuLabelStr);

		Map<String, Map<String, String>> onmsNodeInfo = nodeInfoRepository.getNodeInfo();

		if (layerHierarchy.size()==0){
			//create simple graph with all nodes if layerHierarchy is empty
			if( LOG.isDebugEnabled()) LOG.debug("create simple graph with all nodes as layerHierarchy is empty");

			GraphType graph = createGraphInGraphmlType(graphmlType, "all_nodes");

			Map<String, Map<String, String>> nodeInfo = nodeInfoRepository.getNodeInfo();
			addOpenNMSNodes(graph, nodeInfo);

		} else {

			msg = new StringBuffer("create graphs from asset and layerHierarchy: ");
			// create graphs for all possible layers
			List<GraphType> graphList = new ArrayList<GraphType>();
			for(String graphname:layerHierarchy){
				GraphType graph = createGraphInGraphmlType(graphmlType, graphname);
				graphList.add(graph);
				msg.append(graphname+",");
			}
			if( LOG.isDebugEnabled()) LOG.debug(msg.toString());

			// create graph hierarchy according to asset table contents
			// used to store all nodes which have been allocated to a graph layer
			Map<String, Map<String, String>> allocatedNodeInfo = new LinkedHashMap<String, Map<String, String>>();

			// add layer graphs for defined layerHierarchy
			recursivelyAddlayers(layerHierarchy, 0,   onmsNodeInfo,  allocatedNodeInfo,  graphmlType, graphList);

			// add unallocated nodes into a default unallocated_Nodes graph
			Map<String, Map<String, String>> unAllocatedNodeInfo = new LinkedHashMap<String, Map<String, String>>();
			unAllocatedNodeInfo.putAll(onmsNodeInfo); //initialise with full list of nodes

			for (String allocatedNodeId:allocatedNodeInfo.keySet()){
				unAllocatedNodeInfo.remove(allocatedNodeId);
			}

			GraphType graph = createGraphInGraphmlType(graphmlType, "unallocated_Nodes");
			addOpenNMSNodes(graph, unAllocatedNodeInfo);
		}

		return graphmlType;
	}

	// returns list of nodes added in the next layer for use in edges
	private Map<String, Map<String, String>> recursivelyAddlayers(List<String> layerHierarchy, int layerHierarchyIndex,  Map<String, Map<String, String>> nodeInfo, Map<String, Map<String, String>> allocatedNodeInfo, GraphmlType graphmlType, List<GraphType> graphList){
		if(layerHierarchy==null||layerHierarchy.size()==0 ) throw new RuntimeException("AssetTopologyMapperImpl layerHierarchy must not be null or empty");

		// returns list of nodes added - either OpenNMS nodes or higher level graphs
		Map<String, Map<String, String>> addedNodes=null;

		if( LOG.isDebugEnabled()) LOG.debug("recursivelyAddlayers called for layerHierarchyIndex:"+layerHierarchyIndex);

		// add nodes to graph
		if(layerHierarchyIndex>=layerHierarchy.size()){
			// we are at bottom of hierarchy so add real opennms nodes and edges

			//get hierarchy name for this layer
			String layerNodeParamLabel= layerHierarchy.get(layerHierarchyIndex-1);

			// get graph for this layer
			if( LOG.isDebugEnabled()) LOG.debug("populating graph with OpenNMS nodes for layer="+layerNodeParamLabel);
			GraphType graph = graphList.get(layerHierarchyIndex-1);

			//add real opennms nodes to graph
			addOpenNMSNodes(graph, nodeInfo);

			// add these nodes to the allocated node set
			allocatedNodeInfo.putAll(nodeInfo);

			if (LOG.isDebugEnabled()) {
				StringBuffer msg= new StringBuffer("adding opennms nodes to graphId="+layerNodeParamLabel+ " nodes:");

				for (String targetNodeId:nodeInfo.keySet()){
					msg.append(targetNodeId+",");
				}
			}

			addedNodes=nodeInfo;

		} else {
			// else create and add supernodes for this layer
			if( LOG.isDebugEnabled()) LOG.debug("populating parent graph for index "+layerHierarchyIndex);

			//get hierarchy name for this layer
			String layerNodeParamLabelKey= layerHierarchy.get(layerHierarchyIndex);
			if( LOG.isDebugEnabled()) LOG.debug("parent graph name="+layerNodeParamLabelKey);

			// get graph for this layer
			GraphType graph = graphList.get(layerHierarchyIndex);

			// find all values corresponding to nodeParamLabelKey in this layer
			Set<String> layerNodeParamLabelValues = new TreeSet<String>();
			for (String nodeId: nodeInfo.keySet()){
				String nodeParamValue = nodeInfo.get(nodeId).get(layerNodeParamLabelKey);
				if(nodeParamValue!=null){
					layerNodeParamLabelValues.add(nodeParamValue);
				}
			}

			if (LOG.isDebugEnabled()){
				StringBuffer msg=new StringBuffer("values corresponding to layerNodeParamLabelKey="+layerNodeParamLabelKey+ " in this layer :");
				for (String nodeParamValue:layerNodeParamLabelValues){
					msg.append(nodeParamValue+",");
				}
				LOG.debug(msg.toString());
			}

			// create added nodes to return - with empty parameters if these are only layers and not OpenNMS nodes
			addedNodes = new LinkedHashMap<String, Map<String, String>>();

			// iterate over values in this layer
			for (String nodeParamLabelValue:layerNodeParamLabelValues){

				// create new node for each value in this layer
				NodeType node = createNodeType(nodeParamLabelValue);
				graph.getDataOrNodeOrEdge().add(node);
				StringBuffer msg=new StringBuffer("created node "+nodeParamLabelValue+ " in  graphId="+layerNodeParamLabelKey);

				// create sub list of nodes corresponding to param label 
				Map<String, Map<String, String>> nodeInfoSubList =createNodeInfoSubList(layerNodeParamLabelKey, nodeParamLabelValue, nodeInfo);

				// recursively add graphs and nodes until complete
				int nextLayerHierarchyIndex=layerHierarchyIndex+1;
				Map<String, Map<String, String>> nextLayerNodesAdded = recursivelyAddlayers(layerHierarchy, nextLayerHierarchyIndex, nodeInfoSubList, allocatedNodeInfo, graphmlType, graphList );

				// create edge for each node in returned nextLayerNodesAdded
				if (nextLayerHierarchyIndex<layerHierarchy.size()){
					// if not lowest layer then add edges pointing next layers
					msg.append("edges added for next graph layer: " );
					for (String targetNodeId:nextLayerNodesAdded.keySet()){
						Map<String, String> nodeParamaters = nextLayerNodesAdded.get(targetNodeId);
						String nodeLabelStr = nodeParamaters.get(layerHierarchy.get(nextLayerHierarchyIndex));
						EdgeType edge = addEdgeToGraph(graph, nodeParamLabelValue, nodeLabelStr);
						msg.append(edge.getId()+",");
						
						// create node for added nodes with dummy params
						Map<String, String> emptyParms= new HashMap<String, String>();
						addedNodes.put(targetNodeId, emptyParms);
					}
				} else {
					// if lowest layer then add node ids (i.e. opennms node label)
					msg.append("edges added for opennms nodes: " );
					for (String targetNodeId:nextLayerNodesAdded.keySet()){
						Map<String, String> nodeParamaters = nextLayerNodesAdded.get(targetNodeId);
						String nodeLabelStr = nodeParamaters.get(NodeParamLabels.NODE_NODELABEL);
						EdgeType edge = addEdgeToGraph(graph, nodeParamLabelValue, nodeLabelStr);
						msg.append(edge.getId()+",");
						
						// create node for added nodes with real params
						addedNodes.put(targetNodeId, nextLayerNodesAdded.get(targetNodeId));
					}
				}

				if( LOG.isDebugEnabled()){
					LOG.debug(msg.toString());
				}

			}

		}

		return addedNodes;
	}

	private Map<String, Map<String, String>> createNodeInfoSubList(String nodeParamLabelKey, String nodeParamValue, Map<String, Map<String, String>> nodeInfo){
		if(nodeParamLabelKey==null) throw new RuntimeException("createNodeInfoSubList nodeParamLabel cannot be null");
		if(nodeParamValue==null) throw new RuntimeException("createNodeInfoSubList nodeParamValue cannot be null");

		Map<String,Map<String,String>> nodeInfoSubList = new LinkedHashMap<String, Map<String, String>>();

		StringBuffer msg = new StringBuffer("creating NodeInfoSubList for nodeParamLabelKey:"+nodeParamLabelKey+" nodeParamValue:"+nodeParamValue+ " sublist nodeIds:");
		for (String nodeId:nodeInfo.keySet()){
			Map<String, String> nodeParams = nodeInfo.get(nodeId);
			if(nodeParamValue.equals(nodeParams.get(nodeParamLabelKey))){
				nodeInfoSubList.put(nodeId, nodeParams );
				msg.append(nodeId+",");
			}
		}
		if( LOG.isDebugEnabled()) LOG.debug(msg.toString());
		return nodeInfoSubList;
	}

	private EdgeType addEdgeToGraph(GraphType graph, String sourceIdStr, String targetIdStr){
		EdgeType edge = of.createEdgeType();
		String id =sourceIdStr+"_"+targetIdStr;
		edge.setId(id);
		edge.setSource(sourceIdStr);
		edge.setTarget(targetIdStr);
		if( LOG.isDebugEnabled()) LOG.debug("adding edge id="+id+ " to graph:"+graph.getId());
		graph.getDataOrNodeOrEdge().add(edge);
		return edge;
	}

	private NodeType createNodeType(String nodeId){
		NodeType node = of.createNodeType();

		node.setId(nodeId);
		DataType nodeLabel = of.createDataType();
		nodeLabel.setKey(GraphMLKeyNames.LABEL);
		nodeLabel.setContent(nodeId);
		node.getDataOrPort().add(nodeLabel);

		return node;
	}

	private GraphmlType createGraphML(String menuLabelStr){
		GraphmlType graphmltype = new GraphmlType();

		addKeyTypes(graphmltype);

		// <!-- shows up in the menu -->
		// <data key="label">Minimalistic GraphML Topology Provider</data>
		DataType menuLabel = of.createDataType();
		menuLabel.setKey(GraphMLKeyNames.LABEL);
		menuLabel.setContent(menuLabelStr);
		graphmltype.getGraphOrData().add(menuLabel);

		//<data key="breadcrumb-strategy">SHORTEST_PATH_TO_ROOT</data>
		DataType breadcrumbStrategy = of.createDataType();
		breadcrumbStrategy.setKey(GraphMLKeyNames.BREADCRUMB_STRATEGY);
		breadcrumbStrategy.setContent("SHORTEST_PATH_TO_ROOT");
		graphmltype.getGraphOrData().add(breadcrumbStrategy);

		return graphmltype;
	}

	private GraphmlType addKeyTypes(GraphmlType graphmlType) {

		// <key id="label" for="graphml" attr.name="label"
		// attr.type="string"></key>
		KeyType graphmlLabel = of.createKeyType();
		graphmlLabel.setAttrName(GraphMLKeyNames.LABEL);
		graphmlLabel.setAttrType(KeyTypeType.STRING);
		graphmlLabel.setId(GraphMLKeyNames.LABEL);
		graphmlLabel.setFor(KeyForType.GRAPHML);
		graphmlType.getKey().add(graphmlLabel);

		// <key id="breadcrumb-strategy" for="graphml" attr.name="breadcrumb-strategy" attr.type="string" />
		KeyType breadcrumbStrategy = of.createKeyType();
		breadcrumbStrategy.setAttrName(GraphMLKeyNames.BREADCRUMB_STRATEGY);
		breadcrumbStrategy.setAttrType(KeyTypeType.STRING);
		breadcrumbStrategy.setId(GraphMLKeyNames.BREADCRUMB_STRATEGY);
		breadcrumbStrategy.setFor(KeyForType.GRAPHML);
		graphmlType.getKey().add(breadcrumbStrategy);

		// <key id="label" for="graph" attr.name="label"
		// attr.type="string"></key>
		KeyType graphLabelKey = of.createKeyType();
		graphLabelKey.setAttrName(GraphMLKeyNames.LABEL);
		graphLabelKey.setAttrType(KeyTypeType.STRING);
		graphLabelKey.setId(GraphMLKeyNames.LABEL);
		graphLabelKey.setFor(KeyForType.GRAPH);
		graphmlType.getKey().add(graphLabelKey);

		// <key id="description" for="graph" attr.name="description"
		// attr.type="string"></key>
		KeyType graphDescriptionKey = of.createKeyType();
		graphDescriptionKey.setAttrName(GraphMLKeyNames.DESCRIPTION);
		graphDescriptionKey.setAttrType(KeyTypeType.STRING);
		graphDescriptionKey.setId(GraphMLKeyNames.DESCRIPTION);
		graphDescriptionKey.setFor(KeyForType.GRAPH);
		graphmlType.getKey().add(graphDescriptionKey);

		// <key id="namespace" for="graph" attr.name="namespace"
		// attr.type="string"></key>
		KeyType graphNamespacekey = of.createKeyType();
		graphNamespacekey.setAttrName(GraphMLKeyNames.NAMESPACE);
		graphNamespacekey.setAttrType(KeyTypeType.STRING);
		graphNamespacekey.setId(GraphMLKeyNames.NAMESPACE);
		graphNamespacekey.setFor(KeyForType.GRAPH);
		graphmlType.getKey().add(graphNamespacekey);

		// <key id="preferred-layout" for="graph" attr.name="preferred-layout"
		// attr.type="string"></key>
		KeyType preferredLayoutKey = of.createKeyType();
		preferredLayoutKey.setAttrName(GraphMLKeyNames.PREFERRED_LAYOUT);
		preferredLayoutKey.setAttrType(KeyTypeType.STRING);
		preferredLayoutKey.setId(GraphMLKeyNames.PREFERRED_LAYOUT);
		preferredLayoutKey.setFor(KeyForType.GRAPH);
		graphmlType.getKey().add(preferredLayoutKey);

		// <key id="focus-strategy" for="graph" attr.name="focus-strategy"
		// attr.type="string"></key>
		KeyType focusStrategyKey = of.createKeyType();
		focusStrategyKey.setAttrName(GraphMLKeyNames.FOCUS_STRATEGY);
		focusStrategyKey.setAttrType(KeyTypeType.STRING);
		focusStrategyKey.setId(GraphMLKeyNames.FOCUS_STRATEGY);
		focusStrategyKey.setFor(KeyForType.GRAPH);
		graphmlType.getKey().add(focusStrategyKey);

		// <key id="focus-ids" for="graph" attr.name="focus-ids"
		// attr.type="string"></key>
		KeyType focusIdsKey = of.createKeyType();
		focusIdsKey.setAttrName(GraphMLKeyNames.FOCUS_IDS);
		focusIdsKey.setAttrType(KeyTypeType.STRING);
		focusIdsKey.setId(GraphMLKeyNames.FOCUS_IDS);
		focusIdsKey.setFor(KeyForType.GRAPH);
		graphmlType.getKey().add(focusIdsKey);

		// <key id="semantic-zoom-level" for="graph"
		// attr.name="semantic-zoom-level" attr.type="int"/>
		KeyType semanticZoomlevelKey = of.createKeyType();
		semanticZoomlevelKey.setAttrName(GraphMLKeyNames.SEMANTIC_ZOOM_LEVEL);
		semanticZoomlevelKey.setAttrType(KeyTypeType.INT);
		semanticZoomlevelKey.setId(GraphMLKeyNames.SEMANTIC_ZOOM_LEVEL);
		semanticZoomlevelKey.setFor(KeyForType.GRAPH);
		graphmlType.getKey().add(semanticZoomlevelKey);

		// <key id="label" for="node" attr.name="label"
		// attr.type="string"></key>
		KeyType nodelabelKey = of.createKeyType();
		nodelabelKey.setAttrName(GraphMLKeyNames.LABEL);
		nodelabelKey.setAttrType(KeyTypeType.STRING);
		nodelabelKey.setId(GraphMLKeyNames.LABEL);
		nodelabelKey.setFor(KeyForType.NODE);
		graphmlType.getKey().add(nodelabelKey);

		// key id="foreignSource" for="node" attr.name="foreignSource"
		// attr.type="string"></key>
		KeyType foreignSourcekey = of.createKeyType();
		foreignSourcekey.setAttrName(GraphMLKeyNames.FOREIGN_SOURCE);
		foreignSourcekey.setAttrType(KeyTypeType.STRING);
		foreignSourcekey.setId(GraphMLKeyNames.FOREIGN_SOURCE);
		foreignSourcekey.setFor(KeyForType.NODE);
		graphmlType.getKey().add(foreignSourcekey);

		// <key id="foreignID" for="node" attr.name="foreignID"
		// attr.type="string"></key>
		KeyType foreignIDkey = of.createKeyType();
		foreignIDkey.setAttrName(GraphMLKeyNames.FOREIGN_ID);
		foreignIDkey.setAttrType(KeyTypeType.STRING);
		foreignIDkey.setId(GraphMLKeyNames.FOREIGN_ID);
		foreignIDkey.setFor(KeyForType.NODE);
		graphmlType.getKey().add(foreignIDkey);

		// <key id="nodeID" for="node" attr.name="nodeID" attr.type="int"></key>
		KeyType nodeIdKey = new KeyType();
		nodeIdKey.setAttrName(GraphMLKeyNames.NODE_ID);
		nodeIdKey.setAttrType(KeyTypeType.INT);
		nodeIdKey.setId(GraphMLKeyNames.NODE_ID);
		nodeIdKey.setFor(KeyForType.NODE);
		graphmlType.getKey().add(nodeIdKey);

		return graphmlType;

	}


	private GraphType createGraphInGraphmlType(GraphmlType graphmlType, String graphId) {

		// <graph id="graph1">
		GraphType graph = of.createGraphType();
		graph.setId(graphId);
		graph.setEdgedefault(GraphEdgedefaultType.UNDIRECTED);
		graphmlType.getGraphOrData().add(graph);

		// <data key="namespace">minimalistic</data>
		DataType namespace = of.createDataType();
		namespace.setKey(GraphMLKeyNames.NAMESPACE);
		namespace.setContent(graphId+"_ns");
		graph.getDataOrNodeOrEdge().add(namespace);

		// <data key="focus-strategy">ALL</data>
		DataType focusStrategy = of.createDataType();
		focusStrategy.setKey(GraphMLKeyNames.FOCUS_STRATEGY);
		focusStrategy.setContent("ALL");
		graph.getDataOrNodeOrEdge().add(focusStrategy);

		return graph;
	}


	private void addOpenNMSNodes(GraphType graph, Map<String, Map<String, String>> nodeInfo) {

		for (String nodeId:nodeInfo.keySet()){

			NodeType node = of.createNodeType();
			graph.getDataOrNodeOrEdge().add(node);

			Map<String, String> nodeParamaters = nodeInfo.get(nodeId);
			String foreignSourceStr= nodeParamaters.get(NodeParamLabels.NODE_FOREIGNSOURCE);
			String foreignIdStr= nodeParamaters.get(NodeParamLabels.NODE_FOREIGNID);

			if (foreignIdStr==null
					|| "".equals(foreignIdStr) 
					|| foreignSourceStr==null 
					|| "".equals(foreignSourceStr)){
				// <data key="nodeID">1</data>
				DataType nodeID = of.createDataType();
				nodeID.setKey(GraphMLKeyNames.NODE_ID);
				nodeID.setContent(nodeId);
				node.getDataOrPort().add(nodeID);
			} else {
				// <data key="foreignSource">opennms-test</data>
				DataType foreignSource = of.createDataType();
				foreignSource.setKey(GraphMLKeyNames.FOREIGN_SOURCE);
				foreignSource.setContent(foreignSourceStr);
				node.getDataOrPort().add(foreignSource);

				// <data key="foreignID">1483632606160</data>
				DataType foreignID = of.createDataType();
				foreignID.setKey(GraphMLKeyNames.FOREIGN_ID);
				foreignID.setContent(foreignIdStr);
				node.getDataOrPort().add(foreignID);
			}

			String nodeLabelStr = nodeParamaters.get(NodeParamLabels.NODE_NODELABEL);
			node.setId(nodeLabelStr);

			DataType nodeLabel = of.createDataType();
			nodeLabel.setKey(GraphMLKeyNames.LABEL);
			nodeLabel.setContent(nodeLabelStr);
			node.getDataOrPort().add(nodeLabel);

		}


	}

}
