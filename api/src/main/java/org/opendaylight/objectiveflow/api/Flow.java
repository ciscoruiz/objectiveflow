/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.objectiveflow.api;

import java.util.BitSet;
import java.util.ArrayList;
import java.math.BigInteger;
import java.util.List;

public abstract class Flow {
    private String name;
    private String id;
    private Table table;
    private int priority;
    private int idleTimeout;
    private int hardTimeout;
    private BigInteger cookie;
    private ArrayList<Match> matches;
    private ArrayList<Instruction> instructions;
    private ArrayList<Counter> counters;
    private BitSet flags;

    public enum Flag {
        IsStrict(0),
        IsBarrier(1),
        InstallHardware(2),
        Resync(3),
        CheckOverlap(4),
        NoBytsCounts(5),
        NopktCounts(6),
        ResetCounts(7),
        SendFlowRem(8);

        int nbit;

        Flag(int nbit) {
            this.nbit = nbit;
        }

        public int getNbit() {
            return nbit;
        }
    }

    public Flow(String name, Table table) {
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

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Flow setId(String id) {
        this.id = id;
        return this;
    }

    public Table getTable() {
        return table;
    }

    public int getPriority() {
        return priority;
    }

    public Flow setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public int getIdleTimeout() {
        return idleTimeout;
    }

    public Flow setIdleTimeout(int idleTimeout) {
        this.idleTimeout = idleTimeout;
        return this;
    }

    public int getHardTimeout() {
        return hardTimeout;
    }

    public Flow setHardTimeout(int hardTimeout) {
        this.hardTimeout = hardTimeout;
        return this;
    }

    public BigInteger getCookie() {
        return cookie;
    }

    public Flow setCookie(BigInteger cookie) {
        this.cookie = cookie;
        return this;
    }

    public Flow addMatch(Match match) {
        matches.add(match);
        return this;
    }

    public Flow addInstruction(Instruction instruction) {
        instructions.add(instruction);
        return this;
    }

    public Flow addCounter(Counter counter) {
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

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public List<Counter> getCounters() {
        return counters;
    }

    public abstract org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.Flow build();
}


