/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2016 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2016 The OpenNMS Group, Inc.
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

package org.opennms.features.graphml.model.manual;

import org.junit.Assert;
import org.junit.Test;
import org.opennms.features.graphml.model.GraphML;
import org.opennms.features.graphml.model.GraphMLGraph;
import org.opennms.features.graphml.model.GraphMLReader;
import org.opennms.features.graphml.model.InvalidGraphException;

public class GraphMLReaderTest {

    @Test
    public void verifyRead() throws InvalidGraphException {
        GraphML graphML = GraphMLReader.read(getClass().getResourceAsStream("/test-graph.xml"));
        Assert.assertEquals(2, graphML.getGraphs().size());

        GraphMLGraph graph = graphML.getGraphs().get(0);
        Assert.assertEquals("regions", graph.getId());
        Assert.assertEquals(4, graph.getNodes().size());

        graph = graphML.getGraphs().get(1);
        Assert.assertEquals("markets", graph.getId());
        Assert.assertEquals(16, graph.getNodes().size());

    }
}