/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl;

import org.junit.Before;
import org.junit.Test;

import org.opendaylight.objectiveflow.api.Group;
import org.opendaylight.objectiveflow.impl.action.Output;
import org.opendaylight.objectiveflow.impl.action.PopVlan;

import static org.junit.Assert.*;

public class GroupTest {
    private GroupDirectory directory;

    @Test
    public void build() throws Exception {
        Group group = directory.createGroup("mygroup", Group.Type.All, this.getClass());
        Bucket bucket = new Bucket();
        bucket.setWeight(100).setWatchPort(1111);
        bucket.append(new Output("org.dummy.com"));
        bucket.append(new PopVlan());
        group.append(bucket);

        org.opendaylight.yang.gen.v1.urn.opendaylight.group.types.rev131018.groups.Group build = group.build();

        assertEquals(0, build.getGroupId().getValue().intValue());
        assertEquals("mygroup", build.getGroupName());
        assertEquals(Group.Type.All.getValue(), build.getGroupType().getIntValue());
        assertEquals(1, build.getBuckets().getBucket().size());
        assertEquals(2, build.getBuckets().getBucket().get(0).getAction().size());
    }

    @Before
    public void setUp() throws Exception {
        directory = new GroupDirectory();
    }

}