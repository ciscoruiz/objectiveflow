/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cisco on 7/09/16.
 */
public class CidrNotationTest {
    @Test
    public void verifyIpPrefix() throws Exception {
        final CidrNotation cidrNotation = new CidrNotation(0xff00ff00, (short) 10);
        assertEquals(0xff00ff00, cidrNotation.getIpPrefix());

        cidrNotation.setIpPrefix(0xff000000);
        assertEquals(0xff000000, cidrNotation.getIpPrefix());
    }

    @Test
    public void verifyMask() throws Exception {
        final CidrNotation cidrNotation = new CidrNotation(0xff00ff00, (short) 22);
        assertEquals(22, cidrNotation.getMask());

        cidrNotation.setMask((short) 11);
        assertEquals(11, cidrNotation.getMask());
    }

    @Test
    public void getIpv4Address() throws Exception {
        CidrNotation cidrNotation = new CidrNotation(0xff00ff00, (short) 10);
        assertEquals("255.0.255.0/10", cidrNotation.getIpv4Address());

        cidrNotation.setIpPrefix(0x0a0b0c0d);
        cidrNotation.setMask((short) 32);
        assertEquals("10.11.12.13/32", cidrNotation.getIpv4Address());

        cidrNotation.setIpPrefix(0);
        cidrNotation.setMask((short) 0);
        assertEquals("0.0.0.0/0", cidrNotation.getIpv4Address());
    }

    @Test
    public void initWithAddress() throws Exception {
        CidrNotation cidrNotation = new CidrNotation("192.168.10.1", (short) 10);
        assertEquals("192.168.10.1/10", cidrNotation.getIpv4Address());

        cidrNotation = new CidrNotation("255.255.0.192", (short) 9);
        assertEquals("255.255.0.192/9", cidrNotation.getIpv4Address());

        cidrNotation = new CidrNotation("0.0.0.0", (short) 0);
        assertEquals("0.0.0.0/0", cidrNotation.getIpv4Address());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failIpv4Address() throws Exception {
        CidrNotation cidrNotation = new CidrNotation(0xff00ff00, (short) 33);
        cidrNotation.getIpv4Address();
    }
}