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
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.VlanMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.vlan.match.fields.VlanIdBuilder;

public class VlanId implements Match {
    int value;

    public VlanId(int value) {
        this.value = value;
    }

    @Override
    public void setup(MatchBuilder matchBuilder) {
        final VlanMatchBuilder builder = new VlanMatchBuilder();
        builder.setVlanId(buildVlanId());
        matchBuilder.setVlanMatch(builder.build());
    }

    org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.vlan.match.fields.VlanId buildVlanId() {
        final VlanIdBuilder vlanIdBuilder = new VlanIdBuilder();
        vlanIdBuilder.setVlanId(new org.opendaylight.yang.gen.v1.urn.opendaylight.l2.types.rev130827.VlanId(value));
        vlanIdBuilder.setVlanIdPresent(value != 0);
        return vlanIdBuilder.build();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("VlanId{");
        sb.append("value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
