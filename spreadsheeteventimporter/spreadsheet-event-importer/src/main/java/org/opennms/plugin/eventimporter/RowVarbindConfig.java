package org.opennms.plugin.eventimporter;

import java.util.ArrayList;
import java.util.List;

/**
 * class for holding a simple list of varbind configurations for a mask
 * @author cgallen
 *
 */
public class RowVarbindConfig{
	
	private List<RowVarbindEntry> varbindEntries = new ArrayList<RowVarbindEntry>();
	
	public List<RowVarbindEntry> getVarbindEntries() {
		return varbindEntries;
	}

	public void setVarbindEntries(List<RowVarbindEntry> varbindEntries) {
		this.varbindEntries = varbindEntries;
	}


}
