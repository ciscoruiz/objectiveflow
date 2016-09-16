/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.instruction;

import java.util.ArrayList;
import java.util.List;

import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.Instruction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionKey;

public abstract class InstructionWithActions implements org.opendaylight.objectiveflow.api.Instruction {
    private ArrayList<org.opendaylight.objectiveflow.api.Action> actions;

    InstructionWithActions() {
        actions = new ArrayList<>();
    }

    @Override
    public org.opendaylight.objectiveflow.api.Instruction append(org.opendaylight.objectiveflow.api.Action action) {
        actions.add(action);
        return this;
    }

    public Instruction build(int key) {
        InstructionBuilder instructionBuilder = new InstructionBuilder();
        
        int actionKey = 0;
        ArrayList<Action> rawActions = new ArrayList<>();
        for (org.opendaylight.objectiveflow.api.Action action: actions) {
            rawActions.add(action.build(actionKey++));
        }
        
        instructionBuilder.setInstruction(createInstruction(rawActions));
        return instructionBuilder.setKey(new InstructionKey(key)).build();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("InstructionWithActions{");
        sb.append("actions=").append(actions);
        sb.append('}');
        return sb.toString();
    }

    abstract org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.Instruction createInstruction(ArrayList<Action> actions);
}
