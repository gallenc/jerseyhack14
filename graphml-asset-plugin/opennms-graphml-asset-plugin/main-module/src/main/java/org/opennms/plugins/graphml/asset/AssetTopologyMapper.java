package org.opennms.plugins.graphml.asset;

import org.graphdrawing.graphml.xmlns.GraphmlType;

public interface AssetTopologyMapper {
	
	public GraphmlType nodeInfoToTopology(NodeInfoRepository nodeInfo);

}
