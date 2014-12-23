package org.opennms.karaf.licencemgr;

import java.io.UnsupportedEncodingException;
import java.util.zip.CRC32;

public class StringChecksum {
	
	/**
	 * Adds a CRC32 encoded string to supplied string separated by '-'
	 * resulting in string of form <valueString>-<CRC32 in hex>
	 * @param valueString
	 * @return
	 */
	public String addCRC(String valueString){
        // make checksum
		CRC32 crc=new CRC32();
		try {
			crc.update(valueString.getBytes("UTF-8"));
		}
		catch (  UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 encoding is not supported");
		}
		String hexcrc= Long.toHexString(crc.getValue());
		
		String stringPlusCrc=valueString+"-"+hexcrc;

		return stringPlusCrc;
	}

	/**
	 * Expects stringPlusCrc to be a value string separated from CRC by a '-' character.
	 * <valueString>-<CRC32 in hex>
	 * Splits the string and test that the CRC is correct for value string 
	 * @param stringPlusCrc
	 * @return true if CRC is correct
	 */
	public boolean checkCRC(String stringPlusCrc){
		
		String[] parts = stringPlusCrc.split("-");
		if (parts.length!=2) return false;
		String hexSystemIdString=parts[0];
		String hexcrc=parts[1];
		
        // make checksum
		CRC32 crc=new CRC32();
		try {
			crc.update(hexSystemIdString.getBytes("UTF-8"));
		}
		catch (  UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 encoding is not supported");
		}
		
		String checkHexCrc= Long.toHexString(crc.getValue());
		
		if (!checkHexCrc.equals(hexcrc)) return false;
		
		return true;

	}
}
