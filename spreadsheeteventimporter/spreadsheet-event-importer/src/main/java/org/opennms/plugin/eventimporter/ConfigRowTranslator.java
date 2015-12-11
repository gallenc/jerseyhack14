/* Copyright 2014 OpenNMS Group Inc., Entimoss ltd.
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

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.opennms.xmlns.xsd.eventconf.AlarmData;
import org.opennms.xmlns.xsd.eventconf.Autoacknowledge;
import org.opennms.xmlns.xsd.eventconf.Autoaction;
import org.opennms.xmlns.xsd.eventconf.Correlation;
import org.opennms.xmlns.xsd.eventconf.Decode;
import org.opennms.xmlns.xsd.eventconf.Event;
import org.opennms.xmlns.xsd.eventconf.Filter;
import org.opennms.xmlns.xsd.eventconf.Filters;
import org.opennms.xmlns.xsd.eventconf.Forward;
import org.opennms.xmlns.xsd.eventconf.Logmsg;
import org.opennms.xmlns.xsd.eventconf.Mask;
import org.opennms.xmlns.xsd.eventconf.Maskelement;
import org.opennms.xmlns.xsd.eventconf.ObjectFactory;
import org.opennms.xmlns.xsd.eventconf.Operaction;
import org.opennms.xmlns.xsd.eventconf.Script;
import org.opennms.xmlns.xsd.eventconf.Snmp;
import org.opennms.xmlns.xsd.eventconf.Tticket;
import org.opennms.xmlns.xsd.eventconf.UpdateField;
import org.opennms.xmlns.xsd.eventconf.Varbind;
import org.opennms.xmlns.xsd.eventconf.Varbindsdecode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class translates Event Row config objects into 
 * jaxb Event objects for marshalling
 * @author admin
 *
 */
public class ConfigRowTranslator {
	private static final Logger LOG = LoggerFactory.getLogger(ConfigRowTranslator.class);

	public static EventRowConfigObject rowFromJxbEvent(Event event){

		EventRowConfigObject eventRowConfigObject = new EventRowConfigObject();

		List<Object> eventContent = event.getContent();
		for (Object eventContentObj: eventContent){
			if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Forward){
				//  @XmlElementRef(name = "forward", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Forward.class, required = false),
				Forward forward= (Forward) eventContentObj;
				if (LOG.isDebugEnabled()) {
					LOG.debug ("eventContentObj is Forward.class");

					LOG.debug ("     forward.getMechanism()="+forward.getMechanism());
					LOG.debug ("     forward.getState()="+forward.getState());
					LOG.debug ("     forward.getValue()="+forward.getValue());
				}
				// BEGIN TRANSLATION
				//TODO WHAT IS FORWARD
				LOG.error("     org.opennms.xmlns.xsd.eventconf.Forward detected but not translated");
				// fyi - methods available
				//forward.getMechanism();
				//forward.getState();
				//forward.getValue();
			} else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Logmsg){
				//  @XmlElementRef(name = "logmsg", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Logmsg.class, required = false),
				Logmsg logmsg= (Logmsg) eventContentObj;
				if (LOG.isDebugEnabled()) {
					LOG.debug ("eventContentObj is Logmsg");
					LOG.debug ("     logmsg.getDest()="+logmsg.getDest());
					LOG.debug ("     logmsg.getValue()="+logmsg.getValue());
					LOG.debug ("     logmsg.isNotify()="+logmsg.isNotify());
				}

				// BEGIN TRANSLATION
				eventRowConfigObject.setEventLogmsgDest(logmsg.getDest());
				eventRowConfigObject.setEventLogmsgValue(logmsg.getValue());
				eventRowConfigObject.setEventLogmsgNotify(logmsg.isNotify());
				// fyi - methods available
				// logmsg.getDest();
				// logmsg.getValue();
				// logmsg.isNotify();
			} else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Correlation){

				//  @XmlElementRef(name = "correlation", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Correlation.class, required = false),
				Correlation correlation= (Correlation) eventContentObj;
				if (LOG.isDebugEnabled()) {
					LOG.debug ("eventContentObj is Correlation");
					LOG.debug ("     correlation.getPath()="+correlation.getPath());
					LOG.debug ("     correlation.getState()="+correlation.getState());
					LOG.debug ("     correlation.getContent()="+correlation.getContent());
					List<Serializable> content = correlation.getContent();
					for(Serializable serializableObj:content ){
						LOG.debug ("          correlation.getContent() serializableObj.toString()="+serializableObj.toString()+" serializableObj.getClass().getName():"+serializableObj.getClass().getName() );
					}
				}
				// BEGIN TRANSLATION
				//TODO WHAT IS CORRELATION
				LOG.error("     org.opennms.xmlns.xsd.eventconf.Correlation detected but not translated");
				// fyi - methods available
				// correlation.getContent();
				// correlation.getPath();
				// correlation.getState();
			}
			else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Varbindsdecode){
				//  @XmlElementRef(name = "varbindsdecode", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Varbindsdecode.class, required = false),

				Varbindsdecode varbindsdecodelist= (Varbindsdecode) eventContentObj;
				List<Object> varbinddecode = varbindsdecodelist.getContent();
				//@XmlElementRef(name = "parmid", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = JAXBElement.class, required = false),
				//@XmlElementRef(name = "decode", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Decode.class, required = false)
				if (LOG.isDebugEnabled()) {
					LOG.debug ("eventContentObj is Varbindsdecode");
					for (Object varbinddecodeobj:varbinddecode){
						if (varbinddecodeobj instanceof org.opennms.xmlns.xsd.eventconf.Decode){
							Decode decode = (Decode)varbinddecodeobj;
							LOG.debug ("     varbindsdecode.Decode:");
							LOG.debug ("                  decode.getVarbinddecodedstring()="+decode.getVarbinddecodedstring());
							LOG.debug ("                  decode.getVarbindvalue()="+decode.getVarbindvalue());
							// fyi - methods available
							// decode.getVarbinddecodedstring();
							// decode.getVarbindvalue();
						} else if (varbinddecodeobj instanceof JAXBElement){
							JAXBElement jaxbelement = (JAXBElement) varbinddecodeobj;
							LOG.debug ("     varbinddecodeobj: jaxbelement.getName():"+jaxbelement.getName()+ " jaxbelement.getValue():"+jaxbelement.getValue());
						} else {
							LOG.debug("unknown object type varbinddecodeobj= "+varbinddecodeobj.getClass().getName() +"  varbinddecodeobj.toString()"+varbinddecodeobj.toString());
						}
					}
				}
				// BEGIN TRANSLATION
				//TODO org.opennms.xmlns.xsd.eventconf.Varbindsdecode translation
				LOG.error("     org.opennms.xmlns.xsd.eventconf.Varbindsdecode detected but not translated");

			}
			else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Snmp){
				//  @XmlElementRef(name = "snmp", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Snmp.class, required = false),

				Snmp snmp= (Snmp) eventContentObj;
				List<Serializable> content = snmp.getContent();
				if (LOG.isDebugEnabled()) {
					LOG.debug ("eventContentObj is Snmp");
					for(Serializable serializableObj:content ){
						LOG.debug ("          snmp.getContent() serializableObj.toString()="+serializableObj.toString()+" serializableObj.getClass().getName():"+serializableObj.getClass().getName() );
					}
				}
				// BEGIN TRANSLATION
				//TODO org.opennms.xmlns.xsd.eventconf.Snmp translateor
				LOG.error("     org.opennms.xmlns.xsd.eventconf.Snmp detected but not translated");

				// fyi - methods available
				// snmp.getContent();
			}
			else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Mask){
				//  @XmlElementRef(name = "mask", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Mask.class, required = false),

				Mask mask= (Mask) eventContentObj;
				if (LOG.isDebugEnabled()){
					LOG.debug ("eventContentObj is Mask");
					LOG.debug ("     mask.getMaskelement()="+mask.getMaskelement());
				}
				List<Maskelement> maskelementlist = mask.getMaskelement();
				for(Maskelement maskelement:maskelementlist){
					if (LOG.isDebugEnabled()) {
						LOG.debug ("         maskelement.getMename()="+maskelement.getMename());
						LOG.debug ("         maskelement.getMevalue()="+maskelement.getMevalue());
						List<String> mevalues = maskelement.getMevalue();
						for (String mevalue: mevalues){
							LOG.debug ("             maskelement.getMevalue() mevalue="+mevalue);
						}
					}
					// BEGIN TRANSLATION
					try{ 
						if(maskelement.getMevalue().size()>1) {
							LOG.error("      cannot translate eventContentObj maskelement.getMevalue().size()>1 for maskelement.getMename(): "+maskelement.getMename()+ " maskelement.getMevalue().size():"+maskelement.getMevalue().size());
						} else {
							switch (maskelement.getMename().toString()) {
							case "id":
								eventRowConfigObject.setMaskOid(maskelement.getMevalue().get(0)); // only first value used
								break;
							case "generic":
								eventRowConfigObject.setMaskGeneric(maskelement.getMevalue().get(0)); // only first value used
								break;
							case "specific":
								eventRowConfigObject.setMaskSpecific(maskelement.getMevalue().get(0)); // only first value used
								break;
							default: 
								LOG.error("   eventContentObj unknown maskelement.getMename():"+maskelement.getMename()+ " maskelement.getMevalue():"+maskelement.getMevalue());
								break;
							}
						}
					} catch (Exception e){
						LOG.error("problem translating org.opennms.xmlns.xsd.eventconf.Mask: Exception ",e);
					}
					// fyi - methods available
					// maskelement.getMename();
					// maskelement.getMevalue();
				}
				// NOTE because mixed=true in xsd - space text strings appear to be being stored as strings

				List<Varbind> varbindlist = mask.getVarbind();

				if (LOG.isDebugEnabled()) {
					LOG.debug ("     mask.getVarbind().size()="+mask.getVarbind().size());
					for (int index=0 ; index < varbindlist.size(); index++){
						Varbind varbind=varbindlist.get(index);
						LOG.debug ("         varbindlist(index) index="+index);
						LOG.debug ("         varbind.getTextualConvention()="+varbind.getTextualConvention());
						LOG.debug ("         varbind.getContent().size()"+varbind.getContent().size());
						List content = varbind.getContent();
						for(int i=0; i<content.size(); i++ ){
							Object serializableObj=content.get(i);
							if (serializableObj instanceof JAXBElement){
								JAXBElement jaxbelement = (JAXBElement) serializableObj;
								LOG.debug ("                  varbind.getContent().get("+ i+ ") serializableObj is JAXBElement jaxbelement.getName():"+jaxbelement.getName()+ " jaxbelement.getValue():"+jaxbelement.getValue());
							} else {
								LOG.debug ("                  varbind.getContent().get("+ i+ ") serializableObj is unknown type serializableObj.toString()="+serializableObj.toString()+" serializableObj.getClass().getName():"+serializableObj.getClass().getName() );
							}
						}
						// fyi - methods available
						// varbind.getTextualConvention();
						// varbind.getContent();
					}

					LOG.debug ("   BEGINNING varbind translation");
				}
				// BEGIN TRANSLATION
				try{
					// extract varbind definitions from XSD classes
					RowVarbindConfig rowVbConfig= new RowVarbindConfig();
					for (int index=0 ; index < varbindlist.size(); index++){
						Varbind varbind=varbindlist.get(index);
						List content = varbind.getContent();
						RowVarbindEntry rowvbentry= new RowVarbindEntry();
						for(int i=0; i<content.size(); i++ ){
							Object serializableObj=content.get(i);
							if (serializableObj instanceof JAXBElement){
								JAXBElement jaxbelement = (JAXBElement) serializableObj;
								switch (jaxbelement.getName().toString()) {
								case "{http://xmlns.opennms.org/xsd/eventconf}vbnumber":
									// note only one vb number per entry
									if(rowvbentry.getNumber()!=null) LOG.error("more than one varbind number defined in a varbind config");
									rowvbentry.setNumber(jaxbelement.getValue().toString());
									break;
								case "{http://xmlns.opennms.org/xsd/eventconf}vbvalue":
									eventRowConfigObject.setMaskVarbind_1_value(jaxbelement.getValue().toString()); 
									rowvbentry.getValues().add(jaxbelement.getValue().toString());
									break;
								default: 
									// String values included because of mixed=true in xsd 
									break;
								}
							} 
						}
						rowVbConfig.getVarbindEntries().add(rowvbentry);

					}

					// only 2 varbinds considered for spreadsheet
					if (rowVbConfig.getVarbindEntries().size()>2){
						LOG.error("   eventContentObj more than 2 varbinds defined in list: rowVbConfig.getVarbindEntries().size())="+rowVbConfig.getVarbindEntries().size());
					}

					// fixed number of values in spreadsheet
					if(rowVbConfig.getVarbindEntries().size()>=1){
						eventRowConfigObject.setMaskVarbind_1_number(rowVbConfig.getVarbindEntries().get(0).getNumber());
						eventRowConfigObject.setMaskVarbind_1_value(rowVbConfig.getVarbindEntries().get(0).getValues().get(0));
					}
					if(rowVbConfig.getVarbindEntries().size()>=2){
						eventRowConfigObject.setMaskVarbind_2_number(rowVbConfig.getVarbindEntries().get(1).getNumber());
						eventRowConfigObject.setMaskVarbind_2_value(rowVbConfig.getVarbindEntries().get(1).getValues().get(0));
					}

				} catch (Exception e){
					LOG.error("problem translating org.opennms.xmlns.xsd.eventconf.Mask: Exception ",e);
				}
			}
			else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Operaction){
				//  @XmlElementRef(name = "operaction", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Operaction.class, required = false),
				Operaction operaction= (Operaction) eventContentObj;
				if (LOG.isDebugEnabled()) {
					LOG.debug ("eventContentObj is Operaction");

					LOG.debug ("              operaction.getMenutext()="+operaction.getMenutext());
					LOG.debug ("              operaction.getState()="+operaction.getState());
					LOG.debug ("              operaction.getValue()="+operaction.getValue());
				}
				// BEGIN TRANSLATION
				//TODO WHAT IS OPERACTION
				LOG.error("     org.opennms.xmlns.xsd.eventconf.Operaction detected but not translated");

				// fyi - methods available
				// operaction.getMenutext();
				// operaction.getState();
				// operaction.getValue();
			} else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Script){

				//  @XmlElementRef(name = "script", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Script.class, required = false),
				Script script= (Script) eventContentObj;
				if (LOG.isDebugEnabled()) {
					LOG.debug ("eventContentObj is Script");
					LOG.debug ("     script.getLanguage()="+script.getLanguage());
					LOG.debug ("     script.getValue()="+script.getValue());
				}
				// BEGIN TRANSLATION
				//TODO script not translated
				LOG.error("     org.opennms.xmlns.xsd.eventconf.Script detected but not translated");
				// fyi - methods available
				// script.getLanguage();
				// script.getValue();
			} else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Autoaction){
				//  @XmlElementRef(name = "autoaction", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Autoaction.class, required = false),
				Autoaction autoaction= (Autoaction) eventContentObj;
				if (LOG.isDebugEnabled()) {
					LOG.debug ("eventContentObj is Autoaction");


					LOG.debug ("     script.getState()="+autoaction.getState());
					LOG.debug ("     script.getValue()="+autoaction.getValue());
				}
				// BEGIN TRANSLATION
				//TODO WHAT IS AUTOACTION
				LOG.error("     org.opennms.xmlns.xsd.eventconf.Autoaction detected but not translated");

				// fyi - methods available
				// autoaction.getState();
				// autoaction.getValue();
			}else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.AlarmData){
				//  @XmlElementRef(name = "alarm-data", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = AlarmData.class, required = false),
				AlarmData alarmData= (AlarmData) eventContentObj;
				if (LOG.isDebugEnabled()) {
					LOG.debug ("eventContentObj is AlarmData");
					LOG.debug ("     alarmData.getAlarmType()="+alarmData.getAlarmType());
					LOG.debug ("     alarmData.getClearKey()="+alarmData.getClearKey());
					LOG.debug ("     alarmData.getReductionKey()="+alarmData.getReductionKey());
					LOG.debug ("     alarmData.getX733AlarmType()="+alarmData.getX733AlarmType());
					LOG.debug ("     alarmData.getX733ProbableCause()="+alarmData.getX733ProbableCause());
					LOG.debug ("     alarmData.isAutoClean()="+alarmData.isAutoClean());
				}
				// BEGIN TRANSLATION
				try {
					eventRowConfigObject.setAlarmType(alarmData.getAlarmType());
					eventRowConfigObject.setAlarmClearKey(alarmData.getClearKey());
					eventRowConfigObject.setAlarmReductionKey(alarmData.getReductionKey());
					//TODO alarmData.getUpdateField(); NOT TRANSLATED
					//TODO alarmData.getX733AlarmType(); NOT TRANSLATED
					//TODO alarmData.getX733ProbableCause(); NOT TRANSLATED
					eventRowConfigObject.setAlarmAutoClean(alarmData.isAutoClean());

					// fyi - methods available
					// alarmData.getAlarmType();
					// alarmData.getClearKey();
					// alarmData.getReductionKey();
					// alarmData.getUpdateField();
					// alarmData.getX733AlarmType();
					// alarmData.getX733ProbableCause();
					// alarmData.isAutoClean();
					if (LOG.isDebugEnabled()) {
						LOG.debug ("     alarmData.getUpdateField()="+alarmData.getUpdateField());
						//TODO alarmData.getUpdateField(); NOT TRANSLATED
						if(alarmData.getUpdateField()!=null && alarmData.getUpdateField().size()!=0) {
							LOG.error("     alarmData.getUpdateField() detected but not translated");
						}
						List<UpdateField> updatefieldList = alarmData.getUpdateField();
						for (UpdateField updatefield:updatefieldList){
							LOG.debug ("          updatefield.getFieldName()="+updatefield.getFieldName());
							LOG.debug ("          updatefield.isUpdateOnReduction()="+updatefield.isUpdateOnReduction());
							// fyi - methods available
							// updatefield.getFieldName();
							// updatefield.isUpdateOnReduction();
						}
					}
				} catch (Exception e){
					LOG.error("problem translating org.opennms.xmlns.xsd.eventconf.AlarmData: Exception ",e);
				}

			}else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Tticket){
				//  @XmlElementRef(name = "tticket", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Tticket.class, required = false),
				Tticket tticket= (Tticket) eventContentObj;
				if (LOG.isDebugEnabled()) {
					LOG.debug ("eventContentObj is Tticket");

					LOG.debug ("     tticket.getState()="+tticket.getState());
					LOG.debug ("     tticket.getValue()="+tticket.getValue());
				}
				// BEGIN TRANSLATION
				//TODO trouble ticket translation
				LOG.error("     org.opennms.xmlns.xsd.eventconf.Tticket detected but not translated");

				// fyi - methods available
				// tticket.getState();
				// tticket.getValue();
			} else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Autoacknowledge){
				//  @XmlElementRef(name = "autoacknowledge", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Autoacknowledge.class, required = false),
				Autoacknowledge autoacknowledge= (Autoacknowledge) eventContentObj;
				if (LOG.isDebugEnabled()) {
					LOG.debug ("eventContentObj is Autoacknowledge");
					LOG.debug ("     autoacknowledge.getState()="+autoacknowledge.getState());
					LOG.debug ("     autoacknowledge.getValue()="+autoacknowledge.getValue());
				}
				// BEGIN TRANSLATION
				//TODO org.opennms.xmlns.xsd.eventconf.Autoacknowledge translation
				LOG.error("     org.opennms.xmlns.xsd.eventconf.Autoacknowledge detected but not translated");
				// fyi - methods available
				// autoacknowledge.getState();
				// autoacknowledge.getValue();
			}else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Filters){
				//  @XmlElementRef(name = "filters", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Filters.class, required = false)
				if (LOG.isDebugEnabled()) {
					LOG.debug ("eventContentObj is Filters");
					Filters filters= (Filters) eventContentObj;
					List<Filter> filterlist = filters.getFilter();
					for (Filter filter:filterlist){
						LOG.debug ("        filter.getEventparm()="+filter.getEventparm());
						LOG.debug ("        filter.getPattern()="+filter.getPattern());
						LOG.debug ("        filter.getReplacement()="+filter.getReplacement());
						// fyi - methods available
						// filter.getEventparm();
						// filter.getPattern();
						// filter.getReplacement();
					}
				}
				// BEGIN TRANSLATION
				//TODO WHAT IS FILTERS
				LOG.error("     org.opennms.xmlns.xsd.eventconf.Filters detected but not translated");

			} else if (eventContentObj instanceof JAXBElement){
				//  @XmlElementRef(name = "event-label", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = JAXBElement.class, required = false),
				//  @XmlElementRef(name = "descr", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = JAXBElement.class, required = false),
				//  @XmlElementRef(name = "uei", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = JAXBElement.class, required = false),
				//  @XmlElementRef(name = "mouseovertext", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = JAXBElement.class, required = false),
				//  @XmlElementRef(name = "operinstruct", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = JAXBElement.class, required = false),
				//  @XmlElementRef(name = "severity", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = JAXBElement.class, required = false),
				//  @XmlElementRef(name = "loggroup", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = JAXBElement.class, required = false),
				JAXBElement jaxbelement = (JAXBElement) eventContentObj;

				if (LOG.isDebugEnabled()) {
					LOG.debug ("eventContentObj is JAXBElement");
					LOG.debug ("      jaxbelement.getName():"+jaxbelement.getName()+ " jaxbelement.getValue():"+jaxbelement.getValue());
				}
				// BEGIN TRANSLATION
				try{
					switch (jaxbelement.getName().toString()) {
					case "{http://xmlns.opennms.org/xsd/eventconf}event-label":
						eventRowConfigObject.setEventLabel(jaxbelement.getValue().toString());
						break;
					case "{http://xmlns.opennms.org/xsd/eventconf}descr":
						eventRowConfigObject.setEventDescr(jaxbelement.getValue().toString());
						break;
					case "{http://xmlns.opennms.org/xsd/eventconf}uei":
						eventRowConfigObject.setEventUei(jaxbelement.getValue().toString());
						break;
					case "{http://xmlns.opennms.org/xsd/eventconf}mouseovertext":
						eventRowConfigObject.setEventMouseOverText(jaxbelement.getValue().toString());
						break;
					case "{http://xmlns.opennms.org/xsd/eventconf}operinstruct":
						eventRowConfigObject.setEventOperInstruct(jaxbelement.getValue().toString());
						break;
					case "{http://xmlns.opennms.org/xsd/eventconf}severity":
						eventRowConfigObject.setEventSeverity(jaxbelement.getValue().toString());
						break;
					case "{http://xmlns.opennms.org/xsd/eventconf}loggroup":
						eventRowConfigObject.setEventLoggroup(jaxbelement.getValue().toString());
						break;
					default: 
						LOG.error("   eventContentObj unknown JAXBElement jaxbelement.getName():"+jaxbelement.getName()+ " jaxbelement.getValue():"+jaxbelement.getValue());
						break;
					}
				} catch (Exception e){
					LOG.error("problem translating JAXBElement: Exception ",e);
				}

			}
		} 

		return eventRowConfigObject;
	}

	public static Event jaxbEventFromRow(EventRowConfigObject rowObject){

		ObjectFactory objectFactory= new ObjectFactory();

		Event event = objectFactory.createEvent();


		try {

			List<Object> eventContent = event.getContent();

			//event mask - only created if an oid exists
			if(rowObject.getMaskOid()!=null){
				Mask mask = objectFactory.createMask();
				eventContent.add(mask);

				// oid
				Maskelement oidMaskElement = objectFactory.createMaskelement();
				oidMaskElement.setMename("id");
				oidMaskElement.getMevalue().add(rowObject.getMaskOid());
				mask.getMaskelement().add(oidMaskElement);

				// generic
				if(rowObject.getMaskGeneric()!=null){
					Maskelement genericMaskElement = objectFactory.createMaskelement();
					genericMaskElement.setMename("generic");
					genericMaskElement.getMevalue().add(rowObject.getMaskGeneric());
					mask.getMaskelement().add(genericMaskElement);
				}

				// specific
				if(rowObject.getMaskSpecific()!=null){
					Maskelement specificMaskElement = objectFactory.createMaskelement();
					specificMaskElement.setMename("specific");
					specificMaskElement.getMevalue().add(rowObject.getMaskSpecific());
					mask.getMaskelement().add(specificMaskElement);
				}

				// varbinds ( only 2 varbinds supported)
				if(rowObject.getMaskVarbind_1_number()!=null){

					JAXBElement<Integer> vbnumber = objectFactory.createVarbindVbnumber(Integer.valueOf(rowObject.getMaskVarbind_1_number()));
					JAXBElement<String> vbvalue = objectFactory.createVarbindVbvalue(rowObject.getMaskVarbind_1_value());

					Varbind varbind_1 = objectFactory.createVarbind();
					varbind_1.getContent().add(vbnumber);
					varbind_1.getContent().add(vbvalue);
					mask.getVarbind().add(varbind_1);

				}

				if(rowObject.getMaskVarbind_2_number()!=null){

					JAXBElement<Integer> vbnumber = objectFactory.createVarbindVbnumber(Integer.valueOf(rowObject.getMaskVarbind_2_number()));
					JAXBElement<String> vbvalue = objectFactory.createVarbindVbvalue(rowObject.getMaskVarbind_2_value());

					Varbind varbind_2 = objectFactory.createVarbind();
					varbind_2.getContent().add(vbnumber);
					varbind_2.getContent().add(vbvalue);
					mask.getVarbind().add(varbind_2);
				}
			}

			// Event string variables
			if(rowObject.getEventUei()!=null){
				eventContent.add(objectFactory.createEventUei(rowObject.getEventUei()));
			}
			if(rowObject.getEventMouseOverText()!=null){
				eventContent.add(objectFactory.createEventMouseovertext(rowObject.getEventMouseOverText()));
			}
			if(rowObject.getEventOperInstruct()!=null){
				eventContent.add(objectFactory.createEventOperinstruct(rowObject.getEventOperInstruct()));
			}
			if(rowObject.getEventSeverity()!=null){
				eventContent.add(objectFactory.createEventSeverity(rowObject.getEventSeverity()));
			}
			if(rowObject.getEventDescr()!=null){
				eventContent.add(objectFactory.createEventDescr(rowObject.getEventDescr()));
			}
			if(rowObject.getEventLabel()!=null){
				eventContent.add(objectFactory.createEventEventLabel(rowObject.getEventLabel()));
			}

			// logmessage

			rowObject.getEventLogmsgValue();

		}catch (Exception e){
			LOG.error("jaxbEventFromRow cannot populate event: ",e);
		}

		return event;
	}


}
