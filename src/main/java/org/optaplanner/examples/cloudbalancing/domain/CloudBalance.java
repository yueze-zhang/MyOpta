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

package org.optaplanner.examples.cloudbalancing.domain;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.examples.common.domain.AbstractPersistable;
import org.optaplanner.persistence.xstream.api.score.buildin.hardsoft.HardSoftScoreXStreamConverter;
//规划人员需要知道可以选择哪些值来分配给该属性computer。
// 从etComputerList()规划解决方案中的方法检索这些值，该方法返回当前数据集中所有计算机的列表。
/*
@PlanningSolution 注解：
- 它包含所有计算机的列表和所有进程的列表。
- 它既代表了规划问题，又代表了（如果它已初始化）规划解决方案。
- 要保存解决方案，Planner会初始化该类的新实例。

- 该processList属性包含进程列表。规划人员可以更改流程，将其分配给不同的计算机。
  因此，流程是计划实体，流程列表是计划实体的集合。我们注释getProcessList()用@PlanningEntityCollectionProperty。规划实体收集属性
- 该computerList拥有一系列计算机。规划人员无法更改计算机。
  因此，计算机是一个问题。特别是对于Drools的分数计算，属性computerList需要注释，@ProblemFactCollectionProperty以便Planner可以检索计算机列表（问题事实）并使其可用于Drools引擎。
- CloudBalance类也有一个@PlanningScore带注释属性score，
  其是Score在其当前状态的解决方案。计划程序在计算Score解决方案实例时会自动更新它。因此，此属性需要一个setter。
*/

@PlanningSolution
@XStreamAlias("CloudBalance")
public class CloudBalance extends AbstractPersistable {

    private List<CloudComputer> computerList;
    private List<CloudProcess> processList;

    @XStreamConverter(HardSoftScoreXStreamConverter.class)

    private HardSoftScore score;

    public CloudBalance() {
    }

    public CloudBalance(long id, List<CloudComputer> computerList, List<CloudProcess> processList) {
        super(id);
        this.computerList = computerList;
        this.processList = processList;
    }

    @ValueRangeProvider(id = "computerRange")
    @ProblemFactCollectionProperty
    public List<CloudComputer> getComputerList() {
        return computerList;
    }

    public void setComputerList(List<CloudComputer> computerList) {
        this.computerList = computerList;
    }

    @PlanningEntityCollectionProperty
    public List<CloudProcess> getProcessList() {
        return processList;
    }

    public void setProcessList(List<CloudProcess> processList) {
        this.processList = processList;
    }

    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

}
