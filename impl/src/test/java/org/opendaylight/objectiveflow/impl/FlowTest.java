/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl;

import org.junit.Before;
import org.junit.Test;
import org.opendaylight.objectiveflow.impl.instruction.Goto;
import org.opendaylight.objectiveflow.impl.instruction.WriteMetadata;
import org.opendaylight.objectiveflow.impl.match.EthernetType;
import org.opendaylight.objectiveflow.impl.match.Ipv4Destination;
import org.opendaylight.objectiveflow.impl.match.Metadata;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class FlowTest {
    Directory directory;

    @Test(expected = IllegalArgumentException.class)
    public void withoutId() throws Exception {
        Table table = directory.createTable("table01", this.getClass());
        Flow flow = new Flow("myflow", table);
        org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.Flow build = flow.build();
        assertEquals("myflow", build.getId().getValue());
        assertEquals(table.getId(), build.getTableId().shortValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void withoutTable() throws Exception {
        Flow flow = new Flow("myflow", null);
        flow.setId("myid");
        flow.build();
        org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.Flow build = flow.build();
    }

    @Test
    public void routeInterVpnLink() {
        Table portDispatcher = directory.createTable("PORT_DISPATCHER", (short) 17, this.getClass());

        Flow flow = new Flow("RouteInterVpnLink", portDispatcher);

        flow.setId("12345-12345");
        flow.setPriority(1000);
        flow.setCookie(BigInteger.TEN);
        flow.setHardTimeout(2222);
        flow.setIdleTimeout(1111);
        flow.turnOff(org.opendaylight.objectiveflow.api.Flow.Flag.IsBarrier);
        flow.turnOn(org.opendaylight.objectiveflow.api.Flow.Flag.InstallHardware);

        long vpnTag = 100096;
        long mask = 0xfffff00;
        flow.addMatch(new Metadata(BigInteger.valueOf(vpnTag), BigInteger.valueOf(mask)));
        flow.addMatch(new EthernetType(org.opendaylight.objectiveflow.api.EthernetType.IPV4));
        flow.addMatch(new Ipv4Destination(new CidrNotation("192.168.10.10", (short) 32)));

        flow.addInstruction(new WriteMetadata(BigInteger.valueOf(1234), BigInteger.valueOf(0xffff)));
        flow.addInstruction(new Goto(directory.createTable("L3_INTERFACE", this.getClass())));

        org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.Flow build = flow.build();

        assertEquals("12345-12345", build.getId().getValue());
        assertEquals(1000, build.getPriority().intValue());
        assertEquals(BigInteger.TEN, build.getCookie().getValue());
        assertEquals(2222, build.getHardTimeout().intValue());
        assertEquals(1111, build.getIdleTimeout().intValue());
        assertFalse(build.isBarrier());
        assertFalse(build.isStrict());
        assertTrue(build.isInstallHw());

        assertEquals(3, flow.getMatches().size());
        assertEquals(2, flow.getInstructions().size());
    }

    @Before
    public void setUp() throws Exception {
        directory = new Directory();

    }
}