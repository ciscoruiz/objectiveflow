/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.objectiveflow.api;

import java.math.BigInteger;
import java.util.List;

public interface  Flow {
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

    public String getName();

    public Table getTable();

    public String getId();

    public Flow setId(String id);

    public int getPriority();

    public Flow setPriority(int priority);

    public int getIdleTimeout();

    public Flow setIdleTimeout(int idleTimeout);

    public int getHardTimeout();

    public Flow setHardTimeout(int hardTimeout);

    public BigInteger getCookie();

    public Flow setCookie(BigInteger cookie);

    public Flow addMatch(Match match);

    public Flow addInstruction(Instruction instruction);

    public Flow addCounter(Counter counter);

    public void turnOn(Flag flag);

    public void turnOff(Flag flag);

    public boolean getFlag(Flag flag);

    public List<Match> getMatches();

    public List<Instruction> getInstructions();

    public List<Counter> getCounters();

    public org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.Flow build();
}


