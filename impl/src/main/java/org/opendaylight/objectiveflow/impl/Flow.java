/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl;

import org.opendaylight.objectiveflow.api.*;
import org.opendaylight.objectiveflow.api.Table;
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Flow implements org.opendaylight.objectiveflow.api.Flow {
    private static final Logger LOG = LoggerFactory.getLogger(Flow.class);

    private String name;
    private String id;
    private Table table;
    private int priority;
    private int idleTimeout;
    private int hardTimeout;
    private BigInteger cookie;
    private ArrayList<Match> matches;
    private ArrayList<org.opendaylight.objectiveflow.api.Instruction> instructions;
    private ArrayList<Counter> counters;
    private BitSet flags;

    public Flow(String name, org.opendaylight.objectiveflow.api.Table table) {
        this.name = name;
        this.table = table;
        this.cookie = BigInteger.valueOf(0);
        this.matches = new ArrayList<>();
        this.instructions = new ArrayList<>();
        this.counters = new ArrayList<>();
        this.flags = new BitSet();
        turnOff(Flag.IsBarrier);
        turnOn(Flag.InstallHardware);
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

    public String getName() {
        return name;
    }

    public Table getTable() {
        return table;
    }

    public String getId() {
        return id;
    }

    public org.opendaylight.objectiveflow.api.Flow setId(String id) {
        this.id = id;
        return this;
    }

    public int getPriority() {
        return priority;
    }

    public org.opendaylight.objectiveflow.api.Flow setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public int getIdleTimeout() {
        return idleTimeout;
    }

    public org.opendaylight.objectiveflow.api.Flow setIdleTimeout(int idleTimeout) {
        this.idleTimeout = idleTimeout;
        return this;
    }

    public int getHardTimeout() {
        return hardTimeout;
    }

    public org.opendaylight.objectiveflow.api.Flow setHardTimeout(int hardTimeout) {
        this.hardTimeout = hardTimeout;
        return this;
    }

    public BigInteger getCookie() {
        return cookie;
    }

    public org.opendaylight.objectiveflow.api.Flow setCookie(BigInteger cookie) {
        this.cookie = cookie;
        return this;
    }

    public org.opendaylight.objectiveflow.api.Flow addMatch(Match match) {
        matches.add(match);
        return this;
    }

    public org.opendaylight.objectiveflow.api.Flow addInstruction(org.opendaylight.objectiveflow.api.Instruction instruction) {
        instructions.add(instruction);
        return this;
    }

    public org.opendaylight.objectiveflow.api.Flow addCounter(Counter counter) {
        counters.add(counter);
        return this;
    }

    public void turnOn(Flag flag) {
        flags.set(flag.getNbit(), true);
    }

    public void turnOff(Flag flag) {
        flags.set(flag.getNbit(), false);
    }

    public boolean getFlag(Flag flag) {
        return flags.get(flag.getNbit());
    }

    public List<Match> getMatches() {
        return matches;
    }

    public List<org.opendaylight.objectiveflow.api.Instruction> getInstructions() {
        return instructions;
    }

    public List<Counter> getCounters() {
        return counters;
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
            LOG.debug("Match={}", match);
            match.setup(matchBuilder);
        }
        builder.setMatch(matchBuilder.build());
    }

    private void setInstructions(FlowBuilder builder) {
        final InstructionsBuilder instructionsBuilder = new InstructionsBuilder();
        ArrayList<Instruction> odlInstructions = new ArrayList<>();
        int key = 0;
        for (org.opendaylight.objectiveflow.api.Instruction instruction : getInstructions()) {
            LOG.debug("Instruction={}", instruction);
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Flow{");
        sb.append("name='").append(name).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", table=").append(table);
        sb.append(", priority=").append(priority);
        sb.append(", idleTimeout=").append(idleTimeout);
        sb.append(", hardTimeout=").append(hardTimeout);
        sb.append(", cookie=").append(cookie);
        sb.append(", matches=").append(matches);
        sb.append(", instructions=").append(instructions);
        sb.append(", counters=").append(counters);
        sb.append(", flags=").append(flags);
        sb.append('}');
        return sb.toString();
    }
}

