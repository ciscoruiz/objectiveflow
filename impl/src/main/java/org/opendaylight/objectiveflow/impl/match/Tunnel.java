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
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.TunnelBuilder;

import java.math.BigInteger;

public class Tunnel implements Match {
    BigInteger tunnelId;
    BigInteger mask;

    public Tunnel(BigInteger tunnelId, BigInteger mask) {
        this.tunnelId = tunnelId;
        this.mask = mask;
    }

    public Tunnel(BigInteger tunnelId) {
        this.tunnelId = tunnelId;
        this.mask = null;
    }

    @Override
    public void setup(MatchBuilder matchBuilder) {
        final TunnelBuilder builder = new TunnelBuilder();
        builder.setTunnelId(tunnelId);
        if (mask != null)
            builder.setTunnelMask(mask);
        matchBuilder.setTunnel(builder.build());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Tunnel{");
        sb.append("tunnelId=").append(tunnelId);
        sb.append(", mask=").append(String.format("0x%x", mask));
        sb.append('}');
        return sb.toString();
    }
}
