/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *  
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.objectiveflow.impl;

import org.opendaylight.objectiveflow.api.Bucket;
import org.opendaylight.yang.gen.v1.urn.opendaylight.group.types.rev131018.GroupId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.group.types.rev131018.GroupTypes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.group.types.rev131018.group.BucketsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.group.types.rev131018.groups.GroupBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.group.types.rev131018.groups.GroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Group implements org.opendaylight.objectiveflow.api.Group {
    private static final Logger LOG = LoggerFactory.getLogger(Group.class);

    private String name;
    private int id;
    private org.opendaylight.objectiveflow.api.Group.Type type;
    private String creatorClassName;
    ArrayList<Bucket> buckets;

    Group(String name, int id, Type type, String creatorClassName) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.creatorClassName = creatorClassName;
        buckets = new ArrayList<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public org.opendaylight.objectiveflow.api.Group append(Bucket bucket) {
        buckets.add(bucket);
        return this;
    }

    @Override
    public org.opendaylight.yang.gen.v1.urn.opendaylight.group.types.rev131018.groups.Group build() {
        GroupBuilder builder = new GroupBuilder();
        builder.setKey(new GroupKey(new GroupId((long) id))).setGroupType(GroupTypes.forValue(type.getValue())).setGroupName(name);

        LOG.debug("Group=" + toString());

        List<org.opendaylight.yang.gen.v1.urn.opendaylight.group.types.rev131018.group.buckets.Bucket> ofBuckets = new ArrayList<>();
        int key = 0;
        for (Bucket bucket : buckets) {
            LOG.debug("Bucket={}", bucket);
            ofBuckets.add(bucket.build(key ++));
        }
        builder.setBuckets(new BucketsBuilder().setBucket(ofBuckets).build());

        return builder.build();
    }

    public String getCreatorClassName() {return creatorClassName;}

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Group{");
        sb.append("name='").append(name).append('\'');
        sb.append(", id=").append(id);
        sb.append(", creatorClassName='").append(creatorClassName).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
