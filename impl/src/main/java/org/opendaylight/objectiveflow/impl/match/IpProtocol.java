/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *  
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.match;

import org.opendaylight.objectiveflow.api.Match;
import org.opendaylight.objectiveflow.api.IpProtocolType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.IpMatchBuilder;

public class IpProtocol implements Match {
    private IpProtocolType protocolType;

    public IpProtocol(IpProtocolType protocolType) {
        this.protocolType = protocolType;
    }

    @Override
    public void setup(MatchBuilder matchBuilder) {
        final IpMatchBuilder builder = new IpMatchBuilder();
        builder.setIpProtocol(protocolType.getValue());
        matchBuilder.setIpMatch(builder.build());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("IpProtocol{");
        sb.append("protocolType=").append(protocolType);
        sb.append('}');
        return sb.toString();
    }
}
