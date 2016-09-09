/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *  
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.action;

import org.opendaylight.objectiveflow.impl.CidrNotation;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv4Prefix;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.field._case.SetFieldBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.Ipv4MatchBuilder;

import java.net.InetAddress;


public class SetIpv4Destination extends AbstractSetField {
    private CidrNotation cidrNotation;

    public SetIpv4Destination(CidrNotation cidrNotation) {
        this.cidrNotation = cidrNotation;
    }

    @Override
    SetFieldBuilder createFieldBuilder() {
        Ipv4Prefix ipv4Prefix = new Ipv4Prefix(cidrNotation.getIpv4Address());
        return new SetFieldBuilder().setLayer3Match(new Ipv4MatchBuilder().setIpv4Destination(ipv4Prefix).build());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SetIpv4Destination{");
        sb.append("cidrNotation=").append(cidrNotation);
        sb.append('}');
        return sb.toString();
    }
}
