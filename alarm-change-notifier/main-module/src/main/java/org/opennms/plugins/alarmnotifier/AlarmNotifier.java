package org.opennms.plugins.alarmnotifier;

import com.impossibl.postgres.api.jdbc.PGConnection;
import com.impossibl.postgres.api.jdbc.PGNotificationListener;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;


public class AlarmNotifier {
	private static final Logger LOG = LoggerFactory.getLogger(AlarmNotifier.class);
	
    private PGConnection pgConnection;
    
	private String disConnectionStatement="";
	
	private String connectionStatement="";
	
	public String getDisConnectionStatement() {
		return disConnectionStatement;
	}

	public void setDisConnectionStatement(String disConnectionStatement) {
		this.disConnectionStatement = disConnectionStatement;
	}

	public String getConnectionStatement() {
		return connectionStatement;
	}

	public void setConnectionStatement(String connectionStatement) {
		this.connectionStatement = connectionStatement;
	}

	
    public AlarmNotifier(DataSource dataSource) throws Throwable {
    	System.out.println("setting up connection - be patient this is quite slow");

        pgConnection = (PGConnection) dataSource.getConnection();

        System.out.println("setting up connection listener");
        pgConnection.addNotificationListener(new PGNotificationListener() {
            @Override
            public void notification(int processId, String channelName, String payload) {
            	System.out.println("notification received: payload:"+payload);
            	LOG.error("notification received: payload:"+payload);
            }
        });

    }

    public void init() throws Throwable {
    	System.out.println("initialising AlarmNotifier");
        Statement statement = pgConnection.createStatement();
        
        System.out.println("Executing connectionStatement="+connectionStatement);
        statement.execute(connectionStatement);
        
        System.out.println("Executing 'LISTEN dml_events'");
        statement.execute("LISTEN dml_events");
        
        statement.close();

    }

    public void destroy() throws Throwable {
    	System.out.println("stopping AlarmNotifier");
        Statement statement = pgConnection.createStatement();
        
        System.out.println("Executing 'UNLISTEN dml_events'");
        statement.execute("UNLISTEN dml_events");
        
        System.out.println("Executing disConnectionStatement="+disConnectionStatement);
        statement.execute(disConnectionStatement);
        
        statement.close();
    }
}

