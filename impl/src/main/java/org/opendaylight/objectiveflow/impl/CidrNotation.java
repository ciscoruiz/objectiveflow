/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl;

/**
 * @see https://en.wikipedia.org/wiki/Classless_Inter-Domain_Routing#CIDR_notation
 */
public class CidrNotation {
    int ipPrefix;
    short mask;

    public CidrNotation(int ipv4Prefix, short mask) {
        this.ipPrefix = ipv4Prefix;
        this.mask = mask;
    }

    public int getIpPrefix() {
        return ipPrefix;
    }

    public void setIpPrefix(int ipPrefix) {
        this.ipPrefix = ipPrefix;
    }

    public short getMask() {
        return mask;
    }

    public void setMask(short mask) {
        this.mask = mask;
    }

    public String getIpv4Address() {
        if (mask > 32 || mask < 0) {
            throw new IllegalArgumentException(new StringBuffer ("Ipv4Prefix=").append(ipPrefix).append(" Mask=").append(mask).toString());
        }

        // See http://stackoverflow.com/questions/1957637/java-convert-int-to-inetaddress
        int ip = ipPrefix;
        String ipStr = String.format("%d.%d.%d.%d", (ip & 0xff), (ip >> 8 & 0xff),  (ip >> 16 & 0xff), (ip >> 24 & 0xff));
        StringBuffer sb = new StringBuffer(ipStr);
        sb.append('/').append(mask);
        return sb.toString();
    }
}
