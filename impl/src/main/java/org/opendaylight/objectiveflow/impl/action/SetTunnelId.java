/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *  
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.action;

import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.field._case.SetFieldBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.TunnelBuilder;

import java.math.BigInteger;


public class SetTunnelId extends AbstractSetField {
    private BigInteger tunnelId;
    private BigInteger mask;

    public SetTunnelId(BigInteger tunnelId) {
        this.tunnelId = tunnelId;
        this.mask = null;
    }

    public SetTunnelId(BigInteger tunnelId, BigInteger mask) {
        this.tunnelId = tunnelId;
        this.mask = mask;
    }

    @Override
    SetFieldBuilder createFieldBuilder() {
        TunnelBuilder tunnelBuilder = new TunnelBuilder().setTunnelId(tunnelId);

        if (mask != null)
            tunnelBuilder.setTunnelMask(mask);

        return new SetFieldBuilder().setTunnel(tunnelBuilder.build());
    }

    @Override
    public String toString() {
        return "SetTunnelId{" +
                "tunnelId=" + tunnelId +
                ", mask=" + mask +
                '}';
    }
}
