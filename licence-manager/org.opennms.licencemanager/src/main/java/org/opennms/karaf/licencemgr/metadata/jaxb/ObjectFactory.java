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
     * Create an instance of {@link ProductSpecList }
     */
    public ProductSpecList createProductSpecList() {
        return new ProductSpecList();
    }
    
    /**
     * Create an instance of {@link LicenceList }
     */
    public LicenceList createLicenceList() {
        return new LicenceList();
    }
    
    /**
     * Create an instance of {@link LicenceSpecification }
     */
    public LicenceSpecification createLicenceSpecification() {
        return new LicenceSpecification();
    }
    
    /**
     * Create an instance of {@link LicenceSpecList }
     */
    public LicenceSpecList createLicenceSpecList() {
        return new LicenceSpecList();
    }
    
    /**
     * Create an instance of {@link LicenceSpecList }
     */
    public LicenceMetadataList createLicenceMetadataList() {
        return new LicenceMetadataList();
    }
    
    /**
     * Create an instance of {@link ErrorMessage }
     */
    public ErrorMessage createErrorMessage() {
        return new ErrorMessage();
    }
    
    /**
     * Create an instance of {@link ReplyMessage }
     */
    public ReplyMessage createReplyMessage() {
        return new ReplyMessage();
    }
    
    /**
     * Create an instance of {@link LicenceEntry }
     */
    public LicenceEntry createLicenceEntry() {
        return new LicenceEntry();
    }
    
}