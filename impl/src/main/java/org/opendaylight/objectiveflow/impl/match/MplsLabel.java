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
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.ProtocolMatchFieldsBuilder;

public class MplsLabel implements Match {
    Long label;

    public MplsLabel(Long label) {
        this.label = label;
    }

    @Override
    public void setup(MatchBuilder matchBuilder) {
        final ProtocolMatchFieldsBuilder builder = new ProtocolMatchFieldsBuilder();
        builder.setMplsLabel(label);
        matchBuilder.setProtocolMatchFields(builder.build());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MplsLabel{");
        sb.append("label=").append(label);
        sb.append('}');
        return sb.toString();
    }
}
