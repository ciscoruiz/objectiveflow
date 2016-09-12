/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.objectiveflow.api;

public interface Group {
    enum Type {
        All(0), Select(1), Indirect(2), FastForward(3);

        int value;

        Type(int value) { this.value = value; }

        public int getValue()  { return value; }
    }

    int getId();
    Type getType();
    Group append(Bucket bucket);
    org.opendaylight.yang.gen.v1.urn.opendaylight.group.types.rev131018.groups.Group build();
}
