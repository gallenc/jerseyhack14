/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2007-2014 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2014 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.netmgt.ticketer.tmforum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.opennms.api.integration.ticketing.Plugin;
import org.opennms.api.integration.ticketing.PluginException;
import org.opennms.api.integration.ticketing.Ticket;
import org.opennms.api.integration.ticketing.Ticket.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tmf.org.dsmapi.tt.Status;
import tmf.org.dsmapi.tt.TroubleTicket;
import tmf.org.dsmapi.tt.client.TroubleTicketClientV2;


/**
 * OpenNMS Trouble Ticket Plugin API implementation for TMForum interface.
 * This implementation relies on the tmforumtt REST interface and is compatible
 * with v2.
 *
 * @Author cgallen
 */
public class TmforumTicketerPlugin implements Plugin {
	private static final Logger LOG = LoggerFactory.getLogger(TmforumTicketerPlugin.class);


	protected TroubleTicketClientV2 getConnection() {
		TroubleTicketClientV2 client=null;

		String baseUri =  getProperties().getProperty("tmforumtt.baseuri");
		String username = getProperties().getProperty("tmforumtt.username");
		String password = getProperties().getProperty("tmforumtt.password");

		client = new TroubleTicketClientV2();
		client.setBaseUri(baseUri);
		client.setUsername(username);
		client.setPassword(password);

		return client;
	}

	/**
	 * Implementation of TicketerPlugin API call to retrieve a tmforumtt trouble ticket.
	 *
	 * @return an OpenNMS
	 * @throws PluginException
	 */
	@Override
	public Ticket get(String ticketId) throws PluginException {
		TroubleTicketClientV2 ttclient = getConnection();
		if (ttclient == null) return null;

		TroubleTicket issue;
		try {
			issue = ttclient.getTroubleTicket(ticketId);
		} catch (Exception e) {
			throw new PluginException("Failed to connect and get issue with id: " + ticketId, e);
		}

		if (issue != null) {
			LOG.debug("received ticket id="+ticketId+ "ticket values:-\n"+issue.toString());
			Ticket ticket = new Ticket();
			ticket.setId(issue.getId());
			ticket.setModificationTimestamp(issue.getStatusChangeDate());
			ticket.setSummary(issue.getDescription());
			ticket.setDetails(issue.getDescription());
			ticket.setState(getStateFromId(issue.getStatus()));
			return ticket;
		} else {
			LOG.debug("ticket id="+ticketId+ " no ticket found");
			return null;
		}
	}

	/**
	 * Convenience method for mapping tmforum Status values to OpenNMS status values
	 * @param status
	 * @return
	 */
	private State getStateFromId(Status status) {
		if (status == null ) return Ticket.State.OPEN;

		if (Status.Submitted.equals(status)) return Ticket.State.OPEN ;
		if (Status.Acknowledged.equals(status)) return Ticket.State.OPEN ;
		if (Status.InProgress_Held.equals(status)) return Ticket.State.OPEN ;
		if (Status.InProgress_Pending.equals(status)) return Ticket.State.OPEN ;
		if (Status.Rejected.equals(status)) return Ticket.State.OPEN ;
		if (Status.Resolved.equals(status)) return Ticket.State.CLOSED ;
		if (Status.Cancelled.equals(status)) return Ticket.State.CANCELLED;

		return Ticket.State.CLOSED;

	}

	/**
	 * Retrieves the properties defined in the tmforumtt.properties file.
	 *
	 * @return a <code>java.util.Properties object containing tmforumtt plugin defined properties
	 */
	private static Properties getProperties() {
		File home = new File(System.getProperty("opennms.home"));
		File etc = new File(home, "etc");
		File config = new File(etc, "tmforumtt.properties");

		Properties props = new Properties();

		try (InputStream in = new FileInputStream(config)) {
			props.load(in);
		} catch (IOException e) {
			LOG.error("Unable to load {} ignoring.", config, e);
		}

		LOG.debug("Loaded username:          {}", props.getProperty("tmforumtt.username"));
		LOG.debug("Loaded password:          {}", props.getProperty("tmforumtt.password"));
		LOG.debug("Loaded tmforumtt.baseuri: {}", props.getProperty("tmforumtt.baseuri"));

		return props;
	}

	/*
	 * (non-Javadoc)
	 * @see org.opennms.api.integration.ticketing.Plugin#saveOrUpdate(org.opennms.api.integration.ticketing.Ticket)
	 */
	@Override
	public void saveOrUpdate(Ticket ticket) throws PluginException {

		TroubleTicketClientV2 ttclient = getConnection();
		if (ttclient == null) throw new PluginException("cannot create trouble ticket client: ");

		if (ticket.getId() == null || ticket.getId().equals("")) {
			// If we can't find a ticket with the specified ID then create one.

			TroubleTicket newIssue= new TroubleTicket();
			//        	newIssue.setCorrelationId(correlationId);
			//        	newIssue.setCreationDate(creationDate);
			newIssue.setDescription(ticket.getDetails());
			//        	newIssue.setFieldsIN(fields);
			//        	newIssue.setId(id);
			//        	newIssue.setNotes(notes);
			//        	newIssue.setRelatedObjects(relatedObjects);
			//        	newIssue.setRelatedParties(relatedParties);
			//        	newIssue.setResolutionDate(resolutionDate);
			//        	newIssue.setSeverity(severity);
			//        	newIssue.setStatus(status);
			//        	newIssue.setStatusChangeDate(statusChangeDate);
			//        	newIssue.setStatusChangeReason(statusChangeReason);
			//        	newIssue.setSubStatus(subStatus);
			//        	newIssue.setTargetResolutionDate(targetResolutionDate);
			//        	newIssue.setType(type);

			//        	ticket.getUser();
			//        	ticket.getAlarmId();
			//        	ticket.getAttributes();
			//        	ticket.getDetails();
			//        	ticket.getId();
			//        	ticket.getIpAddress();
			//        	ticket.getModificationTimestamp();
			//        	ticket.getState();
			//        	ticket.getSummary();
			//        	ticket.getAttribute(key);
			//        	ticket.getNodeId();

			//            IssueInputBuilder builder = new IssueInputBuilder(getProperties().getProperty("tmforumtt.project"), Long.valueOf(getProperties().getProperty("tmforumtt.type").trim()));
			//            builder.setReporterName(getProperties().getProperty("tmforumtt.username"));
			//            builder.setSummary(ticket.getSummary());
			//            builder.setDescription(ticket.getDetails());
			//            builder.setDueDate(new DateTime(Calendar.getInstance()));

			//            BasicIssue createdIssue;

			TroubleTicket createdIssue;
			try {
				createdIssue = ttclient.createTroubleTicket(newIssue);
			} catch (Exception e) {
				throw new PluginException("Failed to connect and create issue: ", e);
			}
			LOG.info("created ticket :" + createdIssue);

			ticket.setId(createdIssue.getId());

		} else {
			// Otherwise update the existing ticket
			LOG.info("Received ticket: {}", ticket.getId());

			TroubleTicket issue;
			try {
				issue = ttclient.getTroubleTicket(ticket.getId());
				if (issue ==null) throw new RuntimeException("ticket not found with id="+ticket.getId());
			} catch (Exception e) {
				throw new PluginException("Failed to get issue with id:" + ticket.getId(), e);
			}

			if (Ticket.State.CLOSED.equals(ticket.getState())) {

				issue.setStatus(Status.Closed);
				issue.setStatusChangeReason("Issue resolved by OpenNMS.");
				TroubleTicket newIssue=null;
				try {
					newIssue = ttclient.updateTroubleTicket(issue);
					if (newIssue ==null) throw new RuntimeException("unable to update issue="+issue.getId());
				} catch (Exception e) {
					throw new PluginException("Failed to resolve issue with id:" + issue.getId(), e);
				}

			} else if (Ticket.State.OPEN.equals(ticket.getState())) {

				issue.setStatus(Status.InProgress_Pending);
				issue.setStatusChangeReason("Issue reopened by OpenNMS.");
				TroubleTicket newIssue=null;
				try {
					newIssue = ttclient.updateTroubleTicket(issue);
					if (newIssue ==null) throw new RuntimeException("unable to update issue="+issue.getId());
				} catch (Exception e) {
					throw new PluginException("Failed to resolve issue with id:" + issue.getId(), e);
				}

			}
		}
	}
}
