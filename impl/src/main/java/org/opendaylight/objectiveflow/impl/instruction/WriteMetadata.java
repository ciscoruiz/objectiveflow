/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *  
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.instruction;

import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.Instruction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.WriteMetadataCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.write.metadata._case.WriteMetadataBuilder;

import java.math.BigInteger;
import java.util.ArrayList;

public class WriteMetadata extends AbstractInstruction {
    private BigInteger metadata;
    private BigInteger mask;

    public WriteMetadata(BigInteger metadata, BigInteger mask) {
        this.metadata = metadata;
        this.mask = mask;
    }

    @Override
    Instruction createInstruction(ArrayList<Action> actions) {
        return new WriteMetadataCaseBuilder().setWriteMetadata(new WriteMetadataBuilder().setMetadata(metadata).setMetadataMask(mask).build()).build();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WriteMetadata{");
        sb.append("metadata=").append(metadata);
        sb.append(", mask=").append(mask);
        sb.append('}');
        return sb.toString();
    }
}
