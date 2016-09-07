/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *  
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.action;

import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.PortNumber;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.field._case.SetFieldBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._4.match.UdpMatchBuilder;


public class SetUdpDestinationPort extends AbstractSetField {
    private PortNumber port;

    public SetUdpDestinationPort(Integer port) {
        this.port = new PortNumber(port);
    }

    @Override
    SetFieldBuilder createFieldBuilder() {
        return new SetFieldBuilder().setLayer4Match(new UdpMatchBuilder().setUdpDestinationPort(port).build());
    }

    @Override
    public String toString() {
        return "SetUdpDestinationPort{" +
                "port=" + port +
                '}';
    }
}
