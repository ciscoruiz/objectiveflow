/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.objectiveflow.api;

public interface Bucket {
    int getWeight();
    Bucket setWeight(int value);
    int getWatchPort();
    Bucket setWatchPort(int value);
    int getWatchGroup();
    Bucket setWatchGroup(int value);
    Bucket append(Action value);
    org.opendaylight.yang.gen.v1.urn.opendaylight.group.types.rev131018.group.buckets.Bucket build(int key);
}
