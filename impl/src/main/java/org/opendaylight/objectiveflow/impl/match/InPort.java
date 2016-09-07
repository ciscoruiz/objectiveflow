/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.match;

import org.opendaylight.objectiveflow.api.Match;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;

import java.math.BigInteger;

public class InPort implements Match {
    BigInteger fromPort;
    BigInteger toPort;

    public InPort(BigInteger fromPort, BigInteger toPort) {
        this.fromPort = fromPort;
        this.toPort = toPort;
    }

    @Override
    public void setup(MatchBuilder matchBuilder) {
        StringBuffer nodeConnectorId = new StringBuffer().append("openflow:").append(fromPort).append(':').append(toPort);
        matchBuilder.setInPort(new NodeConnectorId(nodeConnectorId.toString()));
    }
}
