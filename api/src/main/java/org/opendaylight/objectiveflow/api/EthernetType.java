/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.api;

/**
 * @see: https://en.wikipedia.org/wiki/EtherType
 */
public enum EthernetType {
    IPV4(0x0800), Multicast(0x8847), ProviderBackboneBridges(0x88e7), VlanTaggedFrame(0x8100);

    private int value;

    EthernetType(int value) { this.value = value; }

    public int getValue() { return value; }
}

