package org.opennms.plugins.alarmnotifier;

import com.impossibl.postgres.api.jdbc.PGConnection;
import com.impossibl.postgres.api.jdbc.PGNotificationListener;

import javax.sql.DataSource;
import java.sql.Statement;


public class AlarmNotifier {


    private PGConnection pgConnection;

    public AlarmNotifier(DataSource dataSource) throws Throwable {

        pgConnection = (PGConnection) dataSource.getConnection();

        pgConnection.addNotificationListener(new PGNotificationListener() {
            @Override
            public void notification(int processId, String channelName, String payload) {
            	System.out.println("notification received: payload:"+payload);
            }
        });

    }

    public void init() throws Throwable {
    	System.out.println("initialising AlarmNotifier");
        Statement statement = pgConnection.createStatement();
        statement.execute("LISTEN dml_events");
        statement.close();

    }

    public void destroy() throws Throwable {
    	System.out.println("stopping AlarmNotifier");
        Statement statement = pgConnection.createStatement();
        statement.execute("UNLISTEN dml_events");
        statement.close();
    }
}

