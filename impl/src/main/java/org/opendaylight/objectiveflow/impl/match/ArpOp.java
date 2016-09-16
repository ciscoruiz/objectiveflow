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
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.ArpMatchBuilder;

public class ArpOp implements Match {
    private int operation;

    public ArpOp(int operation) {
        this.operation = operation;
    }

    @Override
    public void setup(MatchBuilder matchBuilder) {
        final ArpMatchBuilder builder = new ArpMatchBuilder();
        builder.setArpOp(operation);
        matchBuilder.setLayer3Match(builder.build());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ArpOp{");
        sb.append("operation=").append(operation);
        sb.append('}');
        return sb.toString();
    }
}
