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
		LOG.debug ("obj is Event");
		List<Object> eventContent = event.getContent();
		for (Object eventContentObj: eventContent){
			if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Forward){
				//  @XmlElementRef(name = "forward", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Forward.class, required = false),
				LOG.debug ("eventContentObj is Forward.class");
				Forward forward= (Forward) eventContentObj;
				LOG.debug ("     forward.getMechanism()="+forward.getMechanism());
				LOG.debug ("     forward.getState()="+forward.getState());
				LOG.debug ("     forward.getValue()="+forward.getValue());
				//forward.getMechanism();
				//forward.getState();
				//forward.getValue();
			} else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Logmsg){
				//  @XmlElementRef(name = "logmsg", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Logmsg.class, required = false),
				LOG.debug ("eventContentObj is Logmsg");
				Logmsg logmsg= (Logmsg) eventContentObj;
				LOG.debug ("     logmsg.getDest()="+logmsg.getDest());
				LOG.debug ("     logmsg.getValue()="+logmsg.getValue());
				LOG.debug ("     logmsg.isNotify()="+logmsg.isNotify());
				// logmsg.getDest();
				// logmsg.getValue();
				// logmsg.isNotify();
			} else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Correlation){
				//  @XmlElementRef(name = "correlation", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Correlation.class, required = false),
				LOG.debug ("eventContentObj is Correlation");
				Correlation correlation= (Correlation) eventContentObj;
				LOG.debug ("     correlation.getPath()="+correlation.getPath());
				LOG.debug ("     correlation.getState()="+correlation.getState());
				LOG.debug ("     correlation.getContent()="+correlation.getContent());
				List<Serializable> content = correlation.getContent();
				for(Serializable serializable:content ){
					LOG.debug ("          correlation.getContent() serializable.toString="+serializable.toString());
				}
				// correlation.getContent();
				// correlation.getPath();
				// correlation.getState();
			}
			else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Varbindsdecode){
				//  @XmlElementRef(name = "varbindsdecode", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Varbindsdecode.class, required = false),
				LOG.debug ("eventContentObj is Varbindsdecode");
				Varbindsdecode varbindsdecodelist= (Varbindsdecode) eventContentObj;
				List<Object> varbinddecode = varbindsdecodelist.getContent();
				//@XmlElementRef(name = "parmid", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = JAXBElement.class, required = false),
				//@XmlElementRef(name = "decode", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Decode.class, required = false)
				for (Object varbinddecodeobj:varbinddecode){
					if (varbinddecodeobj instanceof org.opennms.xmlns.xsd.eventconf.Decode){
						Decode decode = (Decode)varbinddecodeobj;
						LOG.debug ("     varbindsdecode.Decode:");
						LOG.debug ("                  decode.getVarbinddecodedstring()="+decode.getVarbinddecodedstring());
						LOG.debug ("                  decode.getVarbindvalue()="+decode.getVarbindvalue());
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
			else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Snmp){
				//  @XmlElementRef(name = "snmp", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Snmp.class, required = false),
				LOG.debug ("eventContentObj is Snmp");
				Snmp snmp= (Snmp) eventContentObj;
				List<Serializable> content = snmp.getContent();
				for(Serializable serializable:content ){
					LOG.debug ("          snmp.getContent() serializable.toString="+serializable.toString());
				}
				// snmp.getContent();
			}
			else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Mask){
				//  @XmlElementRef(name = "mask", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Mask.class, required = false),
				LOG.debug ("eventContentObj is Mask");
				Mask mask= (Mask) eventContentObj;
				LOG.debug ("     mask.getMaskelement()="+mask.getMaskelement());
				List<Maskelement> maskelementlist = mask.getMaskelement();
				for(Maskelement maskelement:maskelementlist){
					LOG.debug ("         maskelement.getMename()="+maskelement.getMename());
					LOG.debug ("         maskelement.getMevalue()="+maskelement.getMevalue());
					List<String> mevalues = maskelement.getMevalue();
					for (String mevalue: mevalues){
						LOG.debug ("             maskelement.getMevalue() mevalue="+mevalue);
					}
					//							maskelement.getMename();
					//							maskelement.getMevalue();
				}
				LOG.debug ("     mask.getVarbind()="+mask.getVarbind());
				List<Varbind> varbindlist = mask.getVarbind();
				for (Varbind varbind:varbindlist){
					LOG.debug ("         varbind.getTextualConvention()="+varbind.getTextualConvention());
					List content = varbind.getContent();
					for(Object serializableObj:content ){
						if (serializableObj instanceof JAXBElement){
							LOG.debug ("         serializableObj is JAXBElement");
							JAXBElement jaxbelement = (JAXBElement) serializableObj;
							LOG.debug ("               varbind.getContent() jaxbelement.getName():"+jaxbelement.getName()+ " jaxbelement.getValue():"+jaxbelement.getValue());
						} else {
							LOG.debug ("               varbind.getContent() serializableObj.toString="+serializableObj.toString());
						}
					}
					//							varbind.getTextualConvention();
					//							varbind.getContent();
				}
				// mask.getMaskelement();
				// mask.getVarbind();
			}
			else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Operaction){
				//  @XmlElementRef(name = "operaction", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Operaction.class, required = false),
				LOG.debug ("eventContentObj is Operaction");
				Operaction operaction= (Operaction) eventContentObj;
				LOG.debug ("              operaction.getMenutext()="+operaction.getMenutext());
				LOG.debug ("              operaction.getState()="+operaction.getState());
				LOG.debug ("              operaction.getValue()="+operaction.getValue());
				// operaction.getMenutext();
				// operaction.getState();
				// operaction.getValue();
			} else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Script){
				//  @XmlElementRef(name = "script", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Script.class, required = false),
				LOG.debug ("eventContentObj is Script");
				Script script= (Script) eventContentObj;
				LOG.debug ("     script.getLanguage()="+script.getLanguage());
				LOG.debug ("     script.getValue()="+script.getValue());
				// script.getLanguage();
				// script.getValue();
			} else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Autoaction){
				//  @XmlElementRef(name = "autoaction", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Autoaction.class, required = false),
				LOG.debug ("eventContentObj is Autoaction");
				Autoaction autoaction= (Autoaction) eventContentObj;
				LOG.debug ("     script.getState()="+autoaction.getState());
				LOG.debug ("     script.getValue()="+autoaction.getValue());
				// autoaction.getState();
				// autoaction.getValue();
			}else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.AlarmData){
				//  @XmlElementRef(name = "alarm-data", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = AlarmData.class, required = false),
				LOG.debug ("eventContentObj is AlarmData");
				AlarmData alarmData= (AlarmData) eventContentObj;
				LOG.debug ("     alarmData.getAlarmType()="+alarmData.getAlarmType());
				LOG.debug ("     alarmData.getClearKey()="+alarmData.getClearKey());
				LOG.debug ("     alarmData.getReductionKey()="+alarmData.getReductionKey());
				LOG.debug ("     alarmData.getX733AlarmType()="+alarmData.getX733AlarmType());
				LOG.debug ("     alarmData.getX733ProbableCause()="+alarmData.getX733ProbableCause());
				LOG.debug ("     alarmData.isAutoClean()="+alarmData.isAutoClean());

				LOG.debug ("     alarmData.getUpdateField()="+alarmData.getUpdateField());
				List<UpdateField> updatefieldList = alarmData.getUpdateField();
				for (UpdateField updatefield:updatefieldList){
					LOG.debug ("          updatefield.getFieldName()="+updatefield.getFieldName());
					LOG.debug ("          updatefield.isUpdateOnReduction()="+updatefield.isUpdateOnReduction());
					// updatefield.getFieldName();
					// updatefield.isUpdateOnReduction();
				}

				// alarmData.getAlarmType();
				// alarmData.getClearKey();
				// alarmData.getReductionKey();
				// alarmData.getUpdateField();
				// alarmData.getX733AlarmType();
				// alarmData.getX733ProbableCause();
				// alarmData.isAutoClean();
			}else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Tticket){
				//  @XmlElementRef(name = "tticket", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Tticket.class, required = false),
				LOG.debug ("eventContentObj is Tticket");
				Tticket tticket= (Tticket) eventContentObj;
				LOG.debug ("     tticket.getState()="+tticket.getState());
				LOG.debug ("     tticket.getValue()="+tticket.getValue());
				// tticket.getState();
				// tticket.getValue();
			} else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Autoacknowledge){
				//  @XmlElementRef(name = "autoacknowledge", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Autoacknowledge.class, required = false),
				LOG.debug ("eventContentObj is Autoacknowledge");
				Autoacknowledge autoacknowledge= (Autoacknowledge) eventContentObj;
				LOG.debug ("     autoacknowledge.getState()="+autoacknowledge.getState());
				LOG.debug ("     autoacknowledge.getValue()="+autoacknowledge.getValue());
				// autoacknowledge.getState();
				// autoacknowledge.getValue();
			}else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Filters){
				//  @XmlElementRef(name = "filters", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Filters.class, required = false)
				LOG.debug ("eventContentObj is Filters");
				Filters filters= (Filters) eventContentObj;
				List<Filter> filterlist = filters.getFilter();
				for (Filter filter:filterlist){
					LOG.debug ("        filter.getEventparm()="+filter.getEventparm());
					LOG.debug ("        filter.getPattern()="+filter.getPattern());
					LOG.debug ("        filter.getReplacement()="+filter.getReplacement());
					// filter.getEventparm();
					// filter.getPattern();
					// filter.getReplacement();
				}
			} else if (eventContentObj instanceof JAXBElement){
				//  @XmlElementRef(name = "event-label", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = JAXBElement.class, required = false),
				//  @XmlElementRef(name = "descr", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = JAXBElement.class, required = false),
				//  @XmlElementRef(name = "uei", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = JAXBElement.class, required = false),
				//  @XmlElementRef(name = "mouseovertext", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = JAXBElement.class, required = false),
				//  @XmlElementRef(name = "operinstruct", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = JAXBElement.class, required = false),
				//  @XmlElementRef(name = "severity", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = JAXBElement.class, required = false),
				//  @XmlElementRef(name = "loggroup", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = JAXBElement.class, required = false),
				LOG.debug ("eventContentObj is JAXBElement");
				JAXBElement jaxbelement = (JAXBElement) eventContentObj;
				LOG.debug ("      jaxbelement.getName():"+jaxbelement.getName()+ " jaxbelement.getValue():"+jaxbelement.getValue());

			}
		} 
		
		
		
		return new  EventRowConfigObject();
	}
	
	public static Event jaxbEventFromRow(EventRowConfigObject rowObject){
		
		
		return new Event();
	}
	
	
}
