package org.opennms.features.pluginmgr;

import java.io.PrintWriter;
import java.io.StringWriter;

public class SimpleStackTrace {
	
	public static String errorToString(Throwable throwable){
		StringWriter sw = new StringWriter();
		sw.append("Reported Exception:");
		throwable.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

}
