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
import org.opendaylight.yang.gen.v1.urn.opendaylight.l2.types.rev130827.EtherType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetTypeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.EthernetMatchBuilder;

public class EthernetType implements Match {
    private int ethernetType;

    public EthernetType(int ethernetType) {
        this.ethernetType = ethernetType;
    }

    @Override
    public void setup(MatchBuilder matchBuilder) {
        final EthernetMatchBuilder builder = new EthernetMatchBuilder();
        builder.setEthernetType(new EthernetTypeBuilder().setType(new EtherType((long) ethernetType)).build());
        matchBuilder.setEthernetMatch(builder.build());
    }
}
