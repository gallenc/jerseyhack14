package org.opennms.plugin.eventimporter;

import java.util.ArrayList;
import java.util.List;

/** 
 * definition of a varbind in a varbind config
 * @author cgallen
 *
 */
public class RowVarbindEntry {
	
	private String number=null;
	private List<String> value = new ArrayList<String>();
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public List<String> getValues() {
		return value;
	}
	public void setValue(List<String> value) {
		this.value = value;
	}

}