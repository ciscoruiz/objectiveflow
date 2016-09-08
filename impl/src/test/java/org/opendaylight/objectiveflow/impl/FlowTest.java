/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl;

import org.junit.Test;

import static org.junit.Assert.*;

public class FlowTest {
    @Test(expected = IllegalArgumentException.class)
    public void withoutId() throws Exception {
        Flow flow = new Flow("myflow");
        flow.build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void withoutTable() throws Exception {
        Flow flow = new Flow("myflow");
        flow.setId("myid");
        flow.build();
    }

}