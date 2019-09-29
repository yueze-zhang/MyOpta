package org.optaplanner.examples.mynqueen.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.examples.common.domain.AbstractPersistable;

/**
 * @program: optaplanner-examples
 * @description:
 * @author: Zhang
 * @create: 2019-09-24 15:51
 */
@PlanningEntity
public class  Queen extends AbstractPersistable {
    private Column column;
    public Column getColumn() {
        return column;
    }
    public void setColumn(Column column) {
        this.column = column;
    }
    // Planning variables: changes during planning, between score calculations.
    //计划变量:在计划期间，在计算分数之间的变化
    private Row row;

    //计划变量是计划实体上的JavaBean属性（即getter和setter）,它指向计划值，该值在计划期间会更改
    //
    @PlanningVariable(valueRangeProviderRefs = {"rowsbRange"})
    public Row getRow() {
        return row;
    }
    public void setRow(Row row) {
        this.row = row;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public int getColumnIndex() {
        return column.getIndex();
    }

    public int getRowIndex() {
        if (row == null) {
            return Integer.MIN_VALUE;
        }
        return row.getIndex();
    }
    //得到升序对角索引
    public int getAscendingDiagonalIndex() {
        return (getColumnIndex() + getRowIndex());
    }
    //得到对角递减指标
    public int getDescendingDiagonalIndex() {
        return (getColumnIndex() - getRowIndex());
    }

    @Override
    public String toString() {
        return "Queen-" + column.getIndex();
    }
}
