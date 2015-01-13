package org.opennms.karaf.licencemgr.metadata.jaxb;

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
    
    /**
     * Create an instance of {@link ProductSpecMap }
     */
    public ProductSpecMap createProductSpecMap() {
        return new ProductSpecMap();
    }
    
    /**
     * Create an instance of {@link LicenceMap }
     */
    public LicenceMap createLicenceMap() {
        return new LicenceMap();
    }
    
    /**
     * Create an instance of {@link LicenceSpecification }
     */
    public LicenceSpecification createLicenceSpecification() {
        return new LicenceSpecification();
    }
    
    /**
     * Create an instance of {@link LicenceSpecMap }
     */
    public LicenceSpecMap createLicenceSpecMap() {
        return new LicenceSpecMap();
    }
    
    /**
     * Create an instance of {@link ErrorMessage }
     */
    public ErrorMessage createErrorMessage() {
        return new ErrorMessage();
    }
    
}