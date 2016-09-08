/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.api;

/**
 * @see https://en.wikipedia.org/wiki/List_of_IP_protocol_numbers
 */
public enum IpProtocolType {
    TCP ((short) 0x06), UDP ((short) 0x11);

    private short value;

    IpProtocolType(short value) { this.value = value; }

    public short getValue() { return value; }
}
