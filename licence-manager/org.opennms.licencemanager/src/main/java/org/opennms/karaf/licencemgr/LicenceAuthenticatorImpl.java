package org.opennms.karaf.licencemgr;

import org.opennms.karaf.licencemgr.LicenceAuthenticator;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceMetadata;
import org.osgi.framework.ServiceException;

public class LicenceAuthenticatorImpl implements LicenceAuthenticator {

	private String productId;
	private String privateKeyEnryptedStr;

	private LicenceService licenceService= null;

	public LicenceAuthenticatorImpl(LicenceService licenceService, String productId, String privateKeyEnryptedStr ){
		
		if (licenceService==null) throw new RuntimeException("LicenceAuthenticatoImpl: licenceService cannot be null");
		if (productId==null) throw new RuntimeException("LicenceAuthenticatoImpl: productId cannot be null");
		if (privateKeyEnryptedStr==null) throw new RuntimeException("LicenceAuthenticatoImpl: privateKeyEnryptedStr cannot be null");
		
		this.productId=productId;
		this.privateKeyEnryptedStr=privateKeyEnryptedStr;

		this.licenceService=licenceService;

		String systemId =licenceService.getSystemId();
		if (systemId==null) throw new ServiceException("systemId cannot be null");
		
		String licencewithCRC = licenceService.getLicence(productId);

		if (licencewithCRC==null) {
			System.out.println("No licence installed for productId:'"+productId+"'");
			throw new ServiceException("No licence installed for productId:'"+productId+"'");
		}

		// check and remove checksum
		StringCrc32Checksum stringCrc32Checksum = new StringCrc32Checksum();
		String licenceStr= stringCrc32Checksum.removeCRC(licencewithCRC);
		if (licenceStr==null) {
			System.out.println("licence checksum incorrect for productId:'"+productId+"'");
			throw new ServiceException("licence checksum incorrect for productId:'"+productId+"'");
		}

		// split components of licence string
		String[] components = licenceStr.split(":");
		if (components.length!=3) {
			System.out.println("incorrectly formatted licence string for productId:'"+productId+"'");
			throw new ServiceException("incorrectly formatted licence string for productId:'"+productId+"'");
		}

		String receivedLicenceMetadataHexStr=components[0];
		String receivedEncryptedHashStr=components[1];
		String receivedAesSecretKeyStr=components[2];

		// decode licence private key before using
		AesSymetricKeyCipher aesCipher = new AesSymetricKeyCipher();
		aesCipher.setEncodedSecretKeyStr(receivedAesSecretKeyStr);

		String decryptedPrivateKeyStr= aesCipher.aesDecryptStr(privateKeyEnryptedStr);

		//decrypt encrypted hash of metadata 
		RsaAsymetricKeyCipher rsaAsymetricKeyCipher = new RsaAsymetricKeyCipher();
		rsaAsymetricKeyCipher.setPrivateKeyStr(decryptedPrivateKeyStr);

		String decriptedHashStr= rsaAsymetricKeyCipher.rsaDecryptString(receivedEncryptedHashStr);

		// verify hash of licence metadata matches decrypted hash
		LicenceMetadata licenceMetadata= new LicenceMetadata();
		licenceMetadata.fromHexString(receivedLicenceMetadataHexStr);
		String sha256Hash = licenceMetadata.sha256Hash();

		if (! sha256Hash.equals(decriptedHashStr)) {
			System.out.println("licence hash not verified  for productId:'"+productId+"'");
			throw new ServiceException("licence hash not verified  for productId:'"+productId+"'");
		}

		// check metadata matches expected values
		if (! productId.equals(licenceMetadata.getProductId())){
			System.out.println("licence productId='"+licenceMetadata.getProductId()+"' does not match expected productId:'"+productId+"'");
			throw new ServiceException("licence productId='"+licenceMetadata.getProductId()+"' does not match expected productId:'"+productId+"'");
		}

		// check system id. If systemID == ALL_SYSTEM_IDS then this test is passed because any systemId is allowed
		if (! systemId.equals(licenceMetadata.getSystemId()) && ! "ALL_SYSTEM_IDS".equals(licenceMetadata.getSystemId())) {
			System.out.println("licence systemId='"+licenceMetadata.getSystemId()+"' does not match local systemId='"+systemId
					+ "' in installed licence for productId='"+productId+"'");
			throw new ServiceException("licence systemId='"+licenceMetadata.getSystemId()+"' does not match local systemId='"+systemId
					+ "' in installed licence for productId='"+productId+"'");
		}

		// TODO licenceService.authenticated licence 
		System.out.println("BundleLicenceAuthenticator authenticated licence for productId="+productId);
		System.out.println("Licence Metadata xml="+licenceMetadata.toXml());

	}

}
