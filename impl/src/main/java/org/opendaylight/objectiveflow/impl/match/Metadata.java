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
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.MetadataBuilder;

import java.math.BigInteger;

public class Metadata implements Match {
    BigInteger value;
    BigInteger mask;

    public Metadata(BigInteger value, BigInteger mask) {
        this.value = value;
        this.mask = mask;
    }

    @Override
    public void setup(MatchBuilder matchBuilder) {
        final MetadataBuilder builder = new MetadataBuilder();
        builder.setMetadata(value).setMetadataMask(mask);
        matchBuilder.setMetadata(builder.build());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Metadata{");
        sb.append("metadata=").append(value);
        sb.append(", mask=").append(String.format("0x%x", mask));
        sb.append('}');
        return sb.toString();
    }
}
