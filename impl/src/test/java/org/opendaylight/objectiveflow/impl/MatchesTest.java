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
import org.opendaylight.objectiveflow.api.IpProtocolType;
import org.opendaylight.objectiveflow.impl.match.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.Match;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.EthernetMatch;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.ProtocolMatchFields;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.VlanMatch;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.ArpMatch;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.Ipv4Match;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._4.match.TcpMatch;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._4.match.UdpMatch;

import java.math.BigInteger;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class MatchesTest {
    MatchBuilder matchBuilder = null;

    @Before
    public void setUp() throws Exception {
        matchBuilder = new MatchBuilder();
    }

    @Test
    public void testArpOp() throws Exception {
        org.opendaylight.objectiveflow.api.Match op = new ArpOp(22);
        op.setup(matchBuilder);
        ArpMatch match = (ArpMatch) matchBuilder.build().getLayer3Match();
        assertEquals(Integer.valueOf(22), match.getArpOp());
    }

    @Test
    public void testArpSourceTransportAddress() throws Exception {
        CidrNotation cidr = new CidrNotation(0x1f000000, (short) 32);
        org.opendaylight.objectiveflow.api.Match op = new ArpSourceTransportAddress(cidr);
        op.setup(matchBuilder);

        ArpMatch match = (ArpMatch) matchBuilder.build().getLayer3Match();
        assertEquals("31.0.0.0/32", match.getArpSourceTransportAddress().getValue());
//        System.out.println(matchBuilder.build().toString());
    }

    @Test
    public void testArpTargetTransportAddress() throws Exception {
        CidrNotation cidr = new CidrNotation(0x1f000001, (short) 32);
        org.opendaylight.objectiveflow.api.Match op = new ArpTargetTransportAddress(cidr);
        op.setup(matchBuilder);

        ArpMatch match = (ArpMatch) matchBuilder.build().getLayer3Match();
        assertEquals("31.0.0.1/32", match.getArpTargetTransportAddress().getValue());
    }

    @Test
    public void testEthernetDestination() throws Exception {
        final org.opendaylight.objectiveflow.api.Match op = new EthernetDestination("00:00:00:01:02:03");
        op.setup(matchBuilder);
        EthernetMatch match = (EthernetMatch) matchBuilder.build().getEthernetMatch();
        assertEquals("00:00:00:01:02:03", match.getEthernetDestination().getAddress().getValue());
    }

    @Test
    public void testEthernetSource() throws Exception {
        final org.opendaylight.objectiveflow.api.Match op = new EthernetSource("e6:00:00:01:02:03");
        op.setup(matchBuilder);
        EthernetMatch match = (EthernetMatch) matchBuilder.build().getEthernetMatch();
        assertEquals("e6:00:00:01:02:03", match.getEthernetSource().getAddress().getValue());
    }
    @Test
    public void testEthernetType() throws Exception {
        org.opendaylight.objectiveflow.api.Match op = new EthernetType(org.opendaylight.objectiveflow.api.EthernetType.IPV4);
        op.setup(matchBuilder);
        EthernetMatch match = (EthernetMatch) matchBuilder.build().getEthernetMatch();
        assertEquals(org.opendaylight.objectiveflow.api.EthernetType.IPV4.getValue(), match.getEthernetType().getType().getValue().intValue());

        op = new EthernetType(org.opendaylight.objectiveflow.api.EthernetType.Multicast);
        op.setup(matchBuilder);
        match = (EthernetMatch) matchBuilder.build().getEthernetMatch();
        assertEquals(org.opendaylight.objectiveflow.api.EthernetType.Multicast.getValue(), match.getEthernetType().getType().getValue().intValue());
    }

    @Test
    public void testInPort() throws Exception {
        org.opendaylight.objectiveflow.api.Match op = new InPort(BigInteger.ONE, BigInteger.TEN);
        op.setup(matchBuilder);
        assertEquals("openflow:1:10", matchBuilder.build().getInPort().getValue());
    }

    @Test
    public void testIpProtocol() throws Exception {
        org.opendaylight.objectiveflow.api.Match op = new IpProtocol(IpProtocolType.UDP);
        op.setup(matchBuilder);
        assertEquals (IpProtocolType.UDP.getValue(), matchBuilder.build().getIpMatch().getIpProtocol().shortValue());
    }

    @Test
    public void testIpv4Destination() throws Exception {
        CidrNotation cidr = new CidrNotation(0x7f000000, (short) 24);
        org.opendaylight.objectiveflow.api.Match op = new Ipv4Destination(cidr);
        op.setup(matchBuilder);
        final Ipv4Match match = (Ipv4Match) matchBuilder.build().getLayer3Match();
        assertEquals("127.0.0.0/24", match.getIpv4Destination().getValue());
    }

    @Test
    public void testIpv4Source() throws Exception {
        CidrNotation cidr = new CidrNotation(0xc0a80101, (short) 0);
        org.opendaylight.objectiveflow.api.Match op = new Ipv4Source(cidr);
        op.setup(matchBuilder);
        final Ipv4Match match = (Ipv4Match) matchBuilder.build().getLayer3Match();
        assertEquals("192.168.1.1/0", match.getIpv4Source().getValue());
    }

    @Test
    public void testMetadata() throws Exception {
        org.opendaylight.objectiveflow.api.Match op = new Metadata(BigInteger.ONE, BigInteger.TEN);
        op.setup(matchBuilder);
        final org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.Metadata metadata = matchBuilder.build().getMetadata();
        assertEquals(BigInteger.ONE, metadata.getMetadata());
        assertEquals(BigInteger.TEN, metadata.getMetadataMask());
    }

    @Test
    public void testMplsLabel() throws Exception {
        org.opendaylight.objectiveflow.api.Match op = new MplsLabel(Long.valueOf(1024));
        op.setup(matchBuilder);
        final ProtocolMatchFields match = matchBuilder.build().getProtocolMatchFields();
        assertEquals(Long.valueOf(1024), match.getMplsLabel());
    }

    @Test
    public void testPbbIsid() throws Exception {
        org.opendaylight.objectiveflow.api.Match op = new PbbIsid(Long.valueOf(1024));
        op.setup(matchBuilder);
        final ProtocolMatchFields match = matchBuilder.build().getProtocolMatchFields();
        assertEquals(Long.valueOf(1024), match.getPbb().getPbbIsid());
    }

    @Test
    public void testTcpDestinationPort() throws Exception {
        org.opendaylight.objectiveflow.api.Match op = new TcpDestinationPort(2048);
        op.setup(matchBuilder);
        final TcpMatch match = (TcpMatch) matchBuilder.build().getLayer4Match();
        assertEquals(2048, match.getTcpDestinationPort().getValue().intValue());
    }

    @Test
    public void testTcpSourcePort() throws Exception {
        org.opendaylight.objectiveflow.api.Match op = new TcpSourcePort(4095);
        op.setup(matchBuilder);
        final TcpMatch match = (TcpMatch) matchBuilder.build().getLayer4Match();
        assertEquals(4095, match.getTcpSourcePort().getValue().intValue());
    }

    @Test
    public void testTunnel() throws Exception {
        org.opendaylight.objectiveflow.api.Match op = new Tunnel(BigInteger.ONE, BigInteger.TEN);
        op.setup(matchBuilder);
        org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.Tunnel tunnel = matchBuilder.build().getTunnel();
        assertEquals(BigInteger.ONE, tunnel.getTunnelId());
        assertEquals(BigInteger.TEN, tunnel.getTunnelMask());

        op = new Tunnel(BigInteger.TEN);
        op.setup(matchBuilder);
        tunnel = matchBuilder.build().getTunnel();
        assertEquals(BigInteger.TEN, tunnel.getTunnelId());
        assertEquals(null, tunnel.getTunnelMask());
    }

    @Test
    public void testUdpDestinationPort() throws Exception {
        org.opendaylight.objectiveflow.api.Match op = new UdpDestinationPort(2048);
        op.setup(matchBuilder);
        final UdpMatch match = (UdpMatch) matchBuilder.build().getLayer4Match();
        assertEquals(2048, match.getUdpDestinationPort().getValue().intValue());
    }

    @Test
    public void testUdpSourcePort() throws Exception {
        org.opendaylight.objectiveflow.api.Match op = new UdpSourcePort(4095);
        op.setup(matchBuilder);
        final UdpMatch match = (UdpMatch) matchBuilder.build().getLayer4Match();
        assertEquals(4095, match.getUdpSourcePort().getValue().intValue());
    }

    @Test
    public void testVlanId() throws Exception {
        org.opendaylight.objectiveflow.api.Match op = new VlanId(2323);
        op.setup(matchBuilder);
        VlanMatch match = matchBuilder.build().getVlanMatch();
        assertEquals(2323, match.getVlanId().getVlanId().getValue().intValue());
        assertTrue(match.getVlanId().isVlanIdPresent());

        op = new VlanId(0);
        op.setup(matchBuilder);
        match = matchBuilder.build().getVlanMatch();
        assertEquals(0, match.getVlanId().getVlanId().getValue().intValue());
        assertFalse(match.getVlanId().isVlanIdPresent());
    }

    @Test
    public void testCombined() throws Exception {
        {
            org.opendaylight.objectiveflow.api.Match op = new TcpDestinationPort(2048);
            op.setup(matchBuilder);
        }
        {
            org.opendaylight.objectiveflow.api.Match op = new EthernetSource("e6:00:00:01:02:03");
            op.setup(matchBuilder);
        }

        final Match build = matchBuilder.build();

        {
            TcpMatch match = (TcpMatch) build.getLayer4Match();
            assertEquals(2048, match.getTcpDestinationPort().getValue().intValue());
        }
        {
            EthernetMatch match = (EthernetMatch) build.getEthernetMatch();
            assertEquals("e6:00:00:01:02:03", match.getEthernetSource().getAddress().getValue());
        }
    }
}