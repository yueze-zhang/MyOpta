package org.optaplanner.examples.cloudmy.domain;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.examples.common.domain.AbstractPersistable;

import java.util.List;

/**
 * @program: optaplanner-examples
 * @description:
 * @author: Zhang
 * @create: 2019-09-27 14:08
 */
@PlanningSolution
public class Balance extends AbstractPersistable {



    private List<Computer> computerList;
    private List<Process> processList;

    private HardSoftScore score;

    public void setComputerList(List<Computer> computerList) {
        this.computerList = computerList;
    }
    public void setProcessList(List<Process> processList) {
        this.processList = processList;
    }

    @ValueRangeProvider(id = "computerRange")
    @ProblemFactCollectionProperty
    public List<Computer> getComputerList() {
        return computerList;
    }

    @PlanningEntityCollectionProperty
    public List<Process> getProcessList() {
        return processList;
    }

    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }
    public void setScore(HardSoftScore score) {
        this.score = score;
    }

}
