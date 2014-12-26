package org.opennms.karaf.licencemgr.test;

import org.junit.Assert;
import org.junit.Test;
import org.opennms.karaf.licencemgr.AesSymetricKeyCipher;

public class AesSymmetricKeyCipherTest {
    
    @Test
    public void testEncryptionDecryption()
            throws Exception {
    
        AesSymetricKeyCipher aesCipher = new AesSymetricKeyCipher();
        aesCipher.generateKey();
        
        String sourceStr="John has a long mustache.";
		System.out.println("sourceStr="+sourceStr);
        String encrypted = aesCipher.aesEncryptStr(sourceStr);
        System.out.println("encrypted="+encrypted);
        
        String secretKeyStr = aesCipher.getEncodedSecretKeyStr();
        System.out.println("secretKeyStr="+secretKeyStr);
        
        aesCipher = new AesSymetricKeyCipher();
        aesCipher.setEncodedSecretKeyStr(secretKeyStr);
        
        String decrypted = aesCipher.aesDecryptStr(encrypted);
        System.out.println("decrypted="+decrypted);
        Assert.assertEquals(decrypted, sourceStr); 
    }
}