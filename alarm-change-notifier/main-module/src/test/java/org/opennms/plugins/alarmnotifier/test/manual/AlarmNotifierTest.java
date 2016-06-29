package org.opennms.plugins.alarmnotifier.test.manual;

import static org.junit.Assert.*;

import org.junit.Test;

import com.impossibl.postgres.jdbc.PGDataSource;

import javax.sql.DataSource;

import org.opennms.plugins.alarmnotifier.AlarmNotifier;

public class AlarmNotifierTest {

	@Test
	public void test1() {
		System.out.println("Starting AlarmNotifierTest test1");

		PGDataSource pgDataSource = new PGDataSource();
		
		pgDataSource.setHost("localhost");
		pgDataSource.setPort(5432);
		pgDataSource.setDatabase("opennms");
		pgDataSource.setUser("opennms");
		pgDataSource.setPassword("opennms");

		AlarmNotifier alarmNotifier = null;

		try {
			System.out.println("AlarmNotifierTest creating connection - this is quite slow");
			alarmNotifier = new AlarmNotifier(pgDataSource);
			
			System.out.println("AlarmNotifierTest initialising connection");
			alarmNotifier.init();
			
			System.out.println("AlarmNotifierTest waiting for messages or until timeout");

			try{ // wait for interrupt or time out
				Thread.sleep(100000);
			} catch (InterruptedException e){}
			
			System.out.println("AlarmNotifierTest shutting down");

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			System.out.println("AlarmNotifierTest destroying connection");
			try {
				if (alarmNotifier != null) {
					alarmNotifier.destroy();
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

		System.out.println("End of AlarmNotifierTest test1");
	}

}
