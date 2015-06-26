package org.opennms.features.eventgateway.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * used to generate error response messages
 * error handling suggestion taken from http://www.codingpedia.org/ama/error-handling-in-rest-api-with-jersey/
 * @author cgallen@opennms.org
 *
 */
@XmlRootElement
public class EventGatewayErrorMessage {

	/**
	 * Helper constructor to build an error reply 
	 * (Error handling suggestion taken from http://www.codingpedia.org/ama/error-handling-in-rest-api-with-jersey/)
	 * @param status holds redundantly the HTTP error status code, so that the developer can “see” 
	 *        it without having to analyze the response’s header
	 * @param code this is an internal code specific to the API (should be more relevant for business exceptions)
	 * @param message short description of the error, what might have cause it and possibly a “fixing” proposal
	 * @param link points to an online resource, where more details can be found about the error
	 * @param developerMessage detailed message, containing additional data that might be relevant to the developer. 
	 *       This should only be available when the “debug” mode is 
	 *       switched on and could potentially contain stack trace information or something similar
	 * @return EventGatewayErrorMessage jaxb object to include in the xml reply
	 */
	public EventGatewayErrorMessage(int status,int code, String message, String link, String developerMessage)	{
		this.status=status;
		this.code=code;
		this.message=message;
		this.link=link;
		this.developerMessage=developerMessage;
	}

	public EventGatewayErrorMessage() {
	}

	/** contains the same HTTP Status code returned by the server */
	private int status;

	/** application specific error code */
	private int code;

	/** message describing the error*/
	private String message;

	/** link point to page where the error message is documented */
	private String link;

	/** extra information that might useful for developers */
	private String developerMessage;

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * @param status contains the same HTTP Status code returned by the server 
	 */
	@XmlElement(name = "status")
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 * @param code application specific error code
	 */
	@XmlElement(name = "code")
	public void setCode(int code) {
		this.code = code;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @param  message describing the error
	 */
	@XmlElement(name = "message")
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	
	/**
	 * @param link points to page where the error message is documented 
	 */
	@XmlElement(name = "link")
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @return the developerMessage
	 */
	public String getDeveloperMessage() {
		return developerMessage;
	}
	
	/**
	 * @param developerMessage extra information that might useful for developers
	 */
	@XmlElement(name = "developerMessage")
	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

}