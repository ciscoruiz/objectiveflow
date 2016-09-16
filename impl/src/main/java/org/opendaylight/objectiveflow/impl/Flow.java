/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl;

import org.opendaylight.objectiveflow.api.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.FlowCookie;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.FlowModFlags;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.Instruction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.InstructionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Flow extends org.opendaylight.objectiveflow.api.Flow {
    private static final Logger LOG = LoggerFactory.getLogger(Flow.class);

    public Flow(String name, org.opendaylight.objectiveflow.api.Table table) {
        super(name, table);
    }

    @Override
    public org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.Flow build() {
        final FlowBuilder builder = new FlowBuilder();

        checkArgument("id", getId());
        checkArgument("table", getTable());

        builder.setFlowName(getName());
        builder.setKey(new FlowKey(new FlowId(getId())));
        builder.setTableId(getTable().getId());
        builder.setPriority(getPriority());
        builder.setIdleTimeout(getIdleTimeout());
        builder.setHardTimeout(getHardTimeout());
        builder.setCookie(new FlowCookie(getCookie()));

        LOG.debug("Flow=" + toString());
        setMatches(builder);
        setInstructions(builder);
        setFlags(builder);

        return builder.build();
    }

    private void checkArgument(String whatIs, Object object) {
        if (object == null) {
            final StringBuffer sb = new StringBuffer("Flow=").append(getName()).append(" member=").append(whatIs).append(" can not be null");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private void setMatches(FlowBuilder builder) {
        final MatchBuilder matchBuilder = new MatchBuilder();
        for (Match match : getMatches()) {
            LOG.debug("Match=" + match.toString());
            match.setup(matchBuilder);
        }
        builder.setMatch(matchBuilder.build());
    }

    private void setInstructions(FlowBuilder builder) {
        final InstructionsBuilder instructionsBuilder = new InstructionsBuilder();
        ArrayList<Instruction> odlInstructions = new ArrayList<>();
        int key = 0;
        for (org.opendaylight.objectiveflow.api.Instruction instruction : getInstructions()) {
            LOG.debug("Instruction=" + instruction.toString());
            final Instruction odlInstruction = instruction.build(key++);
            odlInstructions.add(odlInstruction);
        }
        builder.setInstructions(instructionsBuilder.setInstruction(odlInstructions).build());
    }

    private void setFlags(FlowBuilder builder) {
        builder.setInstallHw(getFlag(Flag.InstallHardware));
        builder.setStrict(getFlag(Flag.IsStrict));
        builder.setBarrier(getFlag(Flag.IsBarrier));
        final FlowModFlags modFlags = new FlowModFlags(getFlag(Flag.CheckOverlap), getFlag(Flag.NoBytsCounts), getFlag(Flag.NopktCounts), getFlag(Flag.ResetCounts), getFlag(Flag.SendFlowRem));
        builder.setFlags(modFlags);
    }
}

