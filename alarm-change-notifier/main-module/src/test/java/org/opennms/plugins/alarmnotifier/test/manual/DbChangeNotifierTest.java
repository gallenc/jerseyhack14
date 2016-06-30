package org.opennms.plugins.alarmnotifier.test.manual;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.impossibl.postgres.jdbc.PGDataSource;

import org.opennms.plugins.alarmnotifier.DatabaseChangeNotifier;
import org.opennms.plugins.alarmnotifier.DbNotificationClient;
import org.opennms.plugins.alarmnotifier.DbNotificationClientDefaultTestImpl;


public class DbChangeNotifierTest {

	@Test
	public void test1() {
		System.out.println("Starting DbChangeNotifierTest test1");

		PGDataSource pgDataSource = new PGDataSource();
		
		pgDataSource.setHost("localhost");
		pgDataSource.setPort(5432);
		pgDataSource.setDatabase("opennms");
		pgDataSource.setUser("opennms");
		pgDataSource.setPassword("opennms");

		DatabaseChangeNotifier dbChangeNotifier = null;

		try {
			System.out.println("DbChangeNotifierTest creating connection - this is quite slow");
			
			List<String> paramList = new ArrayList<String>();
			
			paramList.add(DatabaseChangeNotifier.NOTIFY_EVENT_CHANGES);
			paramList.add(DatabaseChangeNotifier.NOTIFY_ALARM_CHANGES);
			
			dbChangeNotifier = new DatabaseChangeNotifier(pgDataSource,paramList);
			
			DbNotificationClient dbNotificationClient= new DbNotificationClientDefaultTestImpl();
			dbNotificationClient.setDatabaseChangeNotifier(dbChangeNotifier);
			dbNotificationClient.init();
			
			System.out.println("DbChangeNotifierTest initialising connection");
			dbChangeNotifier.init();
			
			System.out.println("DbChangeNotifierTest waiting for messages or until timeout");

			try{ // wait for interrupt or time out
				Thread.sleep(100000);
			} catch (InterruptedException e){}
			
			System.out.println("DbChangeNotifierTest shutting down");

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			System.out.println("DbChangeNotifierTest destroying connection");
			try {
				if (dbChangeNotifier != null) {
					dbChangeNotifier.destroy();
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

		System.out.println("End of DbChangeNotifierTest test1");
	}

}