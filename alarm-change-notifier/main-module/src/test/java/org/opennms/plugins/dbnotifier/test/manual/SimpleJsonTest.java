package org.opennms.plugins.dbnotifier.test.manual;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SimpleJsonTest {

	@Test
	public void test() {

		// contents of alarm to json in postgres
		String testString="{\"alarmid\":285,\"eventuei\":\"uei.opennms.org/application/generic/piAlarmRaise\",\"nodeid\":null,\"ipaddr\":\"127.0.0.1\",\"serviceid\":null,\"reductionkey\":\"uei.opennms.org/application/generic/piAlarmRaise:0:127.0.0.1:1\",\"alarmtype\":1,\"counter\":2,\"severity\":5,\"lasteventid\":4160,\"firsteventtime\":\"2016-07-04 16:19:14+01\",\"lasteventtime\":\"2016-07-04 16:24:04+01\",\"firstautomationtime\":null,\"lastautomationtime\":null,\"description\":\"Generic Raspberry Pi Alarm Raise 1\",\"logmsg\":\"Generic Raspberry Pi Alarm Raise 1\",\"operinstruct\":\"\",\"tticketid\":null,\"tticketstate\":null,\"mouseovertext\":null,\"suppresseduntil\":\"2016-07-04 16:19:14+01\",\"suppresseduser\":null,\"suppressedtime\":\"2016-07-04 16:19:14+01\",\"alarmackuser\":null,\"alarmacktime\":null,\"managedobjectinstance\":null,\"managedobjecttype\":null,\"applicationdn\":null,\"ossprimarykey\":null,\"x733alarmtype\":null,\"x733probablecause\":0,\"qosalarmstate\":null,\"clearkey\":null,\"ifindex\":null,\"eventparms\":\"PiIoId=1(string,text)\",\"stickymemo\":null,\"systemid\":\"00000000-0000-0000-0000-000000000000\"}";


		JSONParser parser = new JSONParser();

		try {
			System.out.println(this.getClass().getName()+" start of test");

			System.out.println(testString);
			
			Object obj = parser.parse(testString);

			JSONObject jsonObject = (JSONObject) obj;
			
			
			System.out.println("jsonObject.toString():"+jsonObject.toString());
			

			
			 for (Object key : jsonObject.keySet()) {
			        //based on you key types
			        String keyStr = (String)key;
			        Object keyvalue = jsonObject.get(keyStr);

			        //Print key and value
			        System.out.println("key: "+ keyStr + " value: " + keyvalue);
			 }


		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println(this.getClass().getName()+" end of test");

	}
}





