package org.opennms.plugins.alarmnotifier;

/**
 * Client interface used to register with DatabaseChangeNotifier to receive DbNotifications
 * @author admin
 *
 */
public interface DbNotificationClient {

	public void sendDbNotification(DbNotification dbNotification);
	
	public void setDatabaseChangeNotifier(DatabaseChangeNotifier databaseChangeNotifier);
	
	public DatabaseChangeNotifier getDatabaseChangeNotifier();

	public void init();
	
	public void destroy();
	
}
