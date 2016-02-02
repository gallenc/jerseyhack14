package tmf.org.dsmapi.tt.client.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tmf.org.dsmapi.tt.Note;
import tmf.org.dsmapi.tt.RelatedObject;
import tmf.org.dsmapi.tt.RelatedParty;
import tmf.org.dsmapi.tt.Severity;
import tmf.org.dsmapi.tt.Status;
import tmf.org.dsmapi.tt.TroubleTicket;
import tmf.org.dsmapi.tt.client.Format;
import tmf.org.dsmapi.tt.client.TroubleTicketClientV2;

public class TroubleTicketClientTest {
	private static final Logger LOG = LoggerFactory.getLogger(TroubleTicketClientTest.class);

	private String baseUri="http://env-0693795.jelastic.servint.net/DSTroubleTicket/api/troubleTicketManagement/v2";

	@Test
	public void getKnownTroubleTicket(){
		LOG.debug("test getKnownTroubleTicket start");
		try{
			TroubleTicketClientV2 client = new TroubleTicketClientV2();
			client.setBaseUri(baseUri);
			TroubleTicket tt= client.getTroubleTicket("2");
			LOG.debug("test getKnownTroubleTicket received troubleticket="+tt);
		} catch (Exception e){
			LOG.error("test getKnownTroubleTicket error:",e);
			throw new RuntimeException(e);
		}
		LOG.debug("test getKnownTroubleTicket end");
	}

	@Test
	public void getUnKnownTroubleTicket(){
		LOG.debug("test getUnKnownTroubleTicket start");
		try{
			TroubleTicketClientV2 client = new TroubleTicketClientV2();
			client.setBaseUri(baseUri);
			TroubleTicket tt= client.getTroubleTicket("5555555");
			LOG.debug("test getUnKnownTroubleTicket received troubleticket="+tt);
		} catch (Exception e){
			LOG.error("test getUnKnownTroubleTicket error:",e);
			throw new RuntimeException(e);
		}
		LOG.debug("test getUnKnownTroubleTicket end");
	}
	
	@Test
	public void createTroubleTicketTest(){
		LOG.debug("test createTroubleTicketTest start");
		try{
			TroubleTicketClientV2 client = new TroubleTicketClientV2();
			client.setBaseUri(baseUri);
			TroubleTicket createTicket=new TroubleTicket();
			
			createTicket.setDescription("ticket client test description");
			
			String creationDate= Format.toString(new Date());
			createTicket.setCreationDate(creationDate);
			
			List<Note> notes=new ArrayList<Note>();
			createTicket.setNotes(notes);
			
			List<RelatedObject> relatedObjects= new ArrayList<RelatedObject>();
			createTicket.setRelatedObjects(relatedObjects);
			
			List<RelatedParty> relatedParties=new ArrayList<RelatedParty>();
			createTicket.setRelatedParties(relatedParties);
			
			Severity severity= Severity.High;
			createTicket.setSeverity(severity);

			Status status= Status.Submitted;
			createTicket.setStatus(status);
			
			String type="Bills, charges or payment";
			createTicket.setType(type);
			
			TroubleTicket tt= client.createTroubleTicket(createTicket);
			
			LOG.debug("test createTroubleTicketTest created troubleticket="+tt);
		} catch (Exception e){
			LOG.error("test createTroubleTicketTest error:",e);
			throw new RuntimeException(e);
		}
		LOG.debug("test createTroubleTicketTest end");
	}


}
