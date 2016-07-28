package org.opennms.plugins.dbnotifier.test.manual;

import org.opennms.plugins.elasticsearch.rest.EventToIndex;
import org.opennms.netmgt.model.events.EventBuilder;
import org.opennms.netmgt.xml.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.Gson;

public class EventToIndexTest {
	private static final Logger LOG = LoggerFactory.getLogger(EventToIndexTest.class);

	// uei definitions of alarm change events
	public static final String ALARM_DELETED_EVENT = "uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmDeleted";
	public static final String ALARM_CREATED_EVENT = "uei.opennms.org/plugin/AlarmChangeNotificationEvent/NewAlarmCreated";
	public static final String ALARM_SEVERITY_CHANGED_EVENT = "uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmSeverityChanged";
	public static final String ALARM_ACKNOWLEDGED_EVENT = "uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmAcknowledged";
	public static final String ALARM_UNACKNOWLEDGED_EVENT = "uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmUnAcknowledged";
	public static final String ALARM_SUPPRESSED_EVENT = "uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmSuppressed";
	public static final String ALARM_UNSUPPRESSED_EVENT = "uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmUnSuppressed";
	public static final String ALARM_TROUBLETICKET_STATE_CHANGE_EVENT = "uei.opennms.org/plugin/AlarmChangeNotificationEvent/TroubleTicketStateChange";
	public static final String ALARM_CHANGED_EVENT = "uei.opennms.org/plugin/AlarmChangeNotificationEvent/AlarmChanged";

	public static final String EVENT_SOURCE_NAME = "AlarmChangeNotifier";


	public static final String TEST_ALARM_JSON_1="{\"alarmid\":807,\"eventuei\":\"uei.opennms.org/nodes/nodeLostService\",\"nodeid\":36,\"ipaddr\":\"142.34.5.19\",\"serviceid\":2,\"reductionkey\":\"uei.opennms.org/nodes/nodeLostService::36:142.34.5.19:HTTP\",\"alarmtype\":1,\"counter\":1,\"severity\":5,\"lasteventid\":7003,\"firsteventtime\":\"2016-07-27 22:20:52.282+01\",\"lasteventtime\":\"2016-07-27 22:20:52.282+01\",\"firstautomationtime\":null,\"lastautomationtime\":null,\"description\":\"<p>A HTTP outage was identified on interface\n      142.34.5.19.</p> <p>A new Outage record has been\n      created and service level availability calculations will be\n      impacted until this outage is resolved.</p>\",\"logmsg\":\"HTTP outage identified on interface 142.34.5.19 with reason code: Unknown.\",\"operinstruct\":null,\"tticketid\":null,\"tticketstate\":null,\"mouseovertext\":null,\"suppresseduntil\":\"2016-07-27 22:20:52.282+01\",\"suppresseduser\":null,\"suppressedtime\":\"2016-07-27 22:20:52.282+01\",\"alarmackuser\":null,\"alarmacktime\":null,\"managedobjectinstance\":null,\"managedobjecttype\":null,\"applicationdn\":null,\"ossprimarykey\":null,\"x733alarmtype\":null,\"x733probablecause\":0,\"qosalarmstate\":null,\"clearkey\":null,\"ifindex\":null,\"eventparms\":\"eventReason=Unknown(string,text)\",\"stickymemo\":null,\"systemid\":\"00000000-0000-0000-0000-000000000000\"}";



	@Test
	public void jestClientTest(){
		LOG.debug("start of test jestClientTest");

		try {
			// Get Jest client
			HttpClientConfig clientConfig = new HttpClientConfig.Builder(
					"http://localhost:9200").multiThreaded(true).build();
			JestClientFactory factory = new JestClientFactory();
			factory.setHttpClientConfig(clientConfig);
			JestClient jestClient = factory.getObject();

			try {
				Index index = alarmEventToIndex();
				DocumentResult dresult = jestClient.execute(index);

				LOG.debug("received dresult: "+dresult.getJsonString()+ "\n   response code:" +dresult.getResponseCode() +"\n   error message: "+dresult.getErrorMessage());

				String query = "{\n" 
				        +"\n       \"query\": {"
						+ "\n         \"match\": {"
					    + "\n         \"alarmid\": \"805\""
					    + "\n          }"
					    + "\n        }"
					    + "\n     }";

				Search search = new Search.Builder(query)
				// multiple index or types can be added.
				.addIndex("alarm-index")
				.build();

				SearchResult sresult = jestClient.execute(search);

				LOG.debug("received search sresult: "+sresult.getJsonString()+ "\n   response code:" +sresult.getResponseCode() +"\n   error message: "+sresult.getErrorMessage());





			} finally {
				// shutdown client
				jestClient.shutdownClient();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		LOG.debug("end of jestClientTest");
	}


	public Index alarmEventToIndex() {

		EventToIndex eti = new EventToIndex();
		LOG.debug("alarmEventToIndex 1");

		EventBuilder eb = new EventBuilder( ALARM_ACKNOWLEDGED_EVENT, EVENT_SOURCE_NAME);

		//String json="{"alarmid":806,"eventuei":"uei.opennms.org/nodes/nodeLostService","nodeid":36,"ipaddr":"142.34.5.19","serviceid":2,"reductionkey":"uei.opennms.org/nodes/nodeLostService::36:142.34.5.19:HTTP","alarmtype":1,"counter":1,"severity":5,"lasteventid":7003,"firsteventtime":"2016-07-27 22:20:52.282+01","lasteventtime":"2016-07-27 22:20:52.282+01","firstautomationtime":null,"lastautomationtime":null,"description":"<p>A HTTP outage was identified on interface\n      142.34.5.19.</p> <p>A new Outage record has been\n      created and service level availability calculations will be\n      impacted until this outage is resolved.</p>","logmsg":"HTTP outage identified on interface 142.34.5.19 with reason code: Unknown.","operinstruct":null,"tticketid":null,"tticketstate":null,"mouseovertext":null,"suppresseduntil":"2016-07-27 22:20:52.282+01","suppresseduser":null,"suppressedtime":"2016-07-27 22:20:52.282+01","alarmackuser":null,"alarmacktime":null,"managedobjectinstance":null,"managedobjecttype":null,"applicationdn":null,"ossprimarykey":null,"x733alarmtype":null,"x733probablecause":0,"qosalarmstate":null,"clearkey":null,"ifindex":null,"eventparms":"eventReason=Unknown(string,text)","stickymemo":null,"systemid":"00000000-0000-0000-0000-000000000000"}";




		LOG.debug("alarmEventToIndex 2");

		//copy in all values as json in params
		eb.addParam("oldalarmvalues",TEST_ALARM_JSON_1);
		eb.addParam("newalarmvalues",TEST_ALARM_JSON_1);
		Event event = eb.getEvent();
		event.setDbid(100);

		LOG.debug("alarmEventToIndex 2 created event:"+event.toString());

		String indexName="alarm-index";
		String indexType="data";
		Index index = eti.populateAlarmIndexBodyFromAlarmChangeEvent(event, indexName, indexType);
		LOG.debug("created alarm index:"+index.toString());


		return index;
	}

}
