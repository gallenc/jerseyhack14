package org.opennms.karaf.productpub;

import org.opennms.karaf.licencemgr.metadata.ProductMetadata;
import org.osgi.framework.BundleContext;

public interface BundleProductSpec {
	
	public void registerSpec();

	public void unregisterSpec();

	public ProductMetadata getProductMetadata();

	public void setProductMetadata(ProductMetadata productMetadata);

	public ProductPublisher getProductPublisher();

	public void setProductPublisher(ProductPublisher productPublisher);

	public String getProductMetadataUri();

	public void setProductMetadataUri(String productMetadataUri);
	
	public void setBundleContext(BundleContext bundleContext);

	public BundleContext getBundleContext();
}
