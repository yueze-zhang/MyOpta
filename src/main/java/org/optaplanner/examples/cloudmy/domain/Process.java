package org.optaplanner.examples.cloudmy.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.examples.common.domain.AbstractPersistable;

/**
 * @program: optaplanner-examples
 * @description:
 * @author: Zhang
 * @create: 2019-09-27 13:47
 */
@PlanningEntity
public class Process extends AbstractPersistable {

    private int requiredCpuPower; // in gigahertz 千兆赫
    private int requiredMemory; // in gigabyte RAM
    private int requiredNetworkBandwidth; // in gigabyte per hour 带宽

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

    @PlanningVariable(valueRangeProviderRefs = {"computerRange"})
    private Computer computer;
    public Computer getComputer() {
        return computer;
    }
    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    public Process(long id, int requiredCpuPower, int requiredMemory, int requiredNetworkBandwidth) {
        super(id);
        this.requiredCpuPower = requiredCpuPower;
        this.requiredMemory = requiredMemory;
        this.requiredNetworkBandwidth = requiredNetworkBandwidth;
    }
    public Process(){}

    public String getLabel() {
        return "Process " + id;
    }
}
