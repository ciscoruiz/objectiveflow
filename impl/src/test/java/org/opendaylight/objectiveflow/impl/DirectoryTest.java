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

import java.util.Random;

import static org.junit.Assert.*;


public class DirectoryTest {
    private Directory directory;

    class InnerCreator {
        Table apply(String tableName) {
            return directory.createTable("mytable", this.getClass());
        }
    }

    @Test
    public void createSameTable() throws Exception {
        final Table first = directory.createTable("mytable", this.getClass());
        assertEquals(0, first.getId());
        assertEquals(this.getClass().getName(), first.getCreatorClassName());

        final Table second = new InnerCreator().apply("mytable");

        assertEquals(first.getId(), second.getId());
        assertEquals(first.getCreatorClassName(), second.getCreatorClassName());
    }

    @Test
    public void createTwoTables() throws Exception {
        final Table first = directory.createTable("table0", this.getClass());
        assertEquals(0, first.getId());
        assertEquals(this.getClass().getName(), first.getCreatorClassName());

        final Table second = directory.createTable("table1", this.getClass());
        assertEquals(1, second.getId());
        assertEquals(this.getClass().getName(), second.getCreatorClassName());
    }

    @Test
    public void createReusedId () throws Exception {
        final Table first = directory.createTable("table0", this.getClass());
        assertEquals(0, first.getId());
        assertEquals(this.getClass().getName(), first.getCreatorClassName());

        final Table second = directory.createTable("table0", first.getId(), this.getClass());
        assertEquals(0, second.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createFailReusedId () throws Exception {
        final Table first = directory.createTable("table0", this.getClass());
        directory.createTable("another_name", first.getId(), this.getClass());
    }

    @Test
    public void createTableManualId() throws Exception {
        for (short ii = 0; ii < 100; ++ii) {
            short tableId = (short) (ii * 10);
            Table table = directory.createTable(new StringBuffer("table-").append(ii).toString(), tableId, this.getClass());
            assertEquals(tableId, table.getId());
        }
    }

    @Test
    public void getTable() throws Exception {
        for (int ii = 0; ii < 100; ++ ii) {
            Table table = directory.createTable(new StringBuffer("table-").append(ii).toString(), this.getClass());
            assertEquals(ii, table.getId());
        }

        Table table = directory.getTable("table-50");
        assertEquals(50, table.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTableNotFound() throws Exception {
        directory.getTable("table-50");
    }


    @Before
    public void setUp() throws Exception {
        directory = new Directory();
    }
}