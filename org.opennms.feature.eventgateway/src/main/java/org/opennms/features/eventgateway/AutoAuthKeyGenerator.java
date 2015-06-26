package org.opennms.features.eventgateway;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class AutoAuthKeyGenerator {
	
	// used to generate random authKeys
	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static Random rnd = new Random();

    // cache of current authentication keys. Keys are aged until maxTimeKeepOldAuthKeys miliseconds
	private Map<String,Date> authKeyCache = Collections.synchronizedMap(new HashMap<String,Date>());

	// current authKey
	private String currentAuthKey=null;
	
	// current authKey date
	private Date currentAuthKeyDate=null;
	
	// how long to allow old authKeys to still authenticate
	private long maxTimeKeepOldAuthKeys=1000*60*10; // 10 mins
	
	// how long before a new authKey is generated
	private long maxCurrentAuthKeyAge=1000*60*5; // 5 mins

	// length of random authorisation key
	private int authKeylength = 5;


	/**
	 * generates a random capitals ascii string of length len
	 * @param len length of string
	 * @return
	 */
	private String randomString( int len ) {
		StringBuilder sb = new StringBuilder( len );
		for( int i = 0; i < len; i++ ) 
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		return sb.toString();
	}

	/**
	 * creates a new authKey and places in authKey cache with date/time of creation
	 * This must e called after AutoAuthKeyGenerator is first configured
	 * @return new authKey
	 */
	public synchronized String createNewAuthKey(){
		currentAuthKey = randomString( authKeylength );
		currentAuthKeyDate = new Date();
		authKeyCache.put(currentAuthKey, currentAuthKeyDate);
		return currentAuthKey;
	}


	/**
	 * checks if current authKey is aged. Returns current authKey if still valid
	 * returns new authKey if current authKey is aged.
	 * @return
	 */
	public synchronized String getCurrentAuthKey(){
		if (currentAuthKeyDate.getTime() < System.currentTimeMillis() - maxCurrentAuthKeyAge){
			return createNewAuthKey();
		} else 	return currentAuthKey;
	}

	/**
	 * @param authKey
	 * @return true if authKey is in authKey cache and has not aged beyond time to keep old authKeys
	 */
	public synchronized boolean authenticateAuthKey(String authKey){

		// remove aged authKeys from authKey cache
		Iterator<Entry<String, Date>> it = authKeyCache.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, Date> entry = it.next();
			if(entry.getValue().getTime() < System.currentTimeMillis() - maxTimeKeepOldAuthKeys){
				it.remove();
			}
		}

		// check if authKey is in authKey cache
		if (authKeyCache.containsKey(authKey)){
			return true;
		} else return false;

	}

	/**
	 * @return the authKeyCache
	 */
	public synchronized Map<String, Date> getAuthKeyCache() {
		return authKeyCache;
	}


	/**
	 * @return the currentAuthKeyDate
	 */
	public synchronized Date getCurrentAuthKeyDate() {
		return currentAuthKeyDate;
	}


	/**
	 * @return the maxTimeKeepOldAuthKeys
	 */
	public synchronized long getMaxTimeKeepOldAuthKeys() {
		return maxTimeKeepOldAuthKeys;
	}

	/**
	 * @param maxTimeKeepOldAuthKeys the maxTimeKeepOldAuthKeys to set
	 */
	public synchronized void setMaxTimeKeepOldAuthKeys(long maxTimeKeepOldAuthKeys) {
		this.maxTimeKeepOldAuthKeys = maxTimeKeepOldAuthKeys;
	}

	/**
	 * @return the maxCurrentAuthKeyAge
	 */
	public synchronized  long getMaxCurrentAuthKeyAge() {
		return maxCurrentAuthKeyAge;
	}

	/**
	 * @param maxCurrentAuthKeyAge the maxCurrentAuthKeyAge to set
	 */
	public synchronized void setMaxCurrentAuthKeyAge(long maxCurrentAuthKeyAge) {
		this.maxCurrentAuthKeyAge = maxCurrentAuthKeyAge;
	}

	/**
	 * @return the authKeylength
	 */
	public synchronized int getAuthKeylength() {
		return authKeylength;
	}

	/**
	 * @param authKeylength the authKeylength to set
	 */
	public synchronized void setAuthKeylength(int authKeylength) {
		this.authKeylength = authKeylength;
	}
	
	/**
	 * @param authKeylength the authKeylength to set
	 */
	public synchronized String resetAuthKeyCache() {
		// rest replies containing the old map will complete and then get a new map
		authKeyCache = Collections.synchronizedMap(new HashMap<String,Date>());
		return createNewAuthKey();
	}


}
