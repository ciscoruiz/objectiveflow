/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.instruction;

import java.util.ArrayList;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.Instruction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.ApplyActionsCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.apply.actions._case.ApplyActionsBuilder;

public class ApplyActions extends InstructionWithActions {
    @Override
    Instruction createInstruction(ArrayList<Action> actions) {
        return new ApplyActionsCaseBuilder().setApplyActions(new ApplyActionsBuilder().setAction(actions).build()).build();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ApplyActions{");
        sb.append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
