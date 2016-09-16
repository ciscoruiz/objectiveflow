/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.match;

import org.opendaylight.objectiveflow.api.Match;
import org.opendaylight.objectiveflow.impl.CidrNotation;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv4Prefix;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.Ipv4MatchBuilder;

public  class Ipv4Destination implements Match {
    CidrNotation cidrNotation;

    public Ipv4Destination(CidrNotation cidrNotation) {
        this.cidrNotation = cidrNotation;
    }

    @Override
    public void setup(MatchBuilder matchBuilder) {
        final Ipv4MatchBuilder builder = new Ipv4MatchBuilder();
        builder.setIpv4Destination(new Ipv4Prefix(cidrNotation.getIpv4Address()));
        matchBuilder.setLayer3Match(builder.build());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Ipv4Destination{");
        sb.append("cidrNotation=").append(cidrNotation);
        sb.append('}');
        return sb.toString();
    }
}
