package org.opennms.plugin.eventimporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElementRef;

import org.junit.Test;

import java.util.List;
import java.util.Scanner;

import org.opennms.xmlns.xsd.eventconf.AlarmData;
import org.opennms.xmlns.xsd.eventconf.Autoacknowledge;
import org.opennms.xmlns.xsd.eventconf.Autoaction;
import org.opennms.xmlns.xsd.eventconf.Correlation;
import org.opennms.xmlns.xsd.eventconf.Decode;
import org.opennms.xmlns.xsd.eventconf.Event;
import org.opennms.xmlns.xsd.eventconf.Events;
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


public class MarshallerTest {

	private String eventFileName = "testeventfile.xml";

	private String marshalledEvents=null;

	private Events eventsConfig=null;

	@Test 
	public void allTestsInOrder(){
		System.out.println("@Test - allTestsInOrder().START");
		marshallerTest();
		unmarshallerTest();
		extractXmlElementTest();
		System.out.println("@Test - allTestsInOrder().END");
	}

	//@Test
	public void marshallerTest(){
		System.out.println("@Test - marshallerTest().START");

		ClassLoader classLoader = getClass().getClassLoader();
		File eventsFile = new File(classLoader.getResource(eventFileName).getFile());

		StringBuilder fileContents = new StringBuilder((int)eventsFile .length());

		Scanner scanner=null;
		try {
			scanner = new Scanner(eventsFile );
			String lineSeparator = System.getProperty("line.separator");
			while(scanner.hasNextLine()) {        
				fileContents.append(scanner.nextLine() + lineSeparator);
			}

			marshalledEvents=fileContents.toString();
			System.out.println("Config file contents marshalledEvents: "+marshalledEvents);

			eventsConfig = eventsFromXml(marshalledEvents);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (scanner != null) scanner.close();
		}
		System.out.println("@Test - marshallerTest().FINISH");
	}

	//@Test
	public void unmarshallerTest(){
		System.out.println("@Test - unmarshallerTest().START");

		String unmarshalledEvents = eventsToXml(eventsConfig);

		System.out.println("umarshalledEvents: "+unmarshalledEvents);

		System.out.println("@Test - unmarshallerTest().FINISH");
	}


	public void extractXmlElementTest(){
		System.out.println("@Test - extractXmlElementTest().START");

		List<Object> eventsContent = eventsConfig.getContent();
		for( Object obj: eventsContent){
			System.out.println (obj);
			if (obj instanceof org.opennms.xmlns.xsd.eventconf.Event){
				System.out.println ("obj is Event");
				Event event= (Event) obj;
				List<Object> eventContent = event.getContent();
				for (Object eventContentObj: eventContent){
					if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Forward){
						//  @XmlElementRef(name = "forward", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Forward.class, required = false),
						System.out.println ("eventContentObj is Forward.class");
						Forward forward= (Forward) eventContentObj;
						System.out.println ("     forward.getMechanism()="+forward.getMechanism());
						System.out.println ("     forward.getState()="+forward.getState());
						System.out.println ("     forward.getValue()="+forward.getValue());
						//forward.getMechanism();
						//forward.getState();
						//forward.getValue();
					} else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Logmsg){
						//  @XmlElementRef(name = "logmsg", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Logmsg.class, required = false),
						System.out.println ("eventContentObj is Logmsg");
						Logmsg logmsg= (Logmsg) eventContentObj;
						System.out.println ("     logmsg.getDest()="+logmsg.getDest());
						System.out.println ("     logmsg.getValue()="+logmsg.getValue());
						System.out.println ("     logmsg.isNotify()="+logmsg.isNotify());
						// logmsg.getDest();
						// logmsg.getValue();
						// logmsg.isNotify();
					} else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Correlation){
						//  @XmlElementRef(name = "correlation", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Correlation.class, required = false),
						System.out.println ("eventContentObj is Correlation");
						Correlation correlation= (Correlation) eventContentObj;
						System.out.println ("     correlation.getPath()="+correlation.getPath());
						System.out.println ("     correlation.getState()="+correlation.getState());
						System.out.println ("     correlation.getContent()="+correlation.getContent());
						List<Serializable> content = correlation.getContent();
						for(Serializable serializable:content ){
							System.out.println ("          correlation.getContent() serializable.toString="+serializable.toString());
						}
						// correlation.getContent();
						// correlation.getPath();
						// correlation.getState();
					}
					else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Varbindsdecode){
						//  @XmlElementRef(name = "varbindsdecode", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Varbindsdecode.class, required = false),
						System.out.println ("eventContentObj is Varbindsdecode");
						Varbindsdecode varbindsdecodelist= (Varbindsdecode) eventContentObj;
						List<Object> varbinddecode = varbindsdecodelist.getContent();
						//@XmlElementRef(name = "parmid", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = JAXBElement.class, required = false),
						//@XmlElementRef(name = "decode", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Decode.class, required = false)
						for (Object varbinddecodeobj:varbinddecode){
							if (varbinddecodeobj instanceof org.opennms.xmlns.xsd.eventconf.Decode){
								Decode decode = (Decode)varbinddecodeobj;
								System.out.println ("     varbindsdecode.Decode:");
								System.out.println ("                  decode.getVarbinddecodedstring()="+decode.getVarbinddecodedstring());
								System.out.println ("                  decode.getVarbindvalue()="+decode.getVarbindvalue());
								// decode.getVarbinddecodedstring();
								// decode.getVarbindvalue();
							} else if (varbinddecodeobj instanceof JAXBElement){
								JAXBElement jaxbelement = (JAXBElement) varbinddecodeobj;
								System.out.println ("     varbinddecodeobj: jaxbelement.getName():"+jaxbelement.getName()+ " jaxbelement.getValue():"+jaxbelement.getValue());
							} else {
								System.out.println("unknown object type varbinddecodeobj= "+varbinddecodeobj.getClass().getName() +"  varbinddecodeobj.toString()"+varbinddecodeobj.toString());
							}
						}
					}
					else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Snmp){
						//  @XmlElementRef(name = "snmp", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Snmp.class, required = false),
						System.out.println ("eventContentObj is Snmp");
						Snmp snmp= (Snmp) eventContentObj;
						List<Serializable> content = snmp.getContent();
						for(Serializable serializable:content ){
							System.out.println ("          snmp.getContent() serializable.toString="+serializable.toString());
						}
						// snmp.getContent();
					}
					else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Mask){
						//  @XmlElementRef(name = "mask", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Mask.class, required = false),
						System.out.println ("eventContentObj is Mask");
						Mask mask= (Mask) eventContentObj;
						System.out.println ("     mask.getMaskelement()="+mask.getMaskelement());
						List<Maskelement> maskelementlist = mask.getMaskelement();
						for(Maskelement maskelement:maskelementlist){
							System.out.println ("         maskelement.getMename()="+maskelement.getMename());
							System.out.println ("         maskelement.getMevalue()="+maskelement.getMevalue());
							List<String> mevalues = maskelement.getMevalue();
							for (String mevalue: mevalues){
								System.out.println ("             maskelement.getMevalue() mevalue="+mevalue);
							}
							//							maskelement.getMename();
							//							maskelement.getMevalue();
						}
						System.out.println ("     mask.getVarbind()="+mask.getVarbind());
						List<Varbind> varbindlist = mask.getVarbind();
						for (Varbind varbind:varbindlist){
							System.out.println ("         varbind.getTextualConvention()="+varbind.getTextualConvention());
							List content = varbind.getContent();
							for(Object serializableObj:content ){
								if (serializableObj instanceof JAXBElement){
									System.out.println ("serializableObj is JAXBElement");
									JAXBElement jaxbelement = (JAXBElement) serializableObj;
									System.out.println ("      varbind.getContent() jaxbelement.getName():"+jaxbelement.getName()+ " jaxbelement.getValue():"+jaxbelement.getValue());
								} else {
									System.out.println ("          varbind.getContent() serializableObj.toString="+serializableObj.toString());
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
						System.out.println ("eventContentObj is Operaction");
						Operaction operaction= (Operaction) eventContentObj;
						System.out.println ("     operaction.getMenutext()="+operaction.getMenutext());
						System.out.println ("     operaction.getState()="+operaction.getState());
						System.out.println ("     operaction.getValue()="+operaction.getValue());
						// operaction.getMenutext();
						// operaction.getState();
						// operaction.getValue();
					} else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Script){
						//  @XmlElementRef(name = "script", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Script.class, required = false),
						System.out.println ("eventContentObj is Script");
						Script script= (Script) eventContentObj;
						System.out.println ("     script.getLanguage()="+script.getLanguage());
						System.out.println ("     script.getValue()="+script.getValue());
						// script.getLanguage();
						// script.getValue();
					} else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Autoaction){
						//  @XmlElementRef(name = "autoaction", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Autoaction.class, required = false),
						System.out.println ("eventContentObj is Autoaction");
						Autoaction autoaction= (Autoaction) eventContentObj;
						System.out.println ("     script.getValue()="+autoaction.getState());
						System.out.println ("     script.getValue()="+autoaction.getValue());
						// autoaction.getState();
						// autoaction.getValue();
					}else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.AlarmData){
						//  @XmlElementRef(name = "alarm-data", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = AlarmData.class, required = false),
						System.out.println ("eventContentObj is AlarmData");
						AlarmData alarmData= (AlarmData) eventContentObj;
						System.out.println ("     alarmData.getAlarmType()="+alarmData.getAlarmType());
						System.out.println ("     alarmData.getClearKey()="+alarmData.getClearKey());
						System.out.println ("     alarmData.getReductionKey()="+alarmData.getReductionKey());
						System.out.println ("     alarmData.getX733AlarmType()="+alarmData.getX733AlarmType());
						System.out.println ("     alarmData.getX733ProbableCause()="+alarmData.getX733ProbableCause());
						System.out.println ("     alarmData.isAutoClean()="+alarmData.isAutoClean());

						System.out.println ("     alarmData.getUpdateField()="+alarmData.getUpdateField());
						List<UpdateField> updatefieldList = alarmData.getUpdateField();
						for (UpdateField updatefield:updatefieldList){
							System.out.println ("          updatefield.getFieldName()="+updatefield.getFieldName());
							System.out.println ("          updatefield.isUpdateOnReduction()="+updatefield.isUpdateOnReduction());
							//							updatefield.getFieldName();
							//							updatefield.isUpdateOnReduction();
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
						System.out.println ("eventContentObj is Tticket");
						Tticket tticket= (Tticket) eventContentObj;
						System.out.println ("     tticket.getState()="+tticket.getState());
						System.out.println ("     tticket.getValue()="+tticket.getValue());
						// tticket.getState();
						// tticket.getValue();
					} else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Autoacknowledge){
						//  @XmlElementRef(name = "autoacknowledge", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Autoacknowledge.class, required = false),
						System.out.println ("eventContentObj is Autoacknowledge");
						Autoacknowledge autoacknowledge= (Autoacknowledge) eventContentObj;
						System.out.println ("     autoacknowledge.getState()="+autoacknowledge.getState());
						System.out.println ("     autoacknowledge.getValue()="+autoacknowledge.getValue());
						// autoacknowledge.getState();
						// autoacknowledge.getValue();
					}else if (eventContentObj instanceof org.opennms.xmlns.xsd.eventconf.Filters){
						//  @XmlElementRef(name = "filters", namespace = "http://xmlns.opennms.org/xsd/eventconf", type = Filters.class, required = false)
						System.out.println ("eventContentObj is Filters");
						Filters filters= (Filters) eventContentObj;
						List<Filter> filterlist = filters.getFilter();
						for (Filter filter:filterlist){
							System.out.println ("        filter.getEventparm()="+filter.getEventparm());
							System.out.println ("        filter.getPattern()="+filter.getPattern());
							System.out.println ("        filter.getReplacement()="+filter.getReplacement());
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
						System.out.println ("eventContentObj is JAXBElement");
						JAXBElement jaxbelement = (JAXBElement) eventContentObj;
						System.out.println ("      jaxbelement.getName():"+jaxbelement.getName()+ " jaxbelement.getValue():"+jaxbelement.getValue());

					}
				} 
			}
		}

		System.out.println("@Test - extractXmlElementTest().End");
	}

	/**
	 * @return XML encoded version of ProductMetadata
	 */
	public String eventsToXml(Events eventsConfig){

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(org.opennms.xmlns.xsd.eventconf.ObjectFactory.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter stringWriter = new StringWriter();
			jaxbMarshaller.marshal(eventsConfig,stringWriter);
			return stringWriter.toString();
		} catch (JAXBException e) {
			throw new RuntimeException("Problem marshalling event config data:",e);
		}
	}

	/**
	 * load this object with data from xml string
	 * @parm XML encoded version of LicenceMetadata
	 */
	public Events eventsFromXml(String xmlStr){

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(org.opennms.xmlns.xsd.eventconf.ObjectFactory.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(xmlStr);
			Events eventsConfig= (Events) jaxbUnmarshaller.unmarshal(reader);
			return eventsConfig;
		} catch (JAXBException e) {
			throw new RuntimeException("Problem unmarshalling ProductMetadata:",e);
		}
	}

}
