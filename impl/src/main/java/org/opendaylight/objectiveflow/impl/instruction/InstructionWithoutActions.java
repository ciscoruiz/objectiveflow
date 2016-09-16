/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.instruction;

import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.Instruction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionKey;

import javax.management.RuntimeErrorException;
import java.util.ArrayList;

public abstract class InstructionWithoutActions implements org.opendaylight.objectiveflow.api.Instruction {
    InstructionWithoutActions() {
    }

    @Override
    public org.opendaylight.objectiveflow.api.Instruction append(org.opendaylight.objectiveflow.api.Action action) {
        throw new UnsupportedOperationException(toString() + " can not use actions");
    }

    public Instruction build(int key) {
        InstructionBuilder instructionBuilder = new InstructionBuilder();
        instructionBuilder.setInstruction(createInstruction());
        return instructionBuilder.setKey(new InstructionKey(key)).build();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("InstructionWithoutActions{");
        sb.append('}');
        return sb.toString();
    }

    abstract org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.Instruction createInstruction();
}
