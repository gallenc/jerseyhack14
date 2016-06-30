package org.opennms.plugins.alarmnotifier;

import com.impossibl.postgres.api.jdbc.PGConnection;
import com.impossibl.postgres.api.jdbc.PGNotificationListener;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;
import java.util.List;


public class DatabaseChangeNotifier {

	private static final Logger LOG = LoggerFactory.getLogger(DatabaseChangeNotifier.class);

	/**
	 * If NOTIFY_ALARM_CHANGES is added to paramList in Constructor, triggers are applied to alarms table
	 */
	public static final String NOTIFY_ALARM_CHANGES="NOTIFY_ALARM_CHANGES";
	
	/**
	 * If  NOTIFY_EVENT_CHANGES is added to paramList in Constructor, triggers are applied to events table
	 */
	public static final String NOTIFY_EVENT_CHANGES="NOTIFY_EVENT_CHANGES";

	// if true triggers are applied to events table
	private boolean listenForEvents=false;

	// if true triggers are applied to alarms table
	private boolean listenForAlarms=false;

	// SQL statement to remove triggers from events table
	private String disConnectionStatementEvents="";

	// SQL statement to set up triggers on events table
	private String connectionStatementEvents="";
	
	// SQL statement to remove triggers from alarms table
	private String disConnectionStatementAlarms="";

	// SQL statement to set up triggers on alarms table
	private String connectionStatementAlarms="";
	
	private PGConnection pgConnection;

	/**
	 * 
	 * @return SQL statement to set up triggers on events table
	 */
	public String getConnectionStatementEvents() {
		return connectionStatementEvents;
	}

	/**
	 * 
	 * @param connectionStatementEvents SQL statement to set up triggers on events table
	 */
	public void setConnectionStatementEvents(String connectionStatementEvents) {
		this.connectionStatementEvents = connectionStatementEvents;
	}

	/**
	 * 
	 * @return SQL statement to remove triggers from events table
	 */
	public String getDisConnectionStatementEvents() {
		return disConnectionStatementEvents;
	}

	/**
	 * 
	 * @param disConnectionStatementEvents SQL statement to remove triggers from events table
	 */
	public void setDisConnectionStatementEvents(String disConnectionStatementEvents) {
		this.disConnectionStatementEvents = disConnectionStatementEvents;
	}

	/**
	 * 
	 * @return connectionStatementAlarms SQL statement to set up triggers on alarms table
	 */
	public String getConnectionStatementAlarms() {
		return connectionStatementAlarms;
	}

	/**
	 * 
	 * @param connectionStatementAlarms SQL statement to set up triggers on alarms table
	 */
	public void setConnectionStatementAlarms(String connectionStatementAlarms) {
		this.connectionStatementAlarms = connectionStatementAlarms;
	}

	/**
	 * 
	 * @return disConnectionStatementAlarms SQL statement to remove triggers from alarms table
	 */
	public String getDisConnectionStatementAlarms() {
		return disConnectionStatementAlarms;
	}

	/**
	 * 
	 * @param disConnectionStatementAlarms SQL statement to remove triggers from alarms table
	 */
	public void setDisConnectionStatementAlarms(String disConnectionStatementAlarms) {
		this.disConnectionStatementAlarms = disConnectionStatementAlarms;
	}

	public DatabaseChangeNotifier(DataSource dataSource, List<String> paramList) throws Throwable {

		String s="DatabaseChangeNotifier Paramaters: ";
		for(String param : paramList){
			s=s+param+" ";
		}
		System.out.println(s);
		System.out.println("setting up connection - be patient this is quite slow");

		if (paramList.contains(NOTIFY_ALARM_CHANGES)) listenForAlarms=true;	
		if (paramList.contains(NOTIFY_EVENT_CHANGES)) listenForEvents=true;

		pgConnection = (PGConnection) dataSource.getConnection();

		System.out.println("setting up connection listener");
		
		// pgListner is set up outside pgConnection to give hard reference so not garbage collected
		// see http://stackoverflow.com/questions/37916489/listen-notify-pgconnection-goes-down-java
		PGNotificationListener pgListener = new PGNotificationListener() {
			Logger LOG = LoggerFactory.getLogger(DatabaseChangeNotifier.class);

			@Override
			public void notification(int processId, String channelName, String payload) {
				System.out.println("notification received: processId:"+processId
						+ " channelName:"+channelName
						+ " payload:"+payload);
				LOG.error("notification received: processId:"+processId
						+ " channelName:"+channelName
						+ " payload:"+payload);
			}
		};
		
		pgConnection.addNotificationListener(pgListener);

	}

	public void init() throws Throwable {
		System.out.println("initialising DatabaseChangeNotifier");
		Statement statement = pgConnection.createStatement();

		if(listenForEvents){
			System.out.println("Executing connectionStatementEvents="+connectionStatementEvents);
			statement.execute(connectionStatementEvents);

			System.out.println("Executing 'LISTEN opennms_event_changes'");
			statement.execute("LISTEN opennms_event_changes");
		}

		if(listenForAlarms){
			System.out.println("Executing connectionStatementAlarms="+connectionStatementAlarms);
			statement.execute(connectionStatementAlarms);

			System.out.println("Executing 'LISTEN opennms_alarm_changes'");
			statement.execute("LISTEN opennms_alarm_changes");
		}

		statement.close();

	}

	public void destroy() throws Throwable {
		System.out.println("stopping DatabaseChangeNotifier");
		Statement statement = pgConnection.createStatement();

		if(listenForEvents){

			System.out.println("Executing 'UNLISTEN opennms_event_changes'");
			statement.execute("UNLISTEN opennms_event_changes");

			System.out.println("Executing disConnectionStatementEvents="+disConnectionStatementEvents);
			statement.execute(disConnectionStatementEvents);
		}

		if(listenForAlarms){

			System.out.println("Executing 'UNLISTEN opennms_alarm_changes'");
			statement.execute("UNLISTEN opennms_alarm_changes");

			System.out.println("Executing disConnectionStatementAlarms="+disConnectionStatementAlarms);
			statement.execute(disConnectionStatementAlarms);
		}

		statement.close();
	}
}

