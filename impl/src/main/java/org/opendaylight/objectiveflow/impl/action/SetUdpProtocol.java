/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *  
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.action;

import org.opendaylight.objectiveflow.api.IpProtocolType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.field._case.SetFieldBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.IpMatchBuilder;


public class SetUdpProtocol extends AbstractSetField {
    @Override
    SetFieldBuilder createFieldBuilder() {
        return new SetFieldBuilder().setIpMatch(new IpMatchBuilder().setIpProtocol(IpProtocolType.UDP).build());
    }

    @Override
    public String toString() {
        return "SetUdpProtocol{}";
    }
}
