/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *  
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl;

import org.opendaylight.objectiveflow.impl.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class GroupDirectory {
    private static final Logger LOG = LoggerFactory.getLogger(GroupDirectory.class);

    private HashMap<String, Group> names = new HashMap<>();
    private HashMap<Integer, String> ids = new HashMap<>();

    public GroupDirectory() {
    }

    public Group createGroup(String groupName, Group.Type type, Class<?> creatorClassName) {
        Group result = names.get(groupName);

        if (result != null) {
            checkType(result, type);
            return result;
        }

        synchronized (names) {
            result = names.get(groupName);

            if (result != null) {
                checkType(result, type);
                LOG.warn("Current Caller='{}'. Group '{}' already registered by caller '{}'",
                        creatorClassName, groupName, result.getCreatorClassName()
                );
                return result;
            }

            int groupId = getFreeId();

            if (groupId != Integer.MAX_VALUE)
                result = newGroup(groupName, groupId, type, creatorClassName);

            if (result == null)
                throw new IllegalArgumentException("There is not any free ID for table '" + groupName + "'");
        }

        return result;
    }

    public Group createGroup(String groupName, int groupId, Group.Type type, Class<?> creatorClassName) {
        Group result = findGroupWithId(groupName,groupId);

        if (result != null) {
            checkType(result, type);
            return result;
        }

        synchronized (names) {
            result = findGroupWithId(groupName,groupId);

            if (result != null) {
                checkType(result, type);
                return result;
            }

            String registeredGroupName = ids.get(groupId);

            if (registeredGroupName != null)
                throw new IllegalArgumentException("Group Name='" + groupName + "' already registered with ID='" + groupId + "'");

            result = newGroup(groupName,groupId,type,creatorClassName);
        }

        return result;
    }

    public Group getGroup(String groupName) {
        Group result = names.get(groupName);

        if (result == null)
            throw new IllegalArgumentException("Group '" + groupName + "' was not registered");

        return result;
    }

    Group findGroupWithId(String groupName, int groupId) {
        Group result = names.get(groupName);

        if (result != null && result.getId() != groupId) {
            throw new IllegalArgumentException("Group " + result.toString() + " was defined with different ID='" + groupId + "'");
        }

        return result;
    }

    Group newGroup(String groupName, int groupId, Group.Type type, Class<?> creatorClassName) {
        Group result;
        names.put(groupName, result = new Group(groupName, groupId, type, creatorClassName.getName()));
        ids.put(groupId, groupName);
        LOG.debug("Group '{}' registered by caller '{}'", groupName, creatorClassName.getName());
        return result;
    }

    int getFreeId() {
        for (int ii = 0; ii < Integer.MAX_VALUE; ++ ii) {
            if (ids.get(ii) == null) {
                return ii;
            }
        }

        return Integer.MAX_VALUE;
    }

    void checkType(Group group, Group.Type type) {
        if (group.getType() != type) {
            throw new IllegalArgumentException("Type of group " +  group.toString() + " does not match with " + type.name());
        }
    }
}
