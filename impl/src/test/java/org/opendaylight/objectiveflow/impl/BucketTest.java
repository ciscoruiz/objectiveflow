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
import org.opendaylight.objectiveflow.impl.action.Drop;
import org.opendaylight.objectiveflow.impl.action.ForwardToController;

import static org.junit.Assert.*;

public class BucketTest {
    private Bucket bucket;

    @Test
    public void setWeight() throws Exception {
        bucket.setWeight(100);
        org.opendaylight.yang.gen.v1.urn.opendaylight.group.types.rev131018.group.buckets.Bucket build = bucket.build(2);
        assertEquals(100, build.getWeight().intValue());
        assertEquals(0, build.getAction().size());
    }

    @Test
    public void setWatchPort() throws Exception {
        bucket.setWatchPort(200);
        org.opendaylight.yang.gen.v1.urn.opendaylight.group.types.rev131018.group.buckets.Bucket build = bucket.build(2);
        assertEquals(200, build.getWatchPort().intValue());
        assertEquals(0, build.getAction().size());
    }
    
    @Test
    public void setWatchGroup() throws Exception {
        bucket.setWatchGroup(300);
        org.opendaylight.yang.gen.v1.urn.opendaylight.group.types.rev131018.group.buckets.Bucket build = bucket.build(2);
        assertEquals(300, build.getWatchGroup().intValue());
        assertEquals(0, build.getAction().size());
    }

    @Test
    public void append() throws Exception {
        bucket.append(new Drop());
        bucket.append(new ForwardToController());
        org.opendaylight.yang.gen.v1.urn.opendaylight.group.types.rev131018.group.buckets.Bucket build = bucket.build(2);
        assertEquals(0, build.getWatchGroup().intValue());
        assertEquals(0, build.getWatchPort().intValue());
        assertEquals(2, build.getAction().size());
    }

    @Before
    public void setUp() throws Exception {
        bucket = new Bucket();


    }
}