/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.action;

import org.opendaylight.objectiveflow.api.EthernetType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.PopMplsActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.pop.mpls.action._case.PopMplsAction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.pop.mpls.action._case.PopMplsActionBuilder;


public class PopMpls extends AbstractAction {
    @Override
    Action createAction() {
        PopMplsAction action = new PopMplsActionBuilder().setEthernetType(EthernetType.IPV4.getValue()).build();
        return new PopMplsActionCaseBuilder().setPopMplsAction(action).build();
    }

    @Override
    public String toString() {
        return "PopMpls{}";
    }
}
