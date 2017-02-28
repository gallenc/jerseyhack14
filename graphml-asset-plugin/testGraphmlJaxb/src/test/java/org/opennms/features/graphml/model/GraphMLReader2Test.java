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

package org.opennms.features.graphml.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Test;

public class GraphMLReader2Test {

	@Test
	public void verifyRead1() throws InvalidGraphException, FileNotFoundException {
		GraphML graphML = GraphMLReader.read(getClass().getResourceAsStream("/test-graph.xml"));

		GraphMLWriter.write(graphML, new File("target/output1.graphml"));
		GraphML read = GraphMLReader.read(new FileInputStream("target/output1.graphml"));
		Assert.assertEquals(read, graphML);

	}

	/**
	 * test based upon generated topology
	 * @throws InvalidGraphException
	 * @throws FileNotFoundException
	 */
	@Test
	public void verifyRead2() throws InvalidGraphException, FileNotFoundException {
		GraphML graphML = GraphMLReader.read(getClass().getResourceAsStream("/graphmlTestTopology2.xml"));

		GraphMLWriter.write(graphML, new File("target/output2.graphml"));
		GraphML read = GraphMLReader.read(new FileInputStream("target/output2.graphml"));
		Assert.assertEquals(read, graphML);

	}

	/**
	 * test based upon documentation topology examples
	 * @throws InvalidGraphException
	 * @throws FileNotFoundException
	 */
	@Test
	public void verifyRead3() throws InvalidGraphException, FileNotFoundException {

		GraphML graphML = GraphMLReader.read(getClass().getResourceAsStream("/graphmlTestTopologyDoc.xml"));
		GraphMLWriter.write(graphML, new File("target/output3.graphml"));
		GraphML read = GraphMLReader.read(new FileInputStream("target/output3.graphml"));
		Assert.assertEquals(read, graphML);

	}

	/**
	 * test based upon returned xml from OpenNMS graphml rest interface
	 * @throws InvalidGraphException
	 * @throws FileNotFoundException
	 */
	@Test
	public void verifyRead4() throws InvalidGraphException, FileNotFoundException {

		GraphML graphML = GraphMLReader.read(getClass().getResourceAsStream("/returnedAssetTopology.xml"));
		GraphMLWriter.write(graphML, new File("target/output4.graphml"));
		GraphML read = GraphMLReader.read(new FileInputStream("target/output4.graphml"));
		Assert.assertEquals(read, graphML);

	}
}
