/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Directory {
    private static final Logger LOG = LoggerFactory.getLogger(Directory.class);

    private HashMap<String, Table> names = new HashMap<String,Table>();
    private HashMap<Short, String> ids = new HashMap<Short, String>();

    public Directory() {
    }

    public Table createTable(String tableName, Class<?> creatorClassName) {
        Table result = names.get(tableName);

        if (result != null) {
            return result;
        }

        synchronized (names) {
            result = names.get(tableName);

            if (result != null) {
                LOG.warn("Current Caller='{}'. Table '{}' already registered by caller '{}'",
                        creatorClassName, tableName, result.getCreatorClassName()
                );
                return result;
            }

            short idTable = getFreeId();

            if (idTable != Short.MAX_VALUE)
                result = newTable(tableName,idTable,creatorClassName);

            if (result == null)
                throw new IllegalArgumentException("There is not any free ID for table '" + tableName + "'");
        }

        return result;
    }

    public Table createTable(String tableName, short idTable, Class<?> creatorClassName) {
        Table result = findTableWithId(tableName,idTable);

        if (result != null) {
            return result;
        }

        synchronized (names) {
            result = findTableWithId(tableName,idTable);

            if (result != null) {
                return result;
            }

            String registeredTableName = ids.get(idTable);

            if (registeredTableName != null)
                throw new IllegalArgumentException("Table Name='" + tableName + "' already registered with ID='" + idTable + "'");

            result = newTable(tableName,idTable,creatorClassName);
        }

        return result;
    }

    public Table getTable(String tableName) {
        Table result = names.get(tableName);

        if (result == null)
            throw new IllegalArgumentException("Table '" + tableName + "' was not registered");

        return result;
    }

    Table findTableWithId(String tableName, short idTable) {
        Table result = names.get(tableName);

        if (result != null && result.getId() != idTable) {
            throw new IllegalArgumentException("Table " + result.toString() + " was defined with different ID='" + idTable + "'");
        }

        return result;
    }

    Table newTable(String tableName, short idTable, Class<?> creatorClassName) {
        Table result;
        names.put(tableName, result = new Table(tableName, idTable, creatorClassName.getName()));
        ids.put(idTable, tableName);
        LOG.debug("Table '{}' registered by caller '{}'", tableName, creatorClassName.getName());
        return result;
    }

    short getFreeId() {
        for (short ii = 0; ii < Short.MAX_VALUE; ++ ii) {
            if (ids.get(ii) == null) {
                return ii;
            }
        }

        return Short.MAX_VALUE;
    }
}
