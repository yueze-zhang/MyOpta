package org.optaplanner.examples.cloudmy.persistence;

import org.optaplanner.examples.cloudmy.domain.Balance;
import org.optaplanner.examples.cloudmy.domain.Computer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @program: optaplanner-examples
 * @description: 初始化生成器
 * @author: Zhang
 * @create: 2019-09-27 14:37
 */
public class Generator {
    private static class Price {

        private int hardwareValue;
        private String description;
        private int cost;

        public int getHardwareValue() {
            return hardwareValue;
        }

        public String getDescription() {
            return description;
        }

        public int getCost() {
            return cost;
        }

        private Price(int hardwareValue, String description, int cost) {
            this.hardwareValue = hardwareValue;
            this.description = description;
            this.cost = cost;
        }
    }
    //变量类型 [] 数组名 = new 变量类型[]{变量1,变量2,...}; Final表示不可修改
    private static final Price[] CPU_POWER_PRICES = { // in gigahertz
            new Price(3, "single core 3ghz", 110),
            new Price(4, "dual core 2ghz", 140),
            new Price(6, "dual core 3ghz", 180),
            new Price(8, "quad core 2ghz", 270),
            new Price(12, "quad core 3ghz", 400),
            new Price(16, "quad core 4ghz", 1000),
            new Price(24, "eight core 3ghz", 3000),
    };
    private static final Price[] MEMORY_PRICES = { // in gigabyte RAM
            new Price(2, "2 gigabyte", 140),
            new Price(4, "4 gigabyte", 180),
            new Price(8, "8 gigabyte", 220),
            new Price(16, "16 gigabyte", 300),
            new Price(32, "32 gigabyte", 400),
            new Price(64, "64 gigabyte", 600),
            new Price(96, "96 gigabyte", 1000),
    };
    private static final Price[] NETWORK_BANDWIDTH_PRICES = { // in gigabyte per hour
            new Price(2, "2 gigabyte", 100),
            new Price(4, "4 gigabyte", 200),
            new Price(6, "6 gigabyte", 300),
            new Price(8, "8 gigabyte", 400),
            new Price(12, "12 gigabyte", 600),
            new Price(16, "16 gigabyte", 800),
            new Price(20, "20 gigabyte", 1000),
    };
    private static final int MAXIMUM_REQUIRED_CPU_POWER = 12; // in gigahertz
    private static final int MAXIMUM_REQUIRED_MEMORY = 32; // in gigabyte RAM
    private static final int MAXIMUM_REQUIRED_NETWORK_BANDWIDTH = 12; // in gigabyte per hour
    protected Random random;

    public Balance createCloudBalance(int computerListSize, int processListSize){
        random = new Random(47);
        Balance cloudBalance = new Balance();
        cloudBalance.setId(0L);
        //创建电脑表
        createComputerList(cloudBalance, computerListSize);
        //创建进程表
        createProcessList(cloudBalance, processListSize);
        //确保计算机的总处理能力至少达到所需的总处理能力
        //assureComputerCapacityTotalAtLeastProcessRequiredTotal(cloudBalance);
        //valueOf() 方法用于返回给定参数的原生 Number 对象值，参数可以是原生数据类型, String等。
        //pow() 方法用于返回第一个参数的第二个参数次方。
        //创建 可能的解决方案大小
        BigInteger possibleSolutionSize = BigInteger.valueOf(cloudBalance.getComputerList().size()).pow(
                cloudBalance.getProcessList().size());
        return cloudBalance;
    }

    //创建电脑表
    private void createComputerList(Balance cloudBalance, int computerListSize) {
        List<Computer> computerList = new ArrayList<>(computerListSize);
        for (int i = 0; i < computerListSize; i++) {
            Computer computer = generateComputerWithoutId();
            computer.setId((long) i);
            computerList.add(computer);
        }
        cloudBalance.setComputerList(computerList);
    }
    public Computer generateComputerWithoutId() {
        Computer computer = new Computer();
        //随机从CPU_POWER_PRICES数组中选出一个Price,并设置COMPUTER属性的CpuPower值
        int cpuPowerPricesIndex = random.nextInt(CPU_POWER_PRICES.length);
        computer.setCpuPower(CPU_POWER_PRICES[cpuPowerPricesIndex].getHardwareValue());
        int memoryPricesIndex = cpuPowerPricesIndex;  //不加入变形指数
        //int memoryPricesIndex = distortIndex(cpuPowerPricesIndex, MEMORY_PRICES.length);
        computer.setMemory(MEMORY_PRICES[memoryPricesIndex].getHardwareValue());
        int networkBandwidthPricesIndex = cpuPowerPricesIndex;  //不加入变形指数
        //int networkBandwidthPricesIndex = distortIndex(cpuPowerPricesIndex, NETWORK_BANDWIDTH_PRICES.length);
        computer.setNetworkBandwidth(NETWORK_BANDWIDTH_PRICES[networkBandwidthPricesIndex].getHardwareValue());
        int cost = CPU_POWER_PRICES[cpuPowerPricesIndex].getCost()
                + MEMORY_PRICES[memoryPricesIndex].getCost()
                + NETWORK_BANDWIDTH_PRICES[networkBandwidthPricesIndex].getCost();
        computer.setCost(cost);
        return computer;
    }

    private void createProcessList(Balance cloudBalance, int processListSize) {
        List<org.optaplanner.examples.cloudmy.domain.Process> processList = new ArrayList<>(processListSize);
        for (int i = 0; i < processListSize; i++) {
            org.optaplanner.examples.cloudmy.domain.Process process = generateProcessWithoutId();
            process.setId((long) i);
            processList.add(process);
        }
        cloudBalance.setProcessList(processList);
    }

    public org.optaplanner.examples.cloudmy.domain.Process generateProcessWithoutId() {
        org.optaplanner.examples.cloudmy.domain.Process process = new org.optaplanner.examples.cloudmy.domain.Process();
        int requiredCpuPower = generateRandom(MAXIMUM_REQUIRED_CPU_POWER);
        process.setRequiredCpuPower(requiredCpuPower);
        int requiredMemory = generateRandom(MAXIMUM_REQUIRED_MEMORY);
        process.setRequiredMemory(requiredMemory);
        int requiredNetworkBandwidth = generateRandom(MAXIMUM_REQUIRED_NETWORK_BANDWIDTH);
        process.setRequiredNetworkBandwidth(requiredNetworkBandwidth);
        return process;
    }
    //随机数生成器
    private int generateRandom(int maximumValue) {
        double randomDouble = random.nextDouble();
        double parabolaBase = 2000.0;
        double parabolaRandomDouble = (Math.pow(parabolaBase, randomDouble) - 1.0) / (parabolaBase - 1.0);
        if (parabolaRandomDouble < 0.0 || parabolaRandomDouble >= 1.0) {
            throw new IllegalArgumentException("Invalid generated parabolaRandomDouble (" + parabolaRandomDouble + ")");
        }
        int value = ((int) Math.floor(parabolaRandomDouble * ((double) maximumValue))) + 1;
        if (value < 1 || value > maximumValue) {
            throw new IllegalArgumentException("Invalid generated value (" + value + ")");
        }
        return value;
    }

}
