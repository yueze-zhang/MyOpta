package org.optaplanner.examples.cloudmy.domain;

import org.optaplanner.examples.common.domain.AbstractPersistable;
import org.optaplanner.examples.common.swingui.components.Labeled;

import java.io.Serializable;

/**
 * @program: optaplanner-examples
 * @description:
 * @author: Zhang
 * @create: 2019-09-27 12:50
 * 该类为问题事实
 */
public class Computer extends AbstractPersistable implements Labeled {

    private int cpuPower; // in gigahertz
    private int memory; // in gigabyte RAM
    private int networkBandwidth; // in gigabyte per hour
    private int cost; // in euro per month

    public int getCpuPower() {
        return cpuPower;
    }
    public void setCpuPower(int cpuPower) {
        this.cpuPower = cpuPower;
    }
    public int getMemory() {
        return memory;
    }
    public void setMemory(int memory) {
        this.memory = memory;
    }
    public int getNetworkBandwidth() {
        return networkBandwidth;
    }
    public void setNetworkBandwidth(int networkBandwidth) {
        this.networkBandwidth = networkBandwidth;
    }
    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }

    public Computer(long id, int cpuPower, int memory, int networkBandwidth, int cost) {
        super(id);
        this.cpuPower = cpuPower;
        this.memory = memory;
        this.networkBandwidth = networkBandwidth;
        this.cost = cost;
    }
    public Computer(){}

    @Override
    public String getLabel() {
        return "Computer " + id;
    }


}
