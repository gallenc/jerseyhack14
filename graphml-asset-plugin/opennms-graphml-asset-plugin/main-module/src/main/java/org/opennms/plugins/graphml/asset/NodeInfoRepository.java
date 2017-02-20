/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2002-2014 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2014 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.plugins.graphml.asset;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.opennms.netmgt.dao.api.NodeDao;
import org.opennms.netmgt.model.OnmsAssetRecord;
import org.opennms.netmgt.model.OnmsCategory;
import org.opennms.netmgt.model.OnmsGeolocation;
import org.opennms.netmgt.model.OnmsNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionOperations;


public class NodeInfoRepository {
	private static final Logger LOG = LoggerFactory.getLogger(NodeInfoRepository.class);

	private volatile NodeDao nodeDao;
	private volatile TransactionOperations transactionOperations;

	/**
	 * Map of Maps of node parameters which is populated by populateBodyWithNodeInfo
	 * nodeKey is a unique identifier for the node from nodeid and/or node foreignsource or foreignid
	 * node_parameterKey is the parameter name e.g. foreignsource or foreignid
	 * node_parameterValue is the parameter value for the given key
	 * Map<nodeKey,Map<node_parameterKey,node_parameterValue>> nodeInfo
	 */
	private Map<String,Map<String,String>> nodeInfo = Collections.synchronizedMap(new LinkedHashMap<String, Map<String, String>>());

	/* getters and setters */
	public Map<String, Map<String, String>> getNodeInfo() {
		return nodeInfo;
	}

	public NodeDao getNodeDao() {
		return nodeDao;
	}

	public void setNodeDao(NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}

	public TransactionOperations getTransactionOperations() {
		return transactionOperations;
	}

	public void setTransactionOperations(TransactionOperations transactionOperations) {
		this.transactionOperations = transactionOperations;
	}

	/** 
	 * utility method to clear nodeInfo table
	 */
	private void clearNodeInfo(){
		// make sure nodeInfo is empty
		for(String nodeKey:nodeInfo.keySet()){
			Map<String, String> param = nodeInfo.get(nodeKey);
			if (param !=null) param.clear();
		};
		nodeInfo.clear();
	}

	/**
	 * initialises node info map from the opennms database node and asset tables using nodeDao
	 */
	public synchronized void initialiseNodeInfo() {
		if (nodeDao==null) throw new RuntimeException("nodeDao must be set before running initialiseNodeInfo");
		LOG.info("initialising node info");

		// make sure nodeInfo is empty
		clearNodeInfo();

		// populate nodeinfo from latest database provisioned nodes information
		// wrap in a transaction so that Hibernate session is bound and getCategories works
		transactionOperations.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				List<OnmsNode> nodeList = nodeDao.findAllProvisionedNodes();
				initialiseNodeInfoFromNodeList(nodeList);
			}
		});       
	}

	/**
	 * initialises node info map from supplied opennms nodeList
	 * @param nodeList
	 */
	public synchronized void initialiseNodeInfoFromNodeList(List<OnmsNode> nodeList) {
		if (nodeList==null) throw new RuntimeException("nodeList must note be null");

		// make sure nodeInfo is empty
		clearNodeInfo();

		// populate nodeinfo from supplied nodeList information
		for (OnmsNode node:nodeList){
			Map<String, String> nodeParameters = new LinkedHashMap<String, String>();
			populateNodeParametersWithNodeInfo(nodeParameters , node);
			String nodeId = node.getNodeId();
			nodeInfo.put(nodeId, nodeParameters);
			if (LOG.isDebugEnabled()){
				LOG.debug("\nNodeInfoRepository added nodeId:"+nodeId+" parameters:"+nodeParamatersToString(nodeParameters));
			}
		}
	}


	/**
	 * utility method to list contents of nodeParameters as String for debug
	 * @param nodeParameters
	 * @return single string of parameters for node
	 */
	public String nodeParamatersToString(Map<String, String> nodeParameters){
		if(nodeParameters==null) return null;
		StringBuffer sb=new StringBuffer();
		for( String parameterKey:nodeParameters.keySet()){
			sb.append("\n    nodeParamKey: '"+parameterKey+"' nodeParamValue: '"+nodeParameters.get(parameterKey)+"'");
		}
		return sb.toString();
	};

	/**
	 * utility method to list contents of nodeInfo as String for debug
	 * @return
	 */
	public String nodeInfoToString(){
		StringBuffer sb=new StringBuffer();

		for(String nodeId:nodeInfo.keySet()){
			Map<String, String> nodeParams = nodeInfo.get(nodeId);
			sb.append("\n nodeId: '"+nodeId+"' nodeParameters:"+nodeParamatersToString(nodeParams));
		}
		return sb.toString();
	}



	/**
	 * utility method to populate a Map with the most important node attributes
	 *
	 * @param nodeParameters the map
	 * @param node the node object
	 */
	private void populateNodeParametersWithNodeInfo(Map<String,String> nodeParameters, OnmsNode node) {
		nodeParameters.put(NodeParamLabels.NODE_NODELABEL, node.getLabel());
		nodeParameters.put(NodeParamLabels.NODE_NODEID, node.getNodeId());
		nodeParameters.put(NodeParamLabels.NODE_FOREIGNSOURCE, node.getForeignSource());
		nodeParameters.put(NodeParamLabels.NODE_FOREIGNID, node.getForeignId());
		nodeParameters.put(NodeParamLabels.NODE_NODESYSNAME, node.getSysName());
		nodeParameters.put(NodeParamLabels.NODE_NODESYSLOCATION, node.getSysLocation());
		nodeParameters.put(NodeParamLabels.NODE_OPERATINGSYSTEM, node.getOperatingSystem());

		StringBuilder categories=new StringBuilder();
		for (Iterator<OnmsCategory> i=node.getCategories().iterator();i.hasNext();) {
			categories.append(((OnmsCategory)i.next()).getName());
			if(i.hasNext()) {
				categories.append(",");
			}
		}
		nodeParameters.put(NodeParamLabels.NODE_CATEGORIES, categories.toString());

		// parent information
		OnmsNode parent = node.getParent();
		if (parent!=null){
			nodeParameters.put(NodeParamLabels.PARENT_NODELABEL, parent.getLabel());
			nodeParameters.put(NodeParamLabels.PARENT_NODEID, parent.getNodeId());
			nodeParameters.put(NodeParamLabels.PARENT_FOREIGNSOURCE, parent.getForeignSource());
			nodeParameters.put(NodeParamLabels.PARENT_FOREIGNID, parent.getForeignId());
		}

		//assetRecord.
		OnmsAssetRecord assetRecord= node.getAssetRecord() ;
		if(assetRecord!=null){

			//geolocation
			OnmsGeolocation gl = assetRecord.getGeolocation();
			if (gl !=null){
				nodeParameters.put(NodeParamLabels.ASSET_COUNTRY, gl.getCountry());
				nodeParameters.put(NodeParamLabels.ASSET_ADDRESS1, gl.getAddress1());
				nodeParameters.put(NodeParamLabels.ASSET_ADDRESS2, gl.getAddress2());
				nodeParameters.put(NodeParamLabels.ASSET_CITY, gl.getCity());
				nodeParameters.put(NodeParamLabels.ASSET_ZIP, gl.getZip());
				nodeParameters.put(NodeParamLabels.ASSET_STATE, gl.getState());
				nodeParameters.put(NodeParamLabels.ASSET_LATITUDE, (gl.getLatitude()!=null ? gl.getLatitude().toString() : null));
				nodeParameters.put(NodeParamLabels.ASSET_LONGITUDE, (gl.getLongitude()!=null ? gl.getLongitude().toString() : null));
			}

			//assetRecord
			nodeParameters.put(NodeParamLabels.ASSET_REGION, assetRecord.getRegion());
			nodeParameters.put(NodeParamLabels.ASSET_DIVISION, assetRecord.getDivision());
			nodeParameters.put(NodeParamLabels.ASSET_DEPARTMENT, assetRecord.getDepartment());
			nodeParameters.put(NodeParamLabels.ASSET_BUILDING, assetRecord.getBuilding());
			nodeParameters.put(NodeParamLabels.ASSET_FLOOR, assetRecord.getFloor());
			nodeParameters.put(NodeParamLabels.ASSET_ROOM,  assetRecord.getRoom());
			nodeParameters.put(NodeParamLabels.ASSET_RACK, assetRecord.getRack());
			nodeParameters.put(NodeParamLabels.ASSET_SLOT, assetRecord.getSlot());
			nodeParameters.put(NodeParamLabels.ASSET_PORT, assetRecord.getPort());
			nodeParameters.put(NodeParamLabels.ASSET_CIRCUITID, assetRecord.getCircuitId());

			nodeParameters.put(NodeParamLabels.ASSET_CATEGORY, assetRecord.getCategory());
			nodeParameters.put(NodeParamLabels.ASSET_DISPLAYCATEGORY, assetRecord.getDisplayCategory());
			nodeParameters.put(NodeParamLabels.ASSET_NOTIFYCATEGORY, assetRecord.getNotifyCategory());
			nodeParameters.put(NodeParamLabels.ASSET_POLLERCATEGORY, assetRecord.getPollerCategory());
			nodeParameters.put(NodeParamLabels.ASSET_THRESHOLDCATEGORY, assetRecord.getThresholdCategory());
			nodeParameters.put(NodeParamLabels.ASSET_MANAGEDOBJECTTYPE, assetRecord.getManagedObjectType());
			nodeParameters.put(NodeParamLabels.ASSET_MANAGEDOBJECTINSTANCE, assetRecord.getManagedObjectInstance());

			nodeParameters.put(NodeParamLabels.ASSET_MANUFACTURER, assetRecord.getManufacturer());
			nodeParameters.put(NodeParamLabels.ASSET_VENDOR, assetRecord.getVendor());
			nodeParameters.put(NodeParamLabels.ASSET_MODELNUMBER, assetRecord.getModelNumber());
			nodeParameters.put(NodeParamLabels.ASSET_DESCRIPTION, assetRecord.getDescription());
			nodeParameters.put(NodeParamLabels.ASSET_OPERATINGSYSTEM, assetRecord.getOperatingSystem()); 


			/*not used or depreciated*/
			/*
                assetRecord.getComment();
                assetRecord.getPassword();
                assetRecord.getConnection();
                //assetRecord.getCountry(); // depreciated
                assetRecord.getUsername();
                assetRecord.getEnable();
                assetRecord.getAutoenable();
                assetRecord.getCpu();
                assetRecord.getRam();
                assetRecord.getSnmpcommunity();
                assetRecord.getRackunitheight();
                assetRecord.getAdmin();
                assetRecord.getAdditionalhardware();
                assetRecord.getInputpower();
                assetRecord.getNumpowersupplies();
                assetRecord.getHdd6();
                assetRecord.getHdd5();
                assetRecord.getHdd4();
                assetRecord.getHdd3();
                assetRecord.getHdd2();
                assetRecord.getHdd1();
                assetRecord.getStoragectrl();
                //assetRecord.getAddress1();// depreciated
                //assetRecord.getAddress2();// depreciated
                //assetRecord.getCity();// depreciated
                //assetRecord.getZip();// depreciated
                assetRecord.getVmwareManagedEntityType();
                assetRecord.getVmwareManagedObjectId();
                assetRecord.getVmwareManagementServer();
                assetRecord.getVmwareState();
                assetRecord.getVmwareTopologyInfo();
                assetRecord.getSerialNumber();
                assetRecord.getAssetNumber();
                assetRecord.getVendorPhone();
                assetRecord.getVendorFax();
                assetRecord.getVendorAssetNumber();
                assetRecord.getLastModifiedBy();
                assetRecord.getDateInstalled();
                assetRecord.getLease();
                assetRecord.getLeaseExpires();
                assetRecord.getSupportPhone();
                assetRecord.getMaintcontract();
                //assetRecord.getMaintContractNumber();// depreciated
                assetRecord.getMaintContractExpiration();
                //assetRecord.getState();// depreciated
			 */
		}

	}

}
