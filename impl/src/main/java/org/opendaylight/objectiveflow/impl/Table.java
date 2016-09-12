/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.objectiveflow.impl;

public class Table implements org.opendaylight.objectiveflow.api.Table {
    private String name;
    private short id;
    private String creatorClassName;

    Table(String name, short id, String creatorClassName) {
        this.name = name;
        this.id = id;
        this.creatorClassName = creatorClassName;
    }

    public short getId() {
        return id;
    }

    public String getCreatorClassName() {return creatorClassName;}

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Table{");
        sb.append("name='").append(name).append('\'');
        sb.append(", id=").append(id);
        sb.append(", creatorClassName='").append(creatorClassName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}