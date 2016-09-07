/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *  
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl.action;

import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.field._case.SetFieldBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.ProtocolMatchFieldsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.protocol.match.fields.Pbb;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.protocol.match.fields.PbbBuilder;

public class SetPbbIsid extends AbstractSetField {
    private long label;

    public SetPbbIsid(long label) {
        this.label = label;
    }

    @Override
    SetFieldBuilder createFieldBuilder() {
        Pbb pbb = new PbbBuilder().setPbbIsid(label).build();
        return new SetFieldBuilder().setProtocolMatchFields(new ProtocolMatchFieldsBuilder().setPbb(pbb).build());
    }

    @Override
    public String toString() {
        return "SetPbbIsid{" +
                "label=" + label +
                '}';
    }
}
