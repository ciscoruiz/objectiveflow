/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.match;

import org.opendaylight.objectiveflow.api.Match;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.PortNumber;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._4.match.TcpMatchBuilder;

public class TcpDestinationPort implements Match {
    int portNumber;

    public TcpDestinationPort(int portNumber) {
        this.portNumber = portNumber;
    }

    @Override
    public void setup(MatchBuilder matchBuilder) {
        final TcpMatchBuilder builder = new TcpMatchBuilder();
        builder.setTcpDestinationPort(new PortNumber(portNumber));
        matchBuilder.setLayer4Match(builder.build());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TcpDestinationPort{");
        sb.append("portNumber=").append(portNumber);
        sb.append('}');
        return sb.toString();
    }
}
