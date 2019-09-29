package org.optaplanner.examples.mynqueen.domain;

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

import java.util.List;

/**
 * @program: optaplanner-examples
 * @description:
 * @author: Zhang
 * @create: 2019-09-24 16:16
 * 计划问题实际上是未解决的计划解决方案，或者-换而言之-尚未初始化的解决方案。
 * 例如，在n个皇后区中，NQueens该类具有@PlanningSolution批注，
 * 但尚未Queen解决的NQueens类中的每个注释都尚未分配给Row（其row属性为null）。
 * 那不是可行的解决方案。这甚至不是一个可行的解决方案。这是一个未初始化的解决方案。
 */
@PlanningSolution
@XStreamAlias("NQueens")
public class NQueens extends AbstractPersistable {
    // Problem facts
    private int n;
    public int getN() {
        return n;
    }
    public void setN(int n) {
        this.n = n;
    }

    private List<Column> columnList;
    private List<Row> rowList;

    // Planning entities
    private List<Queen> queenList;
    public void setQueenList(List<Queen> queenList) {
        this.queenList = queenList;
    }

    @XStreamConverter(SimpleScoreXStreamConverter.class)
    private SimpleScore score;
    @PlanningScore
    public SimpleScore getScore() {
        return score;
    }
    public void setScore(SimpleScore score) {
        this.score = score;
    }

    //计划者需要从解决方案实例中提取实体实例。
    // 它通过调用每个带注释的getter（或字段）来获取这些集合@PlanningEntityCollectionProperty：
    @PlanningEntityCollectionProperty
    public List<Queen> getQueenList() {
        return queenList;
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

    @ValueRangeProvider(id = "rowsbRange")
    @ProblemFactCollectionProperty
    public List<Row> getRowList() {
        return rowList;
    }
    public void setRowList(List<Row> rowList) {
        this.rowList = rowList;
    }
}
