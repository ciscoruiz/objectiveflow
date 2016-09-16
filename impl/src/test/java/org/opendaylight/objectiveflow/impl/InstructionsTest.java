/*
 * Copyright © 2016 Ragnarok Team and others.  All rights reserved.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *   and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.objectiveflow.impl;

import org.junit.Test;
import org.opendaylight.objectiveflow.impl.action.Drop;
import org.opendaylight.objectiveflow.impl.action.PopPbb;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.write.actions._case.WriteActions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.Instruction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.apply.actions._case.ApplyActions;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class InstructionsTest {
    @Test
    public void testApplyActions() throws Exception {
        org.opendaylight.objectiveflow.api.Instruction instruction = new org.opendaylight.objectiveflow.impl.instruction.ApplyActions();
        instruction.append(new Drop());
        Instruction build = instruction.build(100);

        assertEquals(100, build.getKey().getOrder().intValue());
        ApplyActionsCase instructionCase = (ApplyActionsCase) build.getInstruction();
        ApplyActions actions = instructionCase.getApplyActions();
        assertEquals(1, actions.getAction().size());
    }

    @Test
    public void testClearActions() throws Exception {
        org.opendaylight.objectiveflow.api.Instruction instruction = new org.opendaylight.objectiveflow.impl.instruction.ClearActions();
        Instruction build = instruction.build(200);

        assertEquals(200, build.getKey().getOrder().intValue());
        ClearActionsCase instructionCase = (ClearActionsCase) build.getInstruction();
        assertNotNull(instructionCase);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testFailClearActions() throws Exception {
        org.opendaylight.objectiveflow.api.Instruction instruction = new org.opendaylight.objectiveflow.impl.instruction.ClearActions();
        instruction.append(new Drop());
    }

    @Test
    public void testGoto() throws Exception {
        Table table = new Directory().createTable("mytable", this.getClass());
        org.opendaylight.objectiveflow.api.Instruction instruction = new org.opendaylight.objectiveflow.impl.instruction.Goto(table);

        Instruction build = instruction.build(300);
        assertEquals(300, build.getKey().getOrder().intValue());

        GoToTableCase instructionCase = (GoToTableCase) build.getInstruction();
        assertNotNull(instructionCase);
        assertEquals(table.getId(), instructionCase.getGoToTable().getTableId().shortValue());
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void testFailGoto() throws Exception {
        Table table = new Directory().createTable("mytable", this.getClass());
        org.opendaylight.objectiveflow.api.Instruction instruction = new org.opendaylight.objectiveflow.impl.instruction.Goto(table);
        instruction.append(new PopPbb());
    }

    @Test
    public void testWriteActions() throws Exception {
        org.opendaylight.objectiveflow.api.Instruction instruction = new org.opendaylight.objectiveflow.impl.instruction.WriteActions();
        instruction.append(new Drop());
        Instruction build = instruction.build(500);

        assertEquals(500, build.getKey().getOrder().intValue());
        WriteActionsCase instructionCase = (WriteActionsCase) build.getInstruction();
        WriteActions actions = instructionCase.getWriteActions();
        assertEquals(1, actions.getAction().size());
    }

    @Test
    public void testWriteMetadata() throws Exception {
        org.opendaylight.objectiveflow.api.Instruction instruction = new org.opendaylight.objectiveflow.impl.instruction.WriteMetadata(BigInteger.ONE, BigInteger.TEN);
        Instruction build = instruction.build(2000);

        assertEquals(2000, build.getKey().getOrder().intValue());
        WriteMetadataCase instructionCase = (WriteMetadataCase) build.getInstruction();
        assertNotNull(instructionCase);
        assertEquals(BigInteger.ONE, instructionCase.getWriteMetadata().getMetadata());
        assertEquals(BigInteger.TEN, instructionCase.getWriteMetadata().getMetadataMask());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testFailWriteMetadata() throws Exception {
        org.opendaylight.objectiveflow.api.Instruction instruction = new org.opendaylight.objectiveflow.impl.instruction.WriteMetadata(BigInteger.ONE, BigInteger.TEN);
        instruction.append(null);
    }
}
