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

package org.optaplanner.examples.cloudbalancing.persistence;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.optaplanner.examples.cloudbalancing.app.CloudBalancingApp;
import org.optaplanner.examples.cloudbalancing.domain.CloudBalance;
import org.optaplanner.examples.cloudbalancing.domain.CloudComputer;
import org.optaplanner.examples.cloudbalancing.domain.CloudProcess;
import org.optaplanner.examples.common.app.CommonApp;
import org.optaplanner.examples.common.app.LoggingMain;
import org.optaplanner.examples.common.persistence.AbstractSolutionImporter;
import org.optaplanner.persistence.common.api.domain.solution.SolutionFileIO;
import org.optaplanner.persistence.xstream.impl.domain.solution.XStreamSolutionFileIO;

public class CloudBalancingGenerator extends LoggingMain {

    private static class Price {

        private int hardwareValue;
        private String description;
        private int cost;

        private Price(int hardwareValue, String description, int cost) {
            this.hardwareValue = hardwareValue;
            this.description = description;
            this.cost = cost;
        }

        public int getHardwareValue() {
            return hardwareValue;
        }

        public String getDescription() {
            return description;
        }

        public int getCost() {
            return cost;
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

    public static void main(String[] args) {
        CloudBalancingGenerator generator = new CloudBalancingGenerator();
        generator.writeCloudBalance(2, 6);
//        generator.writeCloudBalance(3, 9);
//        generator.writeCloudBalance(4, 12);
////        generator.writeCloudBalance(5, 15);
////        generator.writeCloudBalance(6, 18);
////        generator.writeCloudBalance(7, 21);
////        generator.writeCloudBalance(8, 24);
////        generator.writeCloudBalance(9, 27);
////        generator.writeCloudBalance(10, 30);
////        generator.writeCloudBalance(11, 33);
////        generator.writeCloudBalance(12, 36);
////        generator.writeCloudBalance(13, 39);
////        generator.writeCloudBalance(14, 42);
////        generator.writeCloudBalance(15, 45);
////        generator.writeCloudBalance(16, 48);
////        generator.writeCloudBalance(17, 51);
////        generator.writeCloudBalance(18, 54);
////        generator.writeCloudBalance(19, 57);
////        generator.writeCloudBalance(20, 60);
//        generator.writeCloudBalance(100, 300);
//        generator.writeCloudBalance(200, 600);
//        generator.writeCloudBalance(400, 1200);
//        generator.writeCloudBalance(800, 2400);
//        generator.writeCloudBalance(1600, 4800);
    }

    protected final SolutionFileIO<CloudBalance> solutionFileIO;
    protected final File outputDir;

    protected Random random;

    //构造函数
    public CloudBalancingGenerator() {
        solutionFileIO = new XStreamSolutionFileIO<>(CloudBalance.class);
        outputDir = new File(CommonApp.determineDataDir(CloudBalancingApp.DATA_DIR_NAME), "unsolved");
    }

    public CloudBalancingGenerator(boolean withoutDao) {
        if (!withoutDao) {
            throw new IllegalArgumentException("The parameter withoutDao (" + withoutDao + ") must be true.");
        }
        checkConfiguration();
        solutionFileIO = null;
        outputDir = null;
    }

    private void checkConfiguration() {
        if (CPU_POWER_PRICES.length != MEMORY_PRICES.length || CPU_POWER_PRICES.length != NETWORK_BANDWIDTH_PRICES.length) {
            throw new IllegalStateException("All price arrays must be equal in length.");
        }
    }

    private void writeCloudBalance(int computerListSize, int processListSize) {
        //和XML相关
        String fileName = determineFileName(computerListSize, processListSize);
        File outputFile = new File(outputDir, fileName + ".xml");
        //生成createCloudBalance案例
        CloudBalance cloudBalance = createCloudBalance(fileName, computerListSize, processListSize);
        solutionFileIO.write(cloudBalance, outputFile);
        logger.info("Saved: {}", outputFile);
    }

    //生成随机案例,如果不生成XML可以直接用此方法,
    public CloudBalance createCloudBalance(int computerListSize, int processListSize) {
        return createCloudBalance(determineFileName(computerListSize, processListSize),
                computerListSize, processListSize);
    }

    private String determineFileName(int computerListSize, int processListSize) {
        return computerListSize + "computers-" + processListSize + "processes";
    }

    //inputId 用于logger记录信息,computerListSize为电脑数量,processListSize 为进程数量
    public CloudBalance createCloudBalance(String inputId, int computerListSize, int processListSize) {
        //创建随机种子
        random = new Random(47);
        CloudBalance cloudBalance = new CloudBalance();
        cloudBalance.setId(0L);
        //创建电脑表
        createComputerList(cloudBalance, computerListSize);
        //创建进程表
        createProcessList(cloudBalance, processListSize);
        //确保计算机的总处理能力至少达到所需的总处理能力
        assureComputerCapacityTotalAtLeastProcessRequiredTotal(cloudBalance);
        //valueOf() 方法用于返回给定参数的原生 Number 对象值，参数可以是原生数据类型, String等。
        //pow() 方法用于返回第一个参数的第二个参数次方。
        //创建 可能的解决方案大小
        BigInteger possibleSolutionSize = BigInteger.valueOf(cloudBalance.getComputerList().size()).pow(
                cloudBalance.getProcessList().size());
        //生成log
        logger.info("CloudBalance {} has {} computers and {} processes with a search space of {}.",
                inputId, computerListSize, processListSize,
                AbstractSolutionImporter.getFlooredPossibleSolutionSize(possibleSolutionSize));
        return cloudBalance;
    }

    //创建电脑表
    private void createComputerList(CloudBalance cloudBalance, int computerListSize) {
        List<CloudComputer> computerList = new ArrayList<>(computerListSize);
        for (int i = 0; i < computerListSize; i++) {
            CloudComputer computer = generateComputerWithoutId();
            computer.setId((long) i);
            computerList.add(computer);
        }
        cloudBalance.setComputerList(computerList);
    }

    public CloudComputer generateComputerWithoutId() {
        CloudComputer computer = new CloudComputer();

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

        logger.trace("Created computer with cpuPowerPricesIndex ({}), memoryPricesIndex ({}),"
                + " networkBandwidthPricesIndex ({}).",
                cpuPowerPricesIndex, memoryPricesIndex, networkBandwidthPricesIndex);
        return computer;
    }

    //变形指数,把length的长度 映射到index上
    private int distortIndex(int referenceIndex, int length) {
        int index = referenceIndex;
        double randomDouble = random.nextDouble();
        double loweringThreshold = 0.25;
        while (randomDouble < loweringThreshold && index >= 1) {
            index--;
            loweringThreshold *= 0.10;
        }
        double heighteningThreshold = 0.75;
        while (randomDouble >= heighteningThreshold && index <= (length - 2)) {
            index++;
            heighteningThreshold = (1.0 - ((1.0 - heighteningThreshold) * 0.10));
        }
        return index;
    }

    private void createProcessList(CloudBalance cloudBalance, int processListSize) {
        List<CloudProcess> processList = new ArrayList<>(processListSize);
        for (int i = 0; i < processListSize; i++) {
            CloudProcess process = generateProcessWithoutId();
            process.setId((long) i);
            processList.add(process);
        }
        cloudBalance.setProcessList(processList);
    }

    public CloudProcess generateProcessWithoutId() {
        CloudProcess process = new CloudProcess();
        int requiredCpuPower = generateRandom(MAXIMUM_REQUIRED_CPU_POWER);
        process.setRequiredCpuPower(requiredCpuPower);
        int requiredMemory = generateRandom(MAXIMUM_REQUIRED_MEMORY);
        process.setRequiredMemory(requiredMemory);
        int requiredNetworkBandwidth = generateRandom(MAXIMUM_REQUIRED_NETWORK_BANDWIDTH);
        process.setRequiredNetworkBandwidth(requiredNetworkBandwidth);
        logger.trace("Created CloudProcess with requiredCpuPower ({}), requiredMemory ({}),"
                + " requiredNetworkBandwidth ({}).",
                requiredCpuPower, requiredMemory, requiredNetworkBandwidth);
        // Notice that we leave the PlanningVariable properties on null
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

    //确保计算机的总处理能力至少达到所需的总处理能力
    private void assureComputerCapacityTotalAtLeastProcessRequiredTotal(CloudBalance cloudBalance) {
        List<CloudComputer> computerList = cloudBalance.getComputerList();
        int cpuPowerTotal = 0;
        int memoryTotal = 0;
        int networkBandwidthTotal = 0;
        for (CloudComputer computer : computerList) {
            cpuPowerTotal += computer.getCpuPower();
            memoryTotal += computer.getMemory();
            networkBandwidthTotal += computer.getNetworkBandwidth();
        }
        int requiredCpuPowerTotal = 0;
        int requiredMemoryTotal = 0;
        int requiredNetworkBandwidthTotal = 0;
        for (CloudProcess process : cloudBalance.getProcessList()) {
            requiredCpuPowerTotal += process.getRequiredCpuPower();
            requiredMemoryTotal += process.getRequiredMemory();
            requiredNetworkBandwidthTotal += process.getRequiredNetworkBandwidth();
        }
        int cpuPowerLacking = requiredCpuPowerTotal - cpuPowerTotal;
        while (cpuPowerLacking > 0) {
            CloudComputer computer = computerList.get(random.nextInt(computerList.size()));
            int upgrade = determineUpgrade(cpuPowerLacking);
            computer.setCpuPower(computer.getCpuPower() + upgrade);
            cpuPowerLacking -= upgrade;
        }
        int memoryLacking = requiredMemoryTotal - memoryTotal;
        while (memoryLacking > 0) {
            CloudComputer computer = computerList.get(random.nextInt(computerList.size()));
            int upgrade = determineUpgrade(memoryLacking);
            computer.setMemory(computer.getMemory() + upgrade);
            memoryLacking -= upgrade;
        }
        int networkBandwidthLacking = requiredNetworkBandwidthTotal - networkBandwidthTotal;
        while (networkBandwidthLacking > 0) {
            CloudComputer computer = computerList.get(random.nextInt(computerList.size()));
            int upgrade = determineUpgrade(networkBandwidthLacking);
            computer.setNetworkBandwidth(computer.getNetworkBandwidth() + upgrade);
            networkBandwidthLacking -= upgrade;
        }
    }

    private int determineUpgrade(int lacking) {
        for (int upgrade : new int[] {8, 4, 2, 1}) {
            if (lacking >= upgrade) {
                return upgrade;
            }
        }
        throw new IllegalStateException("Lacking (" + lacking + ") should be at least 1.");
    }

}
