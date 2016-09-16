/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.match;

import org.opendaylight.objectiveflow.api.Match;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev100924.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetSourceBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.EthernetMatchBuilder;

public class EthernetSource implements Match {
    private String macAddress;

    public EthernetSource(String macAddress) {
        this.macAddress = macAddress;
    }
    @Override
    public void setup(MatchBuilder matchBuilder) {
        final EthernetMatchBuilder builder = new EthernetMatchBuilder();
        builder.setEthernetSource(new EthernetSourceBuilder().setAddress(new MacAddress(macAddress)).build());
        matchBuilder.setEthernetMatch(builder.build());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EthernetSource{");
        sb.append("macAddress='").append(macAddress).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

