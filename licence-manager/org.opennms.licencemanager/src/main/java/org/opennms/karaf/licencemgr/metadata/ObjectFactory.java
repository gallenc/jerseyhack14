package org.opennms.karaf.licencemgr.metadata;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OptionMetadata }
     */
    public OptionMetadata createOptionMetadata() {
        return new OptionMetadata();
    }
    
    /**
     * Create an instance of {@link OptionMetadata }
     */
    public ProductMetadata createProductMetadata() {
        return new ProductMetadata();
    }
    
    /**
     * Create an instance of {@link OptionMetadata }
     */
    public LicenceMetadata createLicenceMetadata() {
        return new LicenceMetadata();
    }
    
}