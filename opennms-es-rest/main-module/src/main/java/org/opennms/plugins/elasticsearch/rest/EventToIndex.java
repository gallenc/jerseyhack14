package org.opennms.plugins.elasticsearch.rest;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.opennms.plugins.elasticsearch.rest.NodeCache;
import org.opennms.netmgt.model.OnmsSeverity;
import org.opennms.netmgt.xml.event.Event;
import org.opennms.netmgt.xml.event.Parm;

public class EventToIndex {
	
	private boolean logEventDescription=false;
    private NodeCache cache;
	
    public boolean isLogEventDescription() {
		return logEventDescription;
	}

	public void setLogEventDescription(boolean logEventDescription) {
		this.logEventDescription = logEventDescription;
	}

	public NodeCache getCache() {
		return cache;
	}

	public void setCache(NodeCache cache) {
		this.cache = cache;
	}



	   /**
     * utility method to populate a Map with the most import event attributes
     *
     * @param body the map
     * @param event the event object
     */
    private void populateBodyFromEvent(Map<String,String> body, Event event) {
        body.put("id",(event.getDbid()==null ? null: Integer.toString(event.getDbid())));
        body.put("eventuei",event.getUei());
        
        Calendar cal=Calendar.getInstance();
        cal.setTime(event.getCreationTime()); // javax.xml.bind.DatatypeConverter.parseDateTime("2010-01-01T12:00:00Z");

        body.put("@timestamp", DatatypeConverter.printDateTime(cal));

        body.put("dow", Integer.toString(cal.get(Calendar.DAY_OF_WEEK)));
        body.put("hour",Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
        body.put("dom", Integer.toString(cal.get(Calendar.DAY_OF_MONTH))); 
        body.put("eventsource", event.getSource());
        body.put("ipaddr", event.getInterfaceAddress()!=null ? event.getInterfaceAddress().toString() : null );
        body.put("servicename", event.getService());
        // params are exported as attributes, see below
        body.put("eventseverity_text", event.getSeverity());
        body.put("eventseverity", Integer.toString(OnmsSeverity.get(event.getSeverity()).getId()));

        if(isLogEventDescription()) {
            body.put("eventdescr", event.getDescr());
        }
        body.put("nodeid", Long.toString(event.getNodeid()));
        body.put("host",event.getHost());
        for(Parm parm : event.getParmCollection()) {
            body.put("p_" + parm.getParmName(), parm.getValue().getContent());
        }
        body.put("interface", event.getInterface());
        body.put("logmsg", ( event.getLogmsg()!=null ? event.getLogmsg().getContent() : null ));
        body.put("logmsgdest", ( event.getLogmsg()!=null ? event.getLogmsg().getDest() : null ));
    }
    
    private void maybeRefreshCache(Event event) {
        String uei=event.getUei();
        if(uei!=null && uei.startsWith("uei.opennms.org/nodes/")) {
            if (
                    uei.endsWith("Added")
                    || uei.endsWith("Deleted")
                    || uei.endsWith("Updated")
                    || uei.endsWith("Changed")
                    ) {
                cache.refreshEntry(event.getNodeid());
            }
        }
    }


}
