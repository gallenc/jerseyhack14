/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2012-2014 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2014 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.features.vaadin.config;

import org.opennms.netmgt.config.api.DataCollectionConfigDao;
import org.opennms.vaadin.extender.AbstractApplicationFactory;

import com.vaadin.ui.UI;

/**
 * A factory for creating SNMP Collection Administration Application objects.
 */
public class PluginManagerAdminApplicationFactory extends AbstractApplicationFactory {

//    /** The OpenNMS Data Collection Configuration DAO. */
//    private DataCollectionConfigDao dataCollectionDao;
//
//    /**
//     * Sets the OpenNMS Data Collection Configuration DAO.
//     *
//     * @param dataCollectionDao the new data collection DAO
//     */
//    public void setDataCollectionDao(DataCollectionConfigDao dataCollectionDao) {
//        this.dataCollectionDao = dataCollectionDao;
//    }

    /* (non-Javadoc)
     * @see org.opennms.vaadin.extender.AbstractApplicationFactory#getUI()
     */
    @Override
    public UI createUI() {
        PluginManagerAdminApplication app = new PluginManagerAdminApplication();
//        app.setDataCollectionDao(dataCollectionDao);
        return app;
    }

    /* (non-Javadoc)
     * @see org.opennms.vaadin.extender.AbstractApplicationFactory#getUIClass()
     */
    @Override
    public Class<? extends UI> getUIClass() {
        return PluginManagerAdminApplication.class;
    }

}
