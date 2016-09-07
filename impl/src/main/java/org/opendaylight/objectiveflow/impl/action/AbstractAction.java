/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionKey;

public abstract class AbstractAction implements org.opendaylight.objectiveflow.api.Action {
    public Action build(int key) {
        ActionBuilder actionBuilder = new ActionBuilder();
        return actionBuilder.setAction(createAction()).setKey(new ActionKey(key)).build();
    }

    abstract org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.Action createAction();
}
