/*
 * Copyright 2011 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.examples.machinereassignment.solver.drools;

import java.io.Serializable;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.optaplanner.core.api.score.constraint.ConstraintMatch;
import org.optaplanner.examples.machinereassignment.domain.MrMachine;
import org.optaplanner.examples.machinereassignment.domain.MrMachineCapacity;
import org.optaplanner.examples.machinereassignment.domain.MrResource;

public class MrMachineTransientUsage implements Serializable, Comparable<MrMachineTransientUsage> {

    private MrMachineCapacity machineCapacity;
    private long usage;

    public MrMachineTransientUsage(MrMachineCapacity machineCapacity, long usage) {
        this.machineCapacity = machineCapacity;
        this.usage = usage;
    }

    public MrMachineCapacity getMachineCapacity() {
        return machineCapacity;
    }

    public long getUsage() {
        return usage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof MrMachineTransientUsage) {
            MrMachineTransientUsage other = (MrMachineTransientUsage) o;
            return new EqualsBuilder()
                    .append(machineCapacity, other.machineCapacity)
                    .append(usage, other.usage)
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(machineCapacity)
                .append(usage)
                .toHashCode();
    }

    /**
     * Used by the GUI to sort the {@link ConstraintMatch} list
     * by {@link ConstraintMatch#getJustificationList()}.
     * @param other never null
     * @return comparison
     */
    @Override
    public int compareTo(MrMachineTransientUsage other) {
        return new CompareToBuilder()
                .append(machineCapacity, other.machineCapacity)
                .append(usage, other.usage)
                .toComparison();
    }

    public MrMachine getMachine() {
        return machineCapacity.getMachine();
    }

    public MrResource getResource() {
        return machineCapacity.getResource();
    }

    @Override
    public String toString() {
        return getMachine() + "-" + getResource() + "=" + usage;
    }

}
