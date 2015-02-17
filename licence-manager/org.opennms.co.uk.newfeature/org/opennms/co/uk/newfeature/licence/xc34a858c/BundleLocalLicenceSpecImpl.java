
package org.opennms.co.uk.newfeature.licence.xc34a858c;

import org.opennms.karaf.licencemgr.BundleLicenceSpec;
import org.opennms.karaf.licencemgr.BundleLicenceSpecImpl;
import org.opennms.karaf.licencepub.LicencePublisher;
import org.osgi.framework.BundleContext;

/**
 *  Generated Licence Specification Class
 *  for productId=org.opennms.co.uk/org.opennms.co.uk.newfeature/0.0.1-SNAPSHOT
 */
public class BundleLocalLicenceSpecImpl {

    private static final String productId="org.opennms.co.uk/org.opennms.co.uk.newfeature/0.0.1-SNAPSHOT";

    private static final String aesSecretKeyStr="DA2120988CB3BF0DBE4B42A56B1D7A8E";
    private static final String publicKeyStr="882ec4580402b135fa008daed6ced0ef29b9759624c3e4373ff65496dc4b5e2670cbc9adab89125142cc79eec56501aab2d9734b26f7e236cdc252437bdab1acb1ea8ae3a6bb4a453c8b41035a8525c679fbe58ac579775600c04f754b809a3c204806ec1978c0c2a9920cac00b23ba05f3ff57adf8f0468080a44e5fa9177a38625ae44274465494ae05de068c2efea3ad9944a76a514deb1a322a5b17344ebc8e8536f1bdb9810749881c691d1b38ebfcac8529707c376a559d978c794456e87153ec296417c52346d5df1a9b12628ec4f9e467993238f9f8874f91d95e44af991ee26a5a69697cc2cd4ec231c32156cf9f6becdf7b313ba62574ba0b8a8f5-10001";

    private BundleLicenceSpec bundleLicenceSpec=null;
    
    public BundleLocalLicenceSpecImpl( LicencePublisher licencePublisher, BundleContext bundleContext, String licenceMetadataUri){
        bundleLicenceSpec = new BundleLicenceSpecImpl(licencePublisher, bundleContext, productId, licenceMetadataUri,  aesSecretKeyStr, publicKeyStr);
    }
    

    //use as blueprint destroy-method
    public void unregisterSpec(){
        if (bundleLicenceSpec!=null){
            try{
                bundleLicenceSpec.unregisterSpec();
                System.out.println(BundleLocalLicenceSpecImpl.class +": Unregistered Licence Specification for productId="+productId);
            } catch (Exception e){
                System.out.println(BundleLocalLicenceSpecImpl.class +": Problem Unregistering Licence Specification for productId="+productId+"  "+ e);
            }  finally {
                bundleLicenceSpec=null; //release resources
            }
        }
    }
}
