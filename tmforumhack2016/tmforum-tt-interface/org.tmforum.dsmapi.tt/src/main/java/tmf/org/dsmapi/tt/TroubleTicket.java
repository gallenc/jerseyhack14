/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.tt;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author pierregauthier {"ticket": { "id": 1, "correlationId": "TT53482",
 * "description": "Customer complaint over last invoice.", "severity": "Urgent",
 * "type": "Bills, charges or payment", "creationDate": "2013-07-23 08:16:39",
 * "targetResolutionDate": "2013-07-30 10:20:01", "status": "In Progress",
 * "subStatus": "Held", "statusChangeReason": "Waiting for invoicing expert.",
 * "statusChangeDate": "2013-07-08 08:55:12", "resolutionDate": "",
 * "relatedParties": [ { "role": "Originator", "reference": "/customer/1234" },
 * { "role": "Owner", "reference": "/operator/1234" }, { "role": "Reviser",
 * "reference": "Roger Collins" } ], "relatedObjects": [ { "involvement":
 * "Disputed", "reference": "/invoice/1234" }, { "involvement": "Adjusted",
 * "reference": "/invoice/5678" } ], "notes": [ { "date": "2013-07-19 09:55:30",
 * "author": "Arthur Evans", "text": "Already called the expert" }, { "date":
 * "2013-07-21 08:55:12", "author": "Arthur Evans", "text": "Informed the
 * originator" } ] }}
 *
 */

@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TroubleTicket implements Serializable {


	//Add other static strings as required....
    private static final long serialVersionUID = 1L;
    // Used for incremental update
    
    @JsonIgnore
    private Set<TroubleTicketField> fieldsIN;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    private String correlationId;
    private String description; 
    private Severity severity;
    private String type;
    private String creationDate;
    private String targetResolutionDate;
    private Status status;
    private SubStatus subStatus;
    private String statusChangeReason;
    private String statusChangeDate;
    private String resolutionDate;
    

    private List<RelatedObject> relatedObjects;
    
    private List<Note> notes;
    
    private List<RelatedParty> relatedParties;

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getTargetResolutionDate() {
        return targetResolutionDate;
    }

    public void setTargetResolutionDate(String targetResolutionDate) {
        this.targetResolutionDate = targetResolutionDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public SubStatus getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(SubStatus subStatus) {
        this.subStatus = subStatus;
    }

    public String getStatusChangeReason() {
        return statusChangeReason;
    }

    public void setStatusChangeReason(String statusChangeReason) {
        this.statusChangeReason = statusChangeReason;
    }

    public String getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(String statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

    public String getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(String resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public List<RelatedObject> getRelatedObjects() {
        if (relatedObjects==null || relatedObjects.isEmpty()) return null;
        else return relatedObjects;
    }

    public void setRelatedObjects(List<RelatedObject> relatedObjects) {
        this.relatedObjects = relatedObjects;
    }

    public List<Note> getNotes() {
        if (notes==null || notes.isEmpty()) return null;
        else return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<RelatedParty> getRelatedParties() {
        if (relatedParties==null || relatedParties.isEmpty()) return null;
        else return relatedParties;
    }

    public void setRelatedParties(List<RelatedParty> relatedParties) {
        this.relatedParties = relatedParties;
    }







    /**
     * @return the fieldsIN
     */
    public Set<TroubleTicketField> getFieldsIN() {
        return fieldsIN;
    }

    /**
     * @param fieldsIN the fieldsIN to set
     */
    public void setFieldsIN(Set<TroubleTicketField> fields) {
        this.fieldsIN = fields;
    }
    
    
    
    // generated code - regenerate with eclipse if class changes
	@Override
	public String toString() {
		return "TroubleTicket"
				+ "       [fieldsIN=" + fieldsIN + ",\n"
				+ "        id=" + id + ",\n"
				+ "        correlationId=" + correlationId + ",\n"
				+ "        description=" + description + ",\n"
				+ "        severity=" + severity + ",\n"
				+ "        type=" + type+ ",\n"
				+ "        creationDate=" + creationDate + ",\n"
				+ "        targetResolutionDate="+ targetResolutionDate + ",\n"
				+ "        status=" + status + ",\n"
				+ "        subStatus="	+ subStatus + ",\n"
				+ "        statusChangeReason=" + statusChangeReason+ ",\n"
				+ "        statusChangeDate=" + statusChangeDate	+ ",\n"
				+ "        resolutionDate=" + resolutionDate + ",\n"
				+ "        relatedObjects="+ relatedObjects + ",\n"
				+ "        notes=" + notes + ",\n"
				+ "        relatedParties="+ relatedParties + "\n"
				+"        ]";
	}
    
    
    
    // generated code - regenerate with eclipse if class changes
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((correlationId == null) ? 0 : correlationId.hashCode());
		result = prime * result
				+ ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((fieldsIN == null) ? 0 : fieldsIN.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result
				+ ((relatedObjects == null) ? 0 : relatedObjects.hashCode());
		result = prime * result
				+ ((relatedParties == null) ? 0 : relatedParties.hashCode());
		result = prime * result
				+ ((resolutionDate == null) ? 0 : resolutionDate.hashCode());
		result = prime * result
				+ ((severity == null) ? 0 : severity.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime
				* result
				+ ((statusChangeDate == null) ? 0 : statusChangeDate.hashCode());
		result = prime
				* result
				+ ((statusChangeReason == null) ? 0 : statusChangeReason
						.hashCode());
		result = prime * result
				+ ((subStatus == null) ? 0 : subStatus.hashCode());
		result = prime
				* result
				+ ((targetResolutionDate == null) ? 0 : targetResolutionDate
						.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

    // generated code - regenerate with eclipse if class changes
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TroubleTicket other = (TroubleTicket) obj;
		if (correlationId == null) {
			if (other.correlationId != null)
				return false;
		} else if (!correlationId.equals(other.correlationId))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (fieldsIN == null) {
			if (other.fieldsIN != null)
				return false;
		} else if (!fieldsIN.equals(other.fieldsIN))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (relatedObjects == null) {
			if (other.relatedObjects != null)
				return false;
		} else if (!relatedObjects.equals(other.relatedObjects))
			return false;
		if (relatedParties == null) {
			if (other.relatedParties != null)
				return false;
		} else if (!relatedParties.equals(other.relatedParties))
			return false;
		if (resolutionDate == null) {
			if (other.resolutionDate != null)
				return false;
		} else if (!resolutionDate.equals(other.resolutionDate))
			return false;
		if (severity != other.severity)
			return false;
		if (status != other.status)
			return false;
		if (statusChangeDate == null) {
			if (other.statusChangeDate != null)
				return false;
		} else if (!statusChangeDate.equals(other.statusChangeDate))
			return false;
		if (statusChangeReason == null) {
			if (other.statusChangeReason != null)
				return false;
		} else if (!statusChangeReason.equals(other.statusChangeReason))
			return false;
		if (subStatus != other.subStatus)
			return false;
		if (targetResolutionDate == null) {
			if (other.targetResolutionDate != null)
				return false;
		} else if (!targetResolutionDate.equals(other.targetResolutionDate))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
 

    
}
