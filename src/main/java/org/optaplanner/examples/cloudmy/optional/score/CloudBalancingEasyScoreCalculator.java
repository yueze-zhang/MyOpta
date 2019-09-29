/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package org.optaplanner.examples.cloudmy.optional.score;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;
import org.optaplanner.examples.cloudmy.domain.Balance;
import org.optaplanner.examples.cloudmy.domain.Computer;
import org.optaplanner.examples.cloudmy.domain.Process;

public class CloudBalancingEasyScoreCalculator implements EasyScoreCalculator<Balance> {

    /**
     * A very simple implementation. The double loop can easily be removed by using Maps as shown in
     * 一个非常简单的实现。使用Maps可以很容易地删除双循环，如图所示
     *
     */

    @Override
    public HardSoftScore calculateScore(Balance cloudBalance) {
        int hardScore = 0;
        int softScore = 0;
        //从可用计算机列表中选择一个计算机
        for (Computer computer : cloudBalance.getComputerList()) {
            int cpuPowerUsage = 0;
            int memoryUsage = 0;
            int networkBandwidthUsage = 0;
            boolean used = false;

            //从进程列表中选择一个进程
            for (Process process : cloudBalance.getProcessList()) {
                if (computer.equals(process.getComputer())) {
                    //如果当前选定的计算机有分配的任务，则进行以下内容
                    cpuPowerUsage += process.getRequiredCpuPower();
                    memoryUsage += process.getRequiredMemory();
                    networkBandwidthUsage += process.getRequiredNetworkBandwidth();
                    used = true;
                }
            }

            // Hard constraints 硬约束
            int cpuPowerAvailable = computer.getCpuPower() - cpuPowerUsage; //CPUPower可用资源 = 总共的资源 - 使用中的资源
            if (cpuPowerAvailable < 0) {
                hardScore += cpuPowerAvailable; //硬错误增加
            }
            //同上检测内存可用
            int memoryAvailable = computer.getMemory() - memoryUsage;
            if (memoryAvailable < 0) {
                hardScore += memoryAvailable;
            }
            //同上检测带宽可用
            int networkBandwidthAvailable = computer.getNetworkBandwidth() - networkBandwidthUsage;
            if (networkBandwidthAvailable < 0) {
                hardScore += networkBandwidthAvailable;
            }

            // Soft constraints
            if (used) {
                softScore -= computer.getCost();
            }
        }
        return HardSoftScore.valueOf(hardScore, softScore);
    }

}
