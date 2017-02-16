package org.opennms.plugins.graphml.asset;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
		StringBuffer s = new StringBuffer("creating topology "+menuLabelStr+" for layerHierarchy :");
		if(layerHierarchy.size()==0){
			s.append("EMPTY");
		} else {
			for(String layer:layerHierarchy){
				s.append(layer+",");
			}
		}
		LOG.info(s.toString());

		GraphmlType graphmlType = createGraphML(menuLabelStr);

		Map<String, Map<String, String>> onmsNodeInfo = nodeInfoRepository.getNodeInfo();

		if (layerHierarchy.size()==0){
			//create simple graph with all nodes if layerHierarchy is empty
			GraphType graph = createGraphInGraphmlType(graphmlType, "all_nodes");

			Map<String, Map<String, String>> nodeInfo = nodeInfoRepository.getNodeInfo();
			addOpenNMSNodes(graph, nodeInfo);

		} else {
			
			// create graphs for all possible layers
			List<GraphType> graphList = new ArrayList<GraphType>();
			for(String graphname:layerHierarchy){
				GraphType graph = createGraphInGraphmlType(graphmlType, graphname);
				graphList.add(graph);
			}
			
			// create graph heirarchy according to asset table contents

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

	private void recursivelyAddlayers(List<String> layerHierarchy, int layerHierarchyIndex,  Map<String, Map<String, String>> nodeInfo, Map<String, Map<String, String>> allocatedNodeInfo, GraphmlType graphmlType, List<GraphType> graphList){
		if(layerHierarchy==null||layerHierarchy.size()==0 ) throw new RuntimeException("AssetTopologyMapperImpl layerHierarchy must not be null or empty");

		String layerNodeParamLabel= layerHierarchy.get(layerHierarchyIndex);

		// get graph for this layer
		if( LOG.isDebugEnabled()) LOG.debug("populating graph for index "+layerHierarchyIndex+ " name="+layerNodeParamLabel);
		GraphType graph = graphList.get(layerHierarchyIndex);

		// add nodes to graph
		if(layerHierarchyIndex>=layerHierarchy.size()-1){
			//add real opennms nodes to graph
			addOpenNMSNodes(graph, nodeInfo);
			// add these nodes to the allocated node set
			allocatedNodeInfo.putAll(nodeInfo);

			if( LOG.isDebugEnabled()){
				StringBuffer s= new StringBuffer("adding OpenNMS nodes to graphId="+layerNodeParamLabel+ " nodes:");
				for(String nodeId:nodeInfo.keySet()){
					s.append(nodeId+",");
				}
				LOG.debug(s.toString());
			}

		} else {
			// else create and add supernodes for this layer
			// find all values corresponding to nodeParamLabel in this layer
			Set<String> layerNodeParamLabelValues = new TreeSet<String>();
			for (String nodeId: nodeInfo.keySet()){
				String nodeParamLabel = nodeInfo.get(nodeId).get(layerNodeParamLabel);
				if(nodeParamLabel!=null)layerNodeParamLabelValues.add(nodeParamLabel);
			}

			// iterate over values in this layer
			for (String nodeParamLabel:layerNodeParamLabelValues){

				// create new node for each value in this layer
				NodeType node = createNodeType(nodeParamLabel);
				graph.getDataOrNodeOrEdge().add(node);

				// create sub list of nodes corresponding to param label
				Map<String, Map<String, String>> nodeInfoSubList = createNodeInfoSubList(nodeParamLabel, nodeInfo);

				if( LOG.isDebugEnabled()){
					LOG.debug("creating node "+nodeParamLabel+ " in  graphId="+layerNodeParamLabel);
					StringBuffer s= new StringBuffer("adding edges to graphId="+nodeParamLabel+ " edges:");
					for(String targetNodeId:nodeInfoSubList.keySet()){
						s.append( nodeParamLabel+"_"+targetNodeId+",");
					}
					LOG.debug(s.toString());
				}

				// create edge for each node in sub list
				for (String targetNodeId:nodeInfoSubList.keySet()){
					addEdgeToGraph(graph, nodeParamLabel, targetNodeId);
				}

				// recursively add graphs and nodes until complete
				recursivelyAddlayers(layerHierarchy, layerHierarchyIndex++, nodeInfoSubList, allocatedNodeInfo, graphmlType, graphList );
			}

		}
	}

	private Map<String, Map<String, String>> createNodeInfoSubList(String nodeParamLabel, Map<String, Map<String, String>> nodeInfo){

		Map<String,Map<String,String>> nodeInfoSubList = new LinkedHashMap<String, Map<String, String>>();

		for (String nodeId:nodeInfo.keySet()){
			Map<String, String> nodeParams = nodeInfo.get(nodeId);
			if(nodeParams.containsKey(nodeParamLabel)){
				nodeInfoSubList.put(nodeId, nodeParams );
			}
		}
		return nodeInfoSubList;
	}

	private void addEdgeToGraph(GraphType graph, String sourceIdStr, String targetIdStr){
		EdgeType edge = of.createEdgeType();
		String id =sourceIdStr+"_"+targetIdStr;
		edge.setId(id);
		edge.setSource(sourceIdStr);
		edge.setTarget(targetIdStr);
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
		menuLabel.setKey(GraphMLKeyNames.BREADCRUMB_STRATEGY);
		menuLabel.setContent("SHORTEST_PATH_TO_ROOT");
		graphmltype.getGraphOrData().add(breadcrumbStrategy);

		return graphmltype;
	}

	private GraphmlType addKeyTypes(GraphmlType graphmlType) {

		// <key id="label" for="graphml" attr.name="label"
		// attr.type="string"></key>
		KeyType graphmlLabel = of.createKeyType();
		graphmlLabel.setAttrName("label");
		graphmlLabel.setAttrType(KeyTypeType.STRING);
		graphmlLabel.setId("label");
		graphmlLabel.setFor(KeyForType.GRAPHML);
		graphmlType.getKey().add(graphmlLabel);

		// <key id="breadcrumb-strategy" for="graphml" attr.name="breadcrumb-strategy" attr.type="string" />
		KeyType breadcrumbStrategy = of.createKeyType();
		breadcrumbStrategy.setAttrName("label");
		breadcrumbStrategy.setAttrType(KeyTypeType.STRING);
		breadcrumbStrategy.setId("label");
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
