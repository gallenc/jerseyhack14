package org.opennms.features.vaadin.config.model;


import java.util.List;

import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceEntry;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceList;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.ProductSpecList;


public class PluginModel {

	ProductSpecList availablePlugins;
	
	ProductSpecList installedPlugins;
	
	LicenceList installedLicenceList;

	/**
	 * @return the availablePlugins
	 */
	public ProductSpecList getAvailablePlugins() {
		
		// test code generating a spec list
		ProductSpecList productSpecList = new ProductSpecList();
		
		String[] productIds = {"Mercury","Venus","Earth","Mars","Jupiter","Saturn","Neptune","Uranus"};
		
		for (String pid: productIds){
			ProductMetadata pmeta= new ProductMetadata();
			pmeta.setProductId(pid);
			pmeta.setOrganization("OpenNMS Project");
			pmeta.setProductDescription("Test product description");
			pmeta.setFeatureRepository("mvn:org.opennms.licencemgr/licence.manager.example/2.10.0/xml/features");
			pmeta.setProductName("test Bundle");
			pmeta.setProductUrl("http:\\\\opennms.co.uk");
			pmeta.setLicenceKeyRequired(true);
			pmeta.setLicenceType("OpenNMS EULA See http:\\\\opennms.co.uk\\EULA");
			productSpecList.getProductSpecList().add(pmeta);
		}

		availablePlugins= productSpecList;
		return availablePlugins;
	}

	/**
	 * @param availablePlugins the availablePlugins to set
	 */
	public void setAvailablePlugins(ProductSpecList availablePlugins) {
		this.availablePlugins = availablePlugins;
	}

	/**
	 * @return the installedPlugins
	 */
	public ProductSpecList getInstalledPlugins() {
		// test code generating a spec list
		ProductSpecList productSpecList = new ProductSpecList();
		
		String[] productIds = {"Mercury","Venus","Earth","Mars","Jupiter","Saturn","Neptune","Uranus"};
		
		for (String pid: productIds){
			ProductMetadata pmeta= new ProductMetadata();
			pmeta.setProductId(pid);
			pmeta.setOrganization("OpenNMS Project");
			pmeta.setProductDescription("Test product description");
			pmeta.setFeatureRepository("mvn:org.opennms.licencemgr/licence.manager.example/2.10.0/xml/features");
			pmeta.setProductName("test Bundle");
			pmeta.setProductUrl("http:\\\\opennms.co.uk");
			pmeta.setLicenceKeyRequired(true);
			pmeta.setLicenceType("OpenNMS EULA See http:\\\\opennms.co.uk\\EULA");
			productSpecList.getProductSpecList().add(pmeta);
		}
		installedPlugins=productSpecList;
		return installedPlugins;
	}

	/**
	 * @param installedPlugins the installedPlugins to set
	 */
	public void setInstalledPlugins(ProductSpecList installedPlugins) {
		this.installedPlugins = installedPlugins;
	}

	/**
	 * @return the installedLicenceList
	 */
	public LicenceList getInstalledLicenceList() {
		
		// test values
		installedLicenceList = new LicenceList();
		List<LicenceEntry> licenceList = installedLicenceList.getLicenceList();
		
		LicenceEntry le = new LicenceEntry();
		le.setProductId("product1");
		le.setLicenceStr("incorrect String");
		licenceList.add(le);
		
		LicenceEntry le2 = new LicenceEntry();
		le2.setProductId("myproject/1.0-SNAPSHOT");
		String correctlicence="3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D3822207374616E64616C6F6E653D22796573223F3E3C6C6963656E63654D657461646174613E3C70726F6475637449643E6D7970726F6A6563742F312E302D534E415053484F543C2F70726F6475637449643E3C6C6963656E7365653E4D722043726169672047616C6C656E3C2F6C6963656E7365653E3C6C6963656E736F723E4F70656E4E4D5320554B3C2F6C6963656E736F723E3C73797374656D49643E333265333936653336623238656635642D61343865663163623C2F73797374656D49643E3C7374617274446174653E323031352D30312D30375431353A31303A34352E3133385A3C2F7374617274446174653E3C657870697279446174653E323031352D30312D30375431353A31303A34352E3133385A3C2F657870697279446174653E3C6F7074696F6E733E3C6F7074696F6E3E3C6465736372697074696F6E3E7468697320697320746865206465736372697074696F6E3C2F6465736372697074696F6E3E3C6E616D653E6F7074696F6E313C2F6E616D653E3C76616C75653E6E657776616C75653C2F76616C75653E3C2F6F7074696F6E3E3C2F6F7074696F6E733E3C2F6C6963656E63654D657461646174613E:612D7BC47629FEF9D0B6ECE90AFDC3A2817027F4815CDFA866914C23B54FF7A428D815E0F8A5939EDFE742ACE10711BC533CC5F73F09130537F8DC4579718FB0AA113FA185707257BBD44E53008368BBF0FAA9E84C53B15302B56E0F222C22AE0B732A1B0E72A267592FF34A3101C209A988BBF71CC2ACF530EDE95164A3CCFF243C2B638FFA7AE243B0436BF91DC0B4EBB0F8884A100BBAE7CA884C258C7F680131FDF6FEEC0566F04483A56DE517FB8D189078A4A98D5FA77F7C2F2C298560EB5C3624C912331F2B750DE082486C190337A3AD51B20DADC1C48B717E8D85464FCDEB30A9DF6072DE7A1933A6745823CE6D2B2C1C30D7D194DCEA122AD54DB8:3E10949F8A08F5CDCB78BF9A944C9534-286cdfd5";
		le2.setLicenceStr(correctlicence);
		licenceList.add(le2);
		
		return installedLicenceList;
	}

	/**
	 * @param installedLicenceList the installedLicenceList to set
	 */
	public void setInstalledLicenceList(LicenceList installedLicenceList) {
		this.installedLicenceList = installedLicenceList;
	}
	
}
