/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.action;

import org.junit.Before;
import org.junit.Test;
import org.opendaylight.objectiveflow.api.EthernetType;
import org.opendaylight.objectiveflow.api.IpProtocolType;
import org.opendaylight.objectiveflow.impl.CidrNotation;
import org.opendaylight.objectiveflow.impl.Directory;
import org.opendaylight.objectiveflow.impl.Table;
import org.opendaylight.objectiveflow.impl.match.IpProtocol;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.OutputPortValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.IpMatch;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.Tunnel;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.Ipv4Match;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._4.match.TcpMatch;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._4.match.UdpMatch;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.action.rev140714.add.group.input.buckets.bucket.action.action.NxActionResubmitRpcAddGroupCase;

import java.math.BigInteger;
import java.net.InetAddress;

import static org.junit.Assert.*;

public class ActionsTest {
    Directory directory;

    @Test
    public void testDrop() throws Exception {
        final Drop action = new Drop();

        final Action build = action.build(10);
        assertEquals(10, build.getKey().getOrder().intValue());
        DropActionCase action1 = (DropActionCase) build.getAction();
        assertNotNull(action1);
    }

    @Test
    public void testForwardToController() throws Exception {
        final ForwardToController action = new ForwardToController();

        final Action build = action.build(11);
        assertEquals(11, build.getKey().getOrder().intValue());

        final OutputActionCase actionCase = (OutputActionCase) build.getAction();
        assertEquals(OutputPortValues.CONTROLLER.toString(), actionCase.getOutputAction().getOutputNodeConnector().getValue().toString());
    }

    @Test
    public void testGroup() throws Exception {
        final Group action = new Group(100);

        final Action build = action.build(0);
        final GroupActionCase actionCase = (GroupActionCase) build.getAction();
        assertEquals(100, actionCase.getGroupAction().getGroupId().intValue());
    }

    @Test
    public void testNxResubmit() throws Exception {
        Table table = directory.createTable("mytable", this.getClass());
        final NxResubmit action = new NxResubmit(table);

        final Action build = action.build(0);
        final NxActionResubmitRpcAddGroupCase actionCase = (NxActionResubmitRpcAddGroupCase) build.getAction();
        assertEquals(table.getId(), actionCase.getNxResubmit().getTable().shortValue());
    }

    @Test
    public void testOutput() throws Exception {
        Output action = new Output("com.ericsson", 2048);

        Action build = action.build(11);
        OutputActionCase actionCase = (OutputActionCase) build.getAction();
        assertEquals("com.ericsson", actionCase.getOutputAction().getOutputNodeConnector().getValue().toString());
        assertEquals(2048, actionCase.getOutputAction().getMaxLength().intValue());

        action = new Output("some-uuid");
        build = action.build(10);
        actionCase = (OutputActionCase) build.getAction();
        assertEquals("some-uuid", actionCase.getOutputAction().getOutputNodeConnector().getValue().toString());
        assertEquals(0, actionCase.getOutputAction().getMaxLength().intValue());
    }

    @Test
    public void testPopMpls() throws Exception {
        PopMpls action = new PopMpls();

        Action build = action.build(11);
        final PopMplsActionCase actionCase = (PopMplsActionCase) build.getAction();
        assertEquals(EthernetType.IPV4.getValue(), actionCase.getPopMplsAction().getEthernetType().intValue());
    }

    @Test
    public void testPopPbb() throws Exception {
        final PopPbb action = new PopPbb();

        final Action build = action.build(10);
        assertEquals(10, build.getKey().getOrder().intValue());
        PopPbbActionCase action1 = (PopPbbActionCase) build.getAction();
        assertNotNull(action1);
    }

    @Test
    public void testPopVlan() throws Exception {
        final PopVlan action = new PopVlan();

        final Action build = action.build(10);
        assertEquals(10, build.getKey().getOrder().intValue());
        PopVlanActionCase action1 = (PopVlanActionCase) build.getAction();
        assertNotNull(action1);
    }

    @Test
    public void testPushMpls() throws Exception {
        PushMpls action = new PushMpls();

        Action build = action.build(11);
        final PushMplsActionCase actionCase = (PushMplsActionCase) build.getAction();
        assertEquals(EthernetType.Multicast.getValue(), actionCase.getPushMplsAction().getEthernetType().intValue());
    }

    @Test
    public void testPushPbb() throws Exception {
        PushPbb action = new PushPbb();

        Action build = action.build(11);
        final PushPbbActionCase actionCase = (PushPbbActionCase) build.getAction();
        assertEquals(EthernetType.ProviderBackboneBridges.getValue(), actionCase.getPushPbbAction().getEthernetType().intValue());
    }

    @Test
    public void testPushVlan() throws Exception {
        PushVlan action = new PushVlan();

        Action build = action.build(11);
        final PushVlanActionCase actionCase = (PushVlanActionCase) build.getAction();
        assertEquals(EthernetType.VlanTaggedFrame.getValue(), actionCase.getPushVlanAction().getEthernetType().intValue());
    }

    @Test
    public void testSetEthernetDestination() throws Exception {
        final SetEthernetDestination action = new SetEthernetDestination("00:00:ff:ee:cc:00");
        Action build = action.build(0);
        final SetFieldCase actionCase = (SetFieldCase) build.getAction();
        assertEquals("00:00:ff:ee:cc:00", actionCase.getSetField().getEthernetMatch().getEthernetDestination().getAddress().getValue());
    }

    @Test
    public void testSetIpv4Destination() throws Exception {
        final SetIpv4Destination action = new SetIpv4Destination(new CidrNotation("192.168.1.10", (short) 16));
        Action build = action.build(0);
        final SetFieldCase actionCase = (SetFieldCase) build.getAction();
        final Ipv4Match match = (Ipv4Match) actionCase.getSetField().getLayer3Match();
        assertEquals("192.168.1.10/16", match.getIpv4Destination().getValue());
    }

    @Test
    public void testSetIpv4Source() throws Exception {
        final SetIpv4Source action = new SetIpv4Source(new CidrNotation("192.255.255.255", (short) 16));
        Action build = action.build(0);
        final SetFieldCase actionCase = (SetFieldCase) build.getAction();
        final Ipv4Match match = (Ipv4Match) actionCase.getSetField().getLayer3Match();
        assertEquals("192.255.255.255/16", match.getIpv4Source().getValue());
    }

    @Test
    public void testSetMplLabel() throws Exception {
        final SetMplsLabel action = new SetMplsLabel(1000);
        Action build = action.build(100);
        final SetFieldCase actionCase = (SetFieldCase) build.getAction();
        assertEquals(1000, actionCase.getSetField().getProtocolMatchFields().getMplsLabel().intValue());
    }

    @Test
    public void testSetPbbIsid() throws Exception {
        final SetPbbIsid action = new SetPbbIsid(1000);
        Action build = action.build(100);
        final SetFieldCase actionCase = (SetFieldCase) build.getAction();
        assertEquals(1000, actionCase.getSetField().getProtocolMatchFields().getPbb().getPbbIsid().intValue());
    }

    @Test
    public void testSetTcpDestinationPort() throws Exception {
        final SetTcpDestinationPort action = new SetTcpDestinationPort(1000);
        Action build = action.build(100);
        final SetFieldCase actionCase = (SetFieldCase) build.getAction();
        assertEquals(1000, ((TcpMatch) actionCase.getSetField().getLayer4Match()).getTcpDestinationPort().getValue().intValue());

    }

    @Test
    public void testSetTcpSourcePort() throws Exception {
        final SetTcpSourcePort action = new SetTcpSourcePort(2222);
        Action build = action.build(100);
        final SetFieldCase actionCase = (SetFieldCase) build.getAction();
        assertEquals(2222, ((TcpMatch) actionCase.getSetField().getLayer4Match()).getTcpSourcePort().getValue().intValue());
    }

    @Test
    public void testSetTunnelId() throws Exception {
        SetTunnelId action = new SetTunnelId(BigInteger.TEN);
        SetFieldCase actionCase = (SetFieldCase) action.build(100).getAction();
        Tunnel tunnel = actionCase.getSetField().getTunnel();
        assertEquals(BigInteger.TEN, tunnel.getTunnelId());
        assertEquals(null, tunnel.getTunnelMask());

        action = new SetTunnelId(BigInteger.ONE, BigInteger.TEN);
        actionCase = (SetFieldCase) action.build(100).getAction();
        tunnel = actionCase.getSetField().getTunnel();
        assertEquals(BigInteger.ONE, tunnel.getTunnelId());
        assertEquals(BigInteger.TEN, tunnel.getTunnelMask());
    }

    @Test
    public void testSetUdpDestinationPort() throws Exception {
        final SetUdpDestinationPort action = new SetUdpDestinationPort(3333);
        Action build = action.build(100);
        final SetFieldCase actionCase = (SetFieldCase) build.getAction();
        assertEquals(3333, ((UdpMatch) actionCase.getSetField().getLayer4Match()).getUdpDestinationPort().getValue().intValue());

    }

    @Test
    public void testSetUdpProtocol() throws Exception {
        final SetUdpProtocol action = new SetUdpProtocol();
        Action build = action.build(100);
        final SetFieldCase actionCase = (SetFieldCase) build.getAction();
        assertEquals(IpProtocolType.UDP.getValue(), ((IpMatch) actionCase.getSetField().getIpMatch()).getIpProtocol().shortValue());
    }

    @Test
    public void testSetUdpSourcePort() throws Exception {
        final SetUdpSourcePort action = new SetUdpSourcePort(4444);
        Action build = action.build(100);
        final SetFieldCase actionCase = (SetFieldCase) build.getAction();
        assertEquals(4444, ((UdpMatch) actionCase.getSetField().getLayer4Match()).getUdpSourcePort().getValue().intValue());
    }

    @Test
    public void testSetVlanId() throws Exception {
        SetVlanId action = new SetVlanId(1000);
        SetFieldCase actionCase = (SetFieldCase) action.build(100).getAction();
        assertEquals(1000, actionCase.getSetField().getVlanMatch().getVlanId().getVlanId().getValue().intValue());
        assertTrue(actionCase.getSetField().getVlanMatch().getVlanId().isVlanIdPresent());
    }

    @Before
    public void setUp() throws Exception {
        directory = new Directory();
    }
}