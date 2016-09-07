/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.instruction;

import org.opendaylight.objectiveflow.api.Table;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.Instruction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.GoToTableCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.go.to.table._case.GoToTableBuilder;

import java.util.ArrayList;

public class Goto extends AbstractInstruction{
    private Table table;

    public Goto(Table table) {
        this.table = table;
    }

    @Override
    Instruction createInstruction(ArrayList<Action> actions) {
        return new GoToTableCaseBuilder().setGoToTable(new GoToTableBuilder().setTableId(table.getId()).build()).build();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Goto{");
        sb.append("table=").append(table);
        sb.append('}');
        return sb.toString();
    }

}
