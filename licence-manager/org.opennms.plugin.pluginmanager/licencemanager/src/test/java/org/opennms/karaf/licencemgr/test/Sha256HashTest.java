package org.opennms.karaf.licencemgr.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.junit.Test;

public class Sha256HashTest {

    @Test
    public void testHash() throws NoSuchAlgorithmException, UnsupportedEncodingException{
    	MessageDigest md = MessageDigest.getInstance("SHA-256");
    	String text = "This is some text";

    	md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
    	byte[] digest = md.digest();
    	    	
		String digestStr = DatatypeConverter.printHexBinary(digest);
		System.out.println("digestStr="+digestStr);

    }

}
