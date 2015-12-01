/*
 * Copyright 2014 OpenNMS Group Inc., Entimoss ltd.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opennms.plugin.eventimporter;

/**
 * This class is used as a value type to transfer data between 
 * a spreadsheet row and an event definition
 * @author admin
 *
 */
public class EventRowConfigObject {

	/*
	 *  Event / Alarm selector
	 *  for each mask element
	 *  note we limit mask elements to flatten max number 
	 */
	private String maskOid=null;    //   <maskelement><mename>id</mename><mevalue>.1.3.6.1.4.1.9.9.70.2</mevalue></maskelement>
	private String maskGeneric=null; //  <maskelement><mename>generic</mename><mevalue>6</mevalue></maskelement>
	private String maskSpecific=null; // <maskelement><mename>specific</mename><mevalue>17</mevalue></maskelement>

	/*
	 * varbinds - spreadsheets can only define 2 varbinds per event
	 * <varbind>
	 *  <vbnumber>3</vbnumber>
	 *  <vbvalue>3</vbvalue>
	 * </varbind>
	 */
	private String maskVarbind_1_number=null; // <varbind><vbnumber>3</vbnumber><vbvalue>3</vbvalue></varbind>
	private String maskVarbind_1_value=null;

	private String maskVarbind_2_number=null; // <varbind><vbnumber>3</vbnumber><vbvalue>3</vbvalue></varbind>
	private String maskVarbind_2_value=null;
	
	/*  not using varbindsdecode
	 *     <varbindsdecode> <!-- decoding varbinds to text for message -->
	 *       <parmid>parm[#1]</parmid> 
	 *       <decode varbindvalue="1" varbinddecodedstring="initial" />
	 *       <decode varbindvalue="2" varbinddecodedstring="learn" />
	 *     </varbindsdecode>
	 */	

	/*
	 * Event / Alarm Definition
	 */

	//  @XmlElementRef(name = "uei"\n    namespace = "http://xmlns.opennms.org/xsd/eventconf"\n    type = JAXBElement.class, required = false),
	private String eventUei= null; // <uei>http://uei.opennms.org/vendor/Cisco/traps/ciscoC3800SysAggregateStatusChange</uei>

	/* Alarm Data
	 * 	  <alarm-data reduction-key="%uei%:%dpname%:%nodeid%:%interface%:%parm[#1]%:%parm[#2]%:%parm[#3]%" 
	 *            alarm-type="2" 
	 *            auto-clean="true" 
	 *            clear-key="uei.opennms.org/vendor/adtran/traps/adATLASHSSIV35IfceDeact:%dpname%:%nodeid%:%interface%:%parm[#1]%:%parm[#2]%:%parm[#3]%" />
	 */
	private String  alarmReductionKey=null;
	private Integer alarmType=null; // 1,2,3
	private String  alarmClearKey=null;
	private Boolean alarmAutoClean=null;

	/*
	 *  Event / Alarm Text definitions
	 */

	//  @XmlElementRef(name = "event-label"\n    namespace = "http://xmlns.opennms.org/xsd/eventconf"\n    type = JAXBElement.class, required = false),
	private String eventLabel=null; // <event-label></event-label>

	//  @XmlElementRef(name = "descr"\n    namespace = "http://xmlns.opennms.org/xsd/eventconf"\n    type = JAXBElement.class, required = false),
	private String eventDescr=null; // <descr></descr>

	//  @XmlElementRef(name = "mouseovertext"\n    namespace = "http://xmlns.opennms.org/xsd/eventconf"\n    type = JAXBElement.class, required = false),
	private String eventMouseOverText=null; // <mouseovertext></mouseovertext>

	//  @XmlElementRef(name = "operinstruct"\n    namespace = "http://xmlns.opennms.org/xsd/eventconf"\n    type = JAXBElement.class, required = false),
	private String eventOperInstruct=null; // <operinstruct></operinstruct>

	//  @XmlElementRef(name = "severity"\n    namespace = "http://xmlns.opennms.org/xsd/eventconf"\n    type = JAXBElement.class, required = false),
	private String eventSeverity=null; // <severity>Indeterminate</severity>

	//  @XmlElementRef(name = "loggroup"\n    namespace = "http://xmlns.opennms.org/xsd/eventconf"\n    type = JAXBElement.class, required = false),
	private String eventLoggroup=null; //<loggroup></loggroup>

	// event / alarm description
	// (logndisplay|displayonly|logonly|suppress|donotpersist|discardtraps)
	private String 	eventLogmsgDest=null; // <logmsg dest='logndisplay'><p>Cisco Event: HSRP State Change to %parm[#1]%.</p></logmsg>
	private String  eventLogmsgValue=null;
	private Boolean eventLogmsgNotify=null;
	/**
	 * @return the maskOid
	 */
	public String getMaskOid() {
		return maskOid;
	}
	/**
	 * @param maskOid the maskOid to set
	 */
	public void setMaskOid(String maskOid) {
		this.maskOid = maskOid;
	}
	/**
	 * @return the maskGeneric
	 */
	public String getMaskGeneric() {
		return maskGeneric;
	}
	/**
	 * @param maskGeneric the maskGeneric to set
	 */
	public void setMaskGeneric(String maskGeneric) {
		this.maskGeneric = maskGeneric;
	}
	/**
	 * @return the maskSpecific
	 */
	public String getMaskSpecific() {
		return maskSpecific;
	}
	/**
	 * @param maskSpecific the maskSpecific to set
	 */
	public void setMaskSpecific(String maskSpecific) {
		this.maskSpecific = maskSpecific;
	}
	/**
	 * @return the maskVarbind_1_number
	 */
	public String getMaskVarbind_1_number() {
		return maskVarbind_1_number;
	}
	/**
	 * @param maskVarbind_1_number the maskVarbind_1_number to set
	 */
	public void setMaskVarbind_1_number(String maskVarbind_1_number) {
		this.maskVarbind_1_number = maskVarbind_1_number;
	}
	/**
	 * @return the maskVarbind_1_value
	 */
	public String getMaskVarbind_1_value() {
		return maskVarbind_1_value;
	}
	/**
	 * @param maskVarbind_1_value the maskVarbind_1_value to set
	 */
	public void setMaskVarbind_1_value(String maskVarbind_1_value) {
		this.maskVarbind_1_value = maskVarbind_1_value;
	}
	/**
	 * @return the maskVarbind_2_number
	 */
	public String getMaskVarbind_2_number() {
		return maskVarbind_2_number;
	}
	/**
	 * @param maskVarbind_2_number the maskVarbind_2_number to set
	 */
	public void setMaskVarbind_2_number(String maskVarbind_2_number) {
		this.maskVarbind_2_number = maskVarbind_2_number;
	}
	/**
	 * @return the maskVarbind_2_value
	 */
	public String getMaskVarbind_2_value() {
		return maskVarbind_2_value;
	}
	/**
	 * @param maskVarbind_2_value the maskVarbind_2_value to set
	 */
	public void setMaskVarbind_2_value(String maskVarbind_2_value) {
		this.maskVarbind_2_value = maskVarbind_2_value;
	}
	/**
	 * @return the eventUei
	 */
	public String getEventUei() {
		return eventUei;
	}
	/**
	 * @param eventUei the eventUei to set
	 */
	public void setEventUei(String eventUei) {
		this.eventUei = eventUei;
	}
	/**
	 * @return the alarmReductionKey
	 */
	public String getAlarmReductionKey() {
		return alarmReductionKey;
	}
	/**
	 * @param alarmReductionKey the alarmReductionKey to set
	 */
	public void setAlarmReductionKey(String alarmReductionKey) {
		this.alarmReductionKey = alarmReductionKey;
	}
	/**
	 * @return the alarmType
	 */
	public Integer getAlarmType() {
		return alarmType;
	}
	/**
	 * @param alarmType the alarmType to set
	 */
	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}
	/**
	 * @return the alarmClearKey
	 */
	public String getAlarmClearKey() {
		return alarmClearKey;
	}
	/**
	 * @param alarmClearKey the alarmClearKey to set
	 */
	public void setAlarmClearKey(String alarmClearKey) {
		this.alarmClearKey = alarmClearKey;
	}
	/**
	 * @return the alarmAutoClean
	 */
	public Boolean getAlarmAutoClean() {
		return alarmAutoClean;
	}
	/**
	 * @param alarmAutoClean the alarmAutoClean to set
	 */
	public void setAlarmAutoClean(Boolean alarmAutoClean) {
		this.alarmAutoClean = alarmAutoClean;
	}
	/**
	 * @return the eventLabel
	 */
	public String getEventLabel() {
		return eventLabel;
	}
	/**
	 * @param eventLabel the eventLabel to set
	 */
	public void setEventLabel(String eventLabel) {
		this.eventLabel = eventLabel;
	}
	/**
	 * @return the eventDescr
	 */
	public String getEventDescr() {
		return eventDescr;
	}
	/**
	 * @param eventDescr the eventDescr to set
	 */
	public void setEventDescr(String eventDescr) {
		this.eventDescr = eventDescr;
	}
	/**
	 * @return the eventMouseOverText
	 */
	public String getEventMouseOverText() {
		return eventMouseOverText;
	}
	/**
	 * @param eventMouseOverText the eventMouseOverText to set
	 */
	public void setEventMouseOverText(String eventMouseOverText) {
		this.eventMouseOverText = eventMouseOverText;
	}
	/**
	 * @return the eventOperInstruct
	 */
	public String getEventOperInstruct() {
		return eventOperInstruct;
	}
	/**
	 * @param eventOperInstruct the eventOperInstruct to set
	 */
	public void setEventOperInstruct(String eventOperInstruct) {
		this.eventOperInstruct = eventOperInstruct;
	}
	/**
	 * @return the eventSeverity
	 */
	public String getEventSeverity() {
		return eventSeverity;
	}
	/**
	 * @param eventSeverity the eventSeverity to set
	 */
	public void setEventSeverity(String eventSeverity) {
		this.eventSeverity = eventSeverity;
	}
	/**
	 * @return the eventLoggroup
	 */
	public String getEventLoggroup() {
		return eventLoggroup;
	}
	/**
	 * @param eventLoggroup the eventLoggroup to set
	 */
	public void setEventLoggroup(String eventLoggroup) {
		this.eventLoggroup = eventLoggroup;
	}
	/**
	 * @return the eventLogmsgDest
	 */
	public String getEventLogmsgDest() {
		return eventLogmsgDest;
	}
	/**
	 * @param eventLogmsgDest the eventLogmsgDest to set
	 */
	public void setEventLogmsgDest(String eventLogmsgDest) {
		this.eventLogmsgDest = eventLogmsgDest;
	}
	/**
	 * @return the eventLogmsgValue
	 */
	public String getEventLogmsgValue() {
		return eventLogmsgValue;
	}
	/**
	 * @param eventLogmsgValue the eventLogmsgValue to set
	 */
	public void setEventLogmsgValue(String eventLogmsgValue) {
		this.eventLogmsgValue = eventLogmsgValue;
	}
	/**
	 * @return the eventLogmsgNotify
	 */
	public Boolean getEventLogmsgNotify() {
		return eventLogmsgNotify;
	}
	/**
	 * @param eventLogmsgNotify the eventLogmsgNotify to set
	 */
	public void setEventLogmsgNotify(Boolean eventLogmsgNotify) {
		this.eventLogmsgNotify = eventLogmsgNotify;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EventRowConfigObject [");
		builder.append("\n    maskOid=");
		builder.append(maskOid);
		builder.append("\n    maskGeneric=");
		builder.append(maskGeneric);
		builder.append("\n    maskSpecific=");
		builder.append(maskSpecific);
		builder.append("\n    maskVarbind_1_number=");
		builder.append(maskVarbind_1_number);
		builder.append("\n    maskVarbind_1_value=");
		builder.append(maskVarbind_1_value);
		builder.append("\n    maskVarbind_2_number=");
		builder.append(maskVarbind_2_number);
		builder.append("\n    maskVarbind_2_value=");
		builder.append(maskVarbind_2_value);
		builder.append("\n    eventUei=");
		builder.append(eventUei);
		builder.append("\n    alarmReductionKey=");
		builder.append(alarmReductionKey);
		builder.append("\n    alarmType=");
		builder.append(alarmType);
		builder.append("\n    alarmClearKey=");
		builder.append(alarmClearKey);
		builder.append("\n    alarmAutoClean=");
		builder.append(alarmAutoClean);
		builder.append("\n    eventLabel=");
		builder.append(eventLabel);
		builder.append("\n    eventDescr=");
		builder.append(eventDescr);
		builder.append("\n    eventMouseOverText=");
		builder.append(eventMouseOverText);
		builder.append("\n    eventOperInstruct=");
		builder.append(eventOperInstruct);
		builder.append("\n    eventSeverity=");
		builder.append(eventSeverity);
		builder.append("\n    eventLoggroup=");
		builder.append(eventLoggroup);
		builder.append("\n    eventLogmsgDest=");
		builder.append(eventLogmsgDest);
		builder.append("\n    eventLogmsgValue=");
		builder.append(eventLogmsgValue);
		builder.append("\n    eventLogmsgNotify=");
		builder.append(eventLogmsgNotify);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
