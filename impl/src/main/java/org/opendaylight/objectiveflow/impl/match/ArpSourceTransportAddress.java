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
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.ArpMatchBuilder;

public class ArpSourceTransportAddress implements Match {
    CidrNotation cidrNotation;

    public ArpSourceTransportAddress(CidrNotation cidrNotation) {
        this.cidrNotation = cidrNotation;
    }

    @Override
    public void setup(MatchBuilder matchBuilder) {
        final ArpMatchBuilder builder = new ArpMatchBuilder();
        builder.setArpSourceTransportAddress(new Ipv4Prefix(cidrNotation.getIpv4Address()));
        matchBuilder.setLayer3Match(builder.build());
    }
}
