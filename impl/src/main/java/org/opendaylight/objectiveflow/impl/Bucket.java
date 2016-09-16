/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl;

import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.group.types.rev131018.BucketId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.group.types.rev131018.group.buckets.BucketBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Bucket implements org.opendaylight.objectiveflow.api.Bucket {
    private static final Logger LOG = LoggerFactory.getLogger(Bucket.class);

    private int weight;
    private int watchPort;
    private int watchGroup;
    private List<org.opendaylight.objectiveflow.api.Action> actions;

    public Bucket() {
        actions = new ArrayList<>();
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public Bucket setWeight(int value) {
        weight = value;
        return this;
    }

    @Override
    public int getWatchPort() {
        return watchPort;
    }

    @Override
    public Bucket setWatchPort(int value) {
        watchPort = value;
        return this;
    }

    @Override
    public int getWatchGroup() {
        return watchGroup;
    }

    @Override
    public Bucket setWatchGroup(int value){
        watchGroup = value;
        return this;
    }

    @Override
    public Bucket append(org.opendaylight.objectiveflow.api.Action value) {
        actions.add(value);
        return this;
    }

    @Override
    public org.opendaylight.yang.gen.v1.urn.opendaylight.group.types.rev131018.group.buckets.Bucket build(int key) {
        BucketBuilder builder = new BucketBuilder();

        builder.setBucketId(new BucketId((long) key)).setWeight(weight).setWatchGroup((long) watchGroup).setWatchPort((long) watchPort);

        List<Action> ofActions = new ArrayList<>();
        int actionKey = 0;
        for (org.opendaylight.objectiveflow.api.Action action : actions) {
            LOG.debug("Action=" + action.toString());
            ofActions.add(action.build(actionKey ++));
        }

        builder.setAction(ofActions);

        return builder.build();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Bucket{");
        sb.append("weight=").append(weight);
        sb.append(", watchPort=").append(watchPort);
        sb.append(", watchGroup=").append(watchGroup);
        sb.append(", #actions=").append(actions.size());
        sb.append('}');
        return sb.toString();
    }
}
