/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *  
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.action;

import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev100924.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.field._case.SetFieldBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetDestination;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetDestinationBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.EthernetMatchBuilder;


public class SetEthernetDestination extends AbstractSetField {
    private MacAddress macAddress;

    public SetEthernetDestination(MacAddress macAddress) {
        this.macAddress = macAddress;
    }

    @Override
    SetFieldBuilder createFieldBuilder() {
        EthernetDestination ethernetDestination = new EthernetDestinationBuilder().setAddress(macAddress).build();
        return new SetFieldBuilder().setEthernetMatch(new EthernetMatchBuilder().setEthernetDestination(ethernetDestination).build());
    }

    @Override
    public String toString() {
        return "SetEthernetDestination{" +
                "macAddress=" + macAddress +
                '}';
    }
}
