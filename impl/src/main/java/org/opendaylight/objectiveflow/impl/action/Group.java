/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.action;

import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.GroupActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.group.action._case.GroupAction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.group.action._case.GroupActionBuilder;


public class Group extends AbstractAction {
    private long groupId;

    public Group(long groupId) {
        this.groupId = groupId;
    }

    @Override
    Action createAction() {
        GroupAction action = new GroupActionBuilder().setGroupId(groupId).build();
        return new GroupActionCaseBuilder().setGroupAction(action).build();
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                '}';
    }
}
