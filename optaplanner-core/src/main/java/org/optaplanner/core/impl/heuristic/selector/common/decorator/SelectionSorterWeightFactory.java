/*
 * Copyright 2011 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.core.impl.heuristic.selector.common.decorator;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.heuristic.selector.Selector;

/**
 * Creates a weight to decide the order of a collections of selections
 * (a selection is a {@link PlanningEntity}, a planningValue, a {@link Move} or a {@link Selector}).
 * The selections are then sorted by their weight,
 * normally ascending unless it's configured descending.
 * @param <Solution_> the solution type, the class with the {@link PlanningSolution} annotation
 * @param <T> the selection type
 */

/**
 *创建权重以决定选择集合的顺序
 *（选择是{@link PlanningEntity}，planningValue，{@link Move}或{@link Selector}）。
 *然后，选择项按其权重排序，
 *除非配置为下降，否则通常会上升。
 * @param <Solution_>解决方案类型，带有{@link PlanningSolution}批注的类
 * @param <T>选择类型
 */
public interface SelectionSorterWeightFactory<Solution_, T> {

    /**
     * @param solution never null, the {@link PlanningSolution} to which the selection belongs or applies to
     * @param selection never null, a {@link PlanningEntity}, a planningValue, a {@link Move} or a {@link Selector}
     * @return never null, for example a {@link Integer}, {@link Double} or a more complex {@link Comparable}
     */
    /**
     * @param solution 永远不会为空，所选内容所属或应用于的{@link PlanningSolution}
     * @param selection 选择永远不会为空，{@link PlanningEntity}，planningValue，{@link Move}或{@link Selector}
     * @return 绝不能为空，例如{@link Integer}，{@link Double}或更复杂的{@link Comparable}
     */
    Comparable createSorterWeight(Solution_ solution, T selection);

}
