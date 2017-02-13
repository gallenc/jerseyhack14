package org.opennms.plugins.graphml.asset;

import java.util.Map;

import org.graphdrawing.graphml.xmlns.DataType;
import org.graphdrawing.graphml.xmlns.GraphEdgedefaultType;
import org.graphdrawing.graphml.xmlns.GraphType;
import org.graphdrawing.graphml.xmlns.KeyForType;
import org.graphdrawing.graphml.xmlns.KeyType;
import org.graphdrawing.graphml.xmlns.KeyTypeType;
import org.graphdrawing.graphml.xmlns.NodeType;
import org.graphdrawing.graphml.xmlns.GraphmlType;
import org.graphdrawing.graphml.xmlns.ObjectFactory;

public class AssetTopologyMapperImpl implements AssetTopologyMapper {

	ObjectFactory of = new ObjectFactory();

	@Override
	public GraphmlType nodeInfoToTopology(NodeInfoRepository nodeInfo) {

		GraphmlType graphmltype = new GraphmlType();

		addKeyTypes(graphmltype);
		GraphType graph = createGraph(graphmltype);
		
		addNodes(graph, nodeInfo);

		return graphmltype;
	}



	public GraphmlType addKeyTypes(GraphmlType graphmlType) {

		// <key id="label" for="graph" attr.name="label"
		// attr.type="string"></key>
		KeyType graphLabelKey = of.createKeyType();
		graphLabelKey.setAttrName(GraphMLKeyNames.LABEL);
		graphLabelKey.setAttrType(KeyTypeType.STRING);
		graphLabelKey.setId(GraphMLKeyNames.LABEL);
		graphLabelKey.setFor(KeyForType.GRAPH);
		graphmlType.getKey().add(graphLabelKey);

		// <key id="label" for="graphml" attr.name="label"
		// attr.type="string"></key>
		// KeyType graphmlLabel = of.createKeyType();
		// graphmlLabel.setAttrName("label");
		// graphmlLabel.setAttrType(KeyTypeType.STRING);
		// graphmlLabel.setId("label");
		// graphmlLabel.setFor(KeyForType.GRAPHML);
		// graphmlType.getKey().add(graphmlLabel);

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
		semanticZoomlevelKey.setAttrType(KeyTypeType.STRING);
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

	private GraphType createGraph(GraphmlType graphmlType) {

		// <!-- shows up in the menu -->
		// <data key="label">Minimalistic GraphML Topology Provider</data>

		DataType menuLabel = of.createDataType();
		menuLabel.setKey(GraphMLKeyNames.LABEL);
		menuLabel.setContent("Asset_GraphML_Topology_Provider");
		graphmlType.getGraphOrData().add(menuLabel);

		// <graph id="graph1">
		GraphType graph = of.createGraphType();
		graph.setId("graph2");
		graph.setEdgedefault(GraphEdgedefaultType.UNDIRECTED);
		graphmlType.getGraphOrData().add(graph);

		// <data key="namespace">minimalistic</data>
		DataType namespace = of.createDataType();
		namespace.setKey(GraphMLKeyNames.NAMESPACE);
		namespace.setContent("minimalistic_1");
		graph.getDataOrNodeOrEdge().add(namespace);



		return graph;
	}
	
	private void addNodes(GraphType graph, NodeInfoRepository nodeInfoRepository) {
		
		
		Map<String, Map<String, String>> nodeInfo = nodeInfoRepository.getNodeInfo();
		
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
