package org.opennms.karaf.licencemanager.testbundle;

import org.opennms.karaf.licencemgr.LicenceService;
import org.opennms.karaf.licencemgr.RsaKeyEncryptor;
import org.osgi.framework.ServiceException;
import org.opennms.karaf.licencemanager.testbundle.LicenceAuthenticator;

public class LicenceAuthenticatorImpl implements LicenceAuthenticator {

	//@Test generateKeys() privatekey=84b2c5f3c3760e2d62d9bb1019bdec42727395fb3ffb62999fd1a126f771245905d030a5d5bc1836348cd02a54c20e8687d942acf4e20a87e29c60a1742eb6683cba30cf9c0bd9543cb91f6a6ce4696db7dcbcc0d5c3aa60a2c298b8b015c307c429dc5ad05f96efa66b1a560918d4d2f57cd9626b63b10dadbfaf486860f6e375ca0b54e0dfa14cc4029b9864babd41a59f927fe7ca7e5db92a47a08fb9d48a12d7ea0a8cc45766754a85819585940229672c3fa7c1bbb3e56d2fe2ffe0a6838b3ebe1fa56a64c21d49100850dce3987bbabcb7c0512d885ac30f168bc1a393e32d89752c3c9b2f5afa9f44fb25f782fd873ea6c827e5dc51e9cd244af68d1d-e9e73586b8c31fba60c3ea383727cf8c50b1ed6d331aaa72a083a2cc4cdde274090b30b5204aad62debe573046b6c963d991dd358171ef9e16940ec0894acdbc6f4ec33b3094b24109c846d811998d3d191abe9c2a30fbe1db03a8afe3b27854f831d17942533c7ab19d988e8efa762a650ded20ceaaab2cb1577060f19bf991d2bf05ff1a7e52161a2ff21e569328f9a912dfe948fcc0544e45ff7d1479f9ced60dbfb69c8954969d98758cff0caf9c34ed3aafcab4443ff1a71c3306e4f82adcef877870d29430743182c6420fc780f132cb0f341e50530762861d56052e2cc2ee2ff08d619acca40f96f32184008bef960fe6a3cbc52785968b2d4e5b431
	//@Test generateKeys() publickey=84b2c5f3c3760e2d62d9bb1019bdec42727395fb3ffb62999fd1a126f771245905d030a5d5bc1836348cd02a54c20e8687d942acf4e20a87e29c60a1742eb6683cba30cf9c0bd9543cb91f6a6ce4696db7dcbcc0d5c3aa60a2c298b8b015c307c429dc5ad05f96efa66b1a560918d4d2f57cd9626b63b10dadbfaf486860f6e375ca0b54e0dfa14cc4029b9864babd41a59f927fe7ca7e5db92a47a08fb9d48a12d7ea0a8cc45766754a85819585940229672c3fa7c1bbb3e56d2fe2ffe0a6838b3ebe1fa56a64c21d49100850dce3987bbabcb7c0512d885ac30f168bc1a393e32d89752c3c9b2f5afa9f44fb25f782fd873ea6c827e5dc51e9cd244af68d1d-10001
	//@Test testEncrypt system id = testString=4ad72a34e3635c1b-99da3323
	//@Test testEncrypt encryptedStr=2CF8B8A6F253D155ADB78449D49D8856C7101B8726F9BEE997E7CD151B094E922C61F283DE8335F616FF9AD9850E0F689B394628196BAE130C74FC19E4DFECF3287CA966E1632C8602A5C13039C827DED7ECB7EA83652C9646304776685D6CAB57B50409C3A9FFAA7514B1CAFB675C56742AF4C12AAEF7506EF9423E64A09F081D4CD727438743D1CD7756C7360750F627920F65556AAFF8BBA12F6B675597BC9D6A41E441B34BB0A8B2226EC104007CA2A2C32D3F752E91616EDDD8DF6E3C38EADA644A20189CBA6F28A2448438630DD394F4FCF409DE399F9CED73B15DA55AE1F8A1EDB7A5959A7740B9D3FF7C7DDE1B8E47378B3E0B1D56963AEE7D65E98D
	//@Test testEncrypt (licence) encryptedStrPlusCrc=57B776F6BA2178A9F150596B8759041913832F6D67E34AAF4AC3E4EA9BC89AD24B7A5E8DCFD0EE700DB169292236FD1326728A8D395EA6DC8921E91A06092A9EED03145AC76A9BD57CF8D3FC614915000C32EB1607FFCB166BAD99BF1ABE71F68F8971F7CB1B1ADEF65F8E7345DD61BA15F49508A9BF87E26585BCAA8CFF3ACE8D6BF0FD2CA43DEA391256ED9EE9501EEA0199CAF369857CD5713A74D66F4783B8A2CE730885401C6BD39B10278801BB75FFC1F61736CB850F57CD6FD43A0ED7BD06939E3AA242FA36470E1FA9B7968873396C97A0FE8B83DC5F8C69E976DA2F594CB2303BFAE3BABC4D88CE8FE6F5DD31963EB53C53EF3800CF45721F6AB0AC-6b891008

	// to test use command
	// licence-mgr:add org.opennms/org.opennms.karaf.licencemanager.testbundle/1.0-SNAPSHOT 57B776F6BA2178A9F150596B8759041913832F6D67E34AAF4AC3E4EA9BC89AD24B7A5E8DCFD0EE700DB169292236FD1326728A8D395EA6DC8921E91A06092A9EED03145AC76A9BD57CF8D3FC614915000C32EB1607FFCB166BAD99BF1ABE71F68F8971F7CB1B1ADEF65F8E7345DD61BA15F49508A9BF87E26585BCAA8CFF3ACE8D6BF0FD2CA43DEA391256ED9EE9501EEA0199CAF369857CD5713A74D66F4783B8A2CE730885401C6BD39B10278801BB75FFC1F61736CB850F57CD6FD43A0ED7BD06939E3AA242FA36470E1FA9B7968873396C97A0FE8B83DC5F8C69E976DA2F594CB2303BFAE3BABC4D88CE8FE6F5DD31963EB53C53EF3800CF45721F6AB0AC-6b891008


	private final String privateKeyStr="84b2c5f3c3760e2d62d9bb1019bdec42727395fb3ffb62999fd1a126f771245905d030a5d5bc1836348cd02a54c20e8687d942acf4e20a87e29c60a1742eb6683cba30cf9c0bd9543cb91f6a6ce4696db7dcbcc0d5c3aa60a2c298b8b015c307c429dc5ad05f96efa66b1a560918d4d2f57cd9626b63b10dadbfaf486860f6e375ca0b54e0dfa14cc4029b9864babd41a59f927fe7ca7e5db92a47a08fb9d48a12d7ea0a8cc45766754a85819585940229672c3fa7c1bbb3e56d2fe2ffe0a6838b3ebe1fa56a64c21d49100850dce3987bbabcb7c0512d885ac30f168bc1a393e32d89752c3c9b2f5afa9f44fb25f782fd873ea6c827e5dc51e9cd244af68d1d-e9e73586b8c31fba60c3ea383727cf8c50b1ed6d331aaa72a083a2cc4cdde274090b30b5204aad62debe573046b6c963d991dd358171ef9e16940ec0894acdbc6f4ec33b3094b24109c846d811998d3d191abe9c2a30fbe1db03a8afe3b27854f831d17942533c7ab19d988e8efa762a650ded20ceaaab2cb1577060f19bf991d2bf05ff1a7e52161a2ff21e569328f9a912dfe948fcc0544e45ff7d1479f9ced60dbfb69c8954969d98758cff0caf9c34ed3aafcab4443ff1a71c3306e4f82adcef877870d29430743182c6420fc780f132cb0f341e50530762861d56052e2cc2ee2ff08d619acca40f96f32184008bef960fe6a3cbc52785968b2d4e5b431";
	private final String productId="org.opennms/org.opennms.karaf.licencemanager.testbundle/1.0-SNAPSHOT";
	
	private LicenceService licenceService= null;
	
	public LicenceAuthenticatorImpl(LicenceService licenceService){
		if (licenceService==null) throw new RuntimeException("LicenceAuthenticatorImpl: licenceService cannot be null");
		
		this.licenceService=licenceService;
		
		String systemId =licenceService.getSystemId();
		if (systemId==null) throw new ServiceException("systemId cannot be null");
		
		String licencewithCRC = licenceService.getLicence(productId);
		
		if (licencewithCRC==null) {
			System.out.println("No licence installed for productId:'"+productId+"'");
			throw new ServiceException("No licence installed for productId:'"+productId+"'");
		} else {
			RsaKeyEncryptor rsaKeyEncryptor = new RsaKeyEncryptor();
			rsaKeyEncryptor.setPrivateKeyStr(privateKeyStr);
			String decryptedLicence=null;
			try {
				decryptedLicence = rsaKeyEncryptor.rsaDecryptStringRemoveChecksum(licencewithCRC);	
			} catch (Exception e){
				System.out.println("Licence Authenticator unable to authenticate licence for productId:'"+productId+"' Exception:"+e);
				throw new ServiceException("Licence Authenticator unable to authenticate licence for productId:'"+productId+"'", e);
			}
			if ((decryptedLicence ==null) || ! decryptedLicence.equals(systemId)){
				System.out.println("Licence Authenticator unable to authenticate licence for productId:'"+productId+"'");
				throw new ServiceException("Licence Authenticator unable to authenticate licence for productId:'"+productId+"'");
			}
			System.out.println("LicenceAuthenticator authenticated licence for productId="+productId);
		}
	}
	
}
