/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.action;

import org.opendaylight.objectiveflow.api.Table;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.action.rev140714.add.group.input.buckets.bucket.action.action.NxActionResubmitRpcAddGroupCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.action.rev140714.nx.action.resubmit.grouping.NxResubmitBuilder;

public class NxResubmit extends AbstractAction {
    private Table table;

    public NxResubmit(Table table) {
        this.table = table;
    }

    @Override
    Action createAction() {
        org.opendaylight.yang.gen.v1.urn.opendaylight.openflowplugin.extension.nicira.action.rev140714.nx.action.resubmit.grouping.NxResubmit action = new NxResubmitBuilder().setTable(table.getId()).build();
        return new NxActionResubmitRpcAddGroupCaseBuilder().setNxResubmit(action).build();
    }

    @Override
    public String toString() {
        return "NxResubmit{" +
                "table=" + table +
                '}';
    }
}
