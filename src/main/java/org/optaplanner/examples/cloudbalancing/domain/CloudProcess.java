/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
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

package org.optaplanner.examples.cloudbalancing.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.examples.cloudbalancing.optional.domain.CloudComputerStrengthComparator;
import org.optaplanner.examples.cloudbalancing.optional.domain.CloudProcessDifficultyComparator;
import org.optaplanner.examples.common.domain.AbstractPersistable;

//Planning entity: Planner在解决过程中可以改变的类。
//在这个例子中，Planning entity是Process类，因为Planner可以将Process分配给计算机
//我们需要告诉Planner它可以更改computer类中的属性，做如下操作
// 使用@PlanningEntity注释类. .
// 使用@PlanningVariable注释类

@PlanningEntity(difficultyComparatorClass = CloudProcessDifficultyComparator.class)
@XStreamAlias("CloudProcess")
public class CloudProcess extends AbstractPersistable {

    private int requiredCpuPower; // in gigahertz 千兆赫
    private int requiredMemory; // in gigabyte RAM
    private int requiredNetworkBandwidth; // in gigabyte per hour 带宽

    // Planning variables: changes during planning, between score calculations.
    //Planning variables: 计划期间，计分计算之间的变化。
    private CloudComputer computer;

    public CloudProcess() {
    }

    public CloudProcess(long id, int requiredCpuPower, int requiredMemory, int requiredNetworkBandwidth) {
        super(id);
        this.requiredCpuPower = requiredCpuPower;
        this.requiredMemory = requiredMemory;
        this.requiredNetworkBandwidth = requiredNetworkBandwidth;
    }

    public int getRequiredCpuPower() {
        return requiredCpuPower;
    }

    public void setRequiredCpuPower(int requiredCpuPower) {
        this.requiredCpuPower = requiredCpuPower;
    }

    public int getRequiredMemory() {
        return requiredMemory;
    }

    public void setRequiredMemory(int requiredMemory) {
        this.requiredMemory = requiredMemory;
    }

    public int getRequiredNetworkBandwidth() {
        return requiredNetworkBandwidth;
    }

    public void setRequiredNetworkBandwidth(int requiredNetworkBandwidth) {
        this.requiredNetworkBandwidth = requiredNetworkBandwidth;
    }

    //The property (or properties) of a planning entity class that changes during solving.
    // In this example, it is the property computer on the class Process.
    //在求解过程中更改的计划实体类的一个或多个属性。
    //在此示例中，它是Process类上的属性计算机。
    @PlanningVariable(valueRangeProviderRefs = {"computerRange"},
            strengthComparatorClass = CloudComputerStrengthComparator.class)
    public CloudComputer getComputer() {
        return computer;
    }
    //Of course, the property computer needs a setter too, so Planner can change it during solving.
    //当然，属性计算机也需要设置器，因此Planner可以在求解过程中对其进行更改。
    public void setComputer(CloudComputer computer) {
        this.computer = computer;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public int getRequiredMultiplicand() {
        return requiredCpuPower * requiredMemory * requiredNetworkBandwidth;
    }
    public String getLabel() {
        return "Process " + id;
    }

}
