package org.opennms.plugins.alarmnotifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DbNotificationClientDefaultTestImpl implements DbNotificationClient{
	private static final Logger LOG = LoggerFactory.getLogger(DbNotificationClientDefaultTestImpl.class);

	private DatabaseChangeNotifier databaseChangeNotifier;
	

	/**
	 * @param databaseChangeNotifier
	 */
	public void setDatabaseChangeNotifier(DatabaseChangeNotifier databaseChangeNotifier) {
		this.databaseChangeNotifier = databaseChangeNotifier;
	}
	
	/**
	 * @return the databaseChangeNotifier
	 */
	public DatabaseChangeNotifier getDatabaseChangeNotifier() {
		return databaseChangeNotifier;
	}

	public void init(){
		LOG.debug("initialising client");
		if (databaseChangeNotifier==null) throw new IllegalStateException("databaseChangeNotifier cannot be null");
		databaseChangeNotifier.addDbNotificationClient(this);
	}
	
	public void destroy(){
		LOG.debug("shutting down client");
		if (databaseChangeNotifier==null) throw new IllegalStateException("databaseChangeNotifier cannot be null");
		databaseChangeNotifier.removeDbNotificationClient(this);
	}
	
	@Override
	public void sendDbNotification(DbNotification dbNotification) {
		System.out.println("TestDbNotificationDefaultClientImpl notification received:\n processId:"+dbNotification.getProcessId()
				+ "\n channelName:"+dbNotification.getChannelName()
				+ "\n payload:"+dbNotification.getPayload());
		LOG.error("TestDbNotificationClientImpl notification received:\n processId:"+dbNotification.getProcessId()
				+ "\n channelName:"+dbNotification.getChannelName()
				+ "\n payload:"+dbNotification.getPayload());
		
	}



}
