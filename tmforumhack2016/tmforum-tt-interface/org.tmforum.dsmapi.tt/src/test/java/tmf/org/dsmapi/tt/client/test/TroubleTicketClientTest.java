package tmf.org.dsmapi.tt.client.test;

import static org.junit.Assert.*;

import org.junit.Test;

import tmf.org.dsmapi.tt.TroubleTicket;
import tmf.org.dsmapi.tt.client.TroubleTicketClient;

public class TroubleTicketClientTest {
	
	private String ttclientbase="http://env-0693795.jelastic.servint.net/DSTroubleTicket/api/troubleTicketManagement/v2/";
	
	// http://env-0693795.jelastic.servint.net/DSTroubleTicket/api/troubleTicketManagement/v2/troubleTicket/2

	@Test
	public void test() {
		TroubleTicketClient client = new TroubleTicketClient(ttclientbase);
		
		String id="2";
		TroubleTicket tt= client.find(TroubleTicket.class, id); 
	}
	
	@Test
	public void test2() {
		TroubleTicketClient client = new TroubleTicketClient(ttclientbase);

		String result= client.countREST(); 
		
		System.out.println("ticket count="+result);
	}

}
