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

import static org.junit.Assert.assertEquals;


public class GroupDirectoryTest {
    private GroupDirectory directory;

    class InnerCreator {
        Group apply(String groupName) {
            return directory.createGroup("mygroup", Group.Type.All, this.getClass());
        }
    }

    @Test
    public void createSameGroup() throws Exception {
        final Group first = directory.createGroup("mygroup", Group.Type.All, this.getClass());
        assertEquals(0, first.getId());
        assertEquals(this.getClass().getName(), first.getCreatorClassName());
        assertEquals(Group.Type.All, first.getType());

        final Group second = new InnerCreator().apply("mygroup");

        assertEquals(first.getId(), second.getId());
        assertEquals(first.getCreatorClassName(), second.getCreatorClassName());
    }

    @Test
    public void createTwoGroups() throws Exception {
        final Group first = directory.createGroup("group0", Group.Type.Select, this.getClass());
        assertEquals(0, first.getId());
        assertEquals(this.getClass().getName(), first.getCreatorClassName());
        assertEquals(Group.Type.Select, first.getType());

        final Group second = directory.createGroup("group1", Group.Type.Indirect, this.getClass());
        assertEquals(1, second.getId());
        assertEquals(this.getClass().getName(), second.getCreatorClassName());
        assertEquals(Group.Type.Indirect, second.getType());
    }

    @Test
    public void createReusedId () throws Exception {
        final Group first = directory.createGroup("group0", Group.Type.FastForward, this.getClass());
        assertEquals(0, first.getId());
        assertEquals(this.getClass().getName(), first.getCreatorClassName());
        assertEquals(Group.Type.FastForward, first.getType());

        final Group second = directory.createGroup("group0", first.getId(), Group.Type.FastForward, this.getClass());
        assertEquals(0, second.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createFailReusedId () throws Exception {
        final Group first = directory.createGroup("group0", Group.Type.All, this.getClass());
        directory.createGroup("another_name", first.getId(), Group.Type.All, this.getClass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createFailBadType () throws Exception {
        final Group first = directory.createGroup("group0", Group.Type.All, this.getClass());
        directory.createGroup("group0", first.getId(), Group.Type.Select, this.getClass());
    }

    @Test
    public void createGroupManualId() throws Exception {
        for (short ii = 0; ii < 100; ++ii) {
            short groupId = (short) (ii * 10);
            Group group = directory.createGroup(new StringBuffer("group-").append(ii).toString(), groupId, Group.Type.All, this.getClass());
            assertEquals(groupId, group.getId());
        }
    }

    @Test
    public void getGroup() throws Exception {
        for (int ii = 0; ii < 100; ++ ii) {
            Group group = directory.createGroup(new StringBuffer("group-").append(ii).toString(), Group.Type.All, this.getClass());
            assertEquals(ii, group.getId());
        }

        Group group = directory.getGroup("group-50");
        assertEquals(50, group.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getGroupNotFound() throws Exception {
        directory.getGroup("group-50");
    }

    @Before
    public void setUp() throws Exception {
        directory = new GroupDirectory();
    }
}