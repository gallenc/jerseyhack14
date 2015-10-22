package org.opennms.features.pluginmgr.vaadin.pluginmanager;

import com.vaadin.ui.TextArea;

/**
 * used to manage user feedback and logging messages
 * @author admin
 *
 */
public class SystemMessages {

	private String message="";
	private TextArea messagePanel=null;
	
	public synchronized void setValue(String message){
		this.message=message;
		if (messagePanel!= null)  messagePanel.setValue(message);
	}

	/**
	 * @return the message
	 */
	public synchronized String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public synchronized void setMessage(String message) {
		this.message = message;
		if (messagePanel!= null)  messagePanel.setValue(message);
	}

	/**
	 * @return the messagePanel
	 */
	public synchronized TextArea getMessagePanel() {
		return messagePanel;
	}

	/**
	 * @param messagePanel the messagePanel to set
	 */
	public synchronized void setMessageTextArea(TextArea messagePanel) {
		this.messagePanel = messagePanel;
	}
}
