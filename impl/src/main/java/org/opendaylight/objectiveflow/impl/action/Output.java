/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.action;

import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Uri;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.OutputActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.output.action._case.OutputAction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.output.action._case.OutputActionBuilder;

public class Output extends AbstractAction {
    String port;
    int maxLength;

    public Output(String port, int maxLength) {
        this.port = port;
        this.maxLength = maxLength;
    }

    public Output(String port) {
        this.port = port;
        this.maxLength = 0;
    }

    @Override
    Action createAction() {
        OutputAction action = new OutputActionBuilder().setMaxLength(maxLength).setOutputNodeConnector(new Uri(port)).build();
        return new OutputActionCaseBuilder().setOutputAction(action).build();
    }

    @Override
    public String toString() {
        return "Output{" +
                "port='" + port + '\'' +
                ", maxLength=" + maxLength +
                '}';
    }
}
