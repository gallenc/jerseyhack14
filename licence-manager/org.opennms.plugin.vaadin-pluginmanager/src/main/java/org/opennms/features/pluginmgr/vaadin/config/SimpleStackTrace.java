package org.opennms.features.pluginmgr.vaadin.config;

import java.io.PrintWriter;
import java.io.StringWriter;

public class SimpleStackTrace {
	
	public static String errorToString(Exception e){
		StringWriter sw = new StringWriter();
		sw.append("Reported Exception:");
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

}
