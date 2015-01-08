package org.opennms.karaf.productpub;


/**
 * Product Register interface is used to register products which have been installed
 * in the system. It allows installed products to describe themselves in a programmatic way.
 * The ProductRegister interface has the same semantics as the ProductPublisher.
 * @author cgallen
 *
 */
public interface ProductRegister extends ProductPublisher{

}
