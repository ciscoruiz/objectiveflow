/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *  
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.action;

import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.field._case.SetFieldBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.l2.types.rev130827.VlanId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.VlanMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.vlan.match.fields.VlanIdBuilder;


public class SetVlanId extends AbstractSetField {
    private VlanId vlanId;

    public SetVlanId(int label) {
        vlanId = new VlanId(label);
    }

    @Override
    SetFieldBuilder createFieldBuilder() {
        org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.vlan.match.fields.VlanId vlanId = new VlanIdBuilder().setVlanId(this.vlanId).setVlanIdPresent(true).build();
        return new SetFieldBuilder().setVlanMatch(new VlanMatchBuilder().setVlanId(vlanId).build());
    }

    @Override
    public String toString() {
        return "SetVlanId{" +
                "vlanId=" + vlanId +
                '}';
    }
}
