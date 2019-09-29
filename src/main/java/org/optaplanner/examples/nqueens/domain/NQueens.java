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

package org.optaplanner.examples.nqueens.domain;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.simple.SimpleScore;
import org.optaplanner.examples.common.domain.AbstractPersistable;
import org.optaplanner.persistence.xstream.api.score.buildin.simple.SimpleScoreXStreamConverter;

@PlanningSolution
@XStreamAlias("NQueens")
public class NQueens extends AbstractPersistable {

    private int n;

    private List<Column> columnList;
    private List<Row> rowList;

    private List<Queen> queenList;

    @XStreamConverter(SimpleScoreXStreamConverter.class)
    private SimpleScore score;

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    //对于约束流和Drools分数计算，规划师需要从解决方案实例中提取问题事实实例。
    // 它通过调用用注释的每个方法（或字段）来获取这些集合@ProblemFactCollectionProperty。
    // 这些方法返回的所有对象都将插入ConstrainStreams或Drools会话中，因此约束流或评分规则可以访问它们。
    // 例如，在NQueens所有Column和Row实例是问题的事实(problem facts)。
    @ProblemFactCollectionProperty
    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    @ValueRangeProvider(id = "rowRange")
    @ProblemFactCollectionProperty
    public List<Row> getRowList() {
        return rowList;
    }

    public void setRowList(List<Row> rowList) {
        this.rowList = rowList;
    }

    //计划者需要从解决方案实例中提取实体实例。
    // 它通过调用每个带注释的getter（或字段）来获取这些集合@PlanningEntityCollectionProperty：
    @PlanningEntityCollectionProperty
    public List<Queen> getQueenList() {
        return queenList;
    }

    public void setQueenList(List<Queen> queenList) {
        this.queenList = queenList;
    }

    @PlanningScore
    public SimpleScore getScore() {
        return score;
    }

    public void setScore(SimpleScore score) {
        this.score = score;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

}
