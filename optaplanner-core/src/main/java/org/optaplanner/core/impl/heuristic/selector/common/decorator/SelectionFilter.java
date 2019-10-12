/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
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

package org.optaplanner.core.impl.heuristic.selector.common.decorator;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.heuristic.selector.Selector;
import org.optaplanner.core.impl.score.director.ScoreDirector;

/**
 * Decides on accepting or discarding a selection
 * (which is a {@link PlanningEntity}, a planningValue, a {@link Move} or a {@link Selector}).
 * For example, an immovable {@link PlanningEntity} is rejected and therefore never used in a {@link Move}.
 * <p>
 * A filtered selection is considered as not selected, it does not count as an unaccepted selection.
 * @param <Solution_> the solution type, the class with the {@link PlanningSolution} annotation
 * @param <T> the selection type
 *   *决定接受还是放弃选择
 *   *（这是{@link PlanningEntity}，planningValue，{@ link Move}或{@link Selector}）。
 *   *例如，不可移动的{@link PlanningEntity}被拒绝，因此从未在{@link Move}中使用。
 *   * <p>
 *   *过滤的选择被视为未选择，不算作不接受的选择。
 *   * @param <Solution_>解决方案类型，带有{@link PlanningSolution}批注的类
 *   * @param <T>选择类型
 */
public interface SelectionFilter<Solution_, T> {

    /**
     * @param scoreDirector never null, the {@link ScoreDirector}
     * which has the {@link ScoreDirector#getWorkingSolution()} to which the selection belongs or applies to
     * @param selection never null, a {@link PlanningEntity}, a planningValue, a {@link Move} or a {@link Selector}
     * @return true if the selection is accepted (for example it is movable),
     * false if the selection will be discarded (for example it is immovable)
     *
     * @param scoreDirector 永远不会为空，{@ link ScoreDirector}
     * 具有所选内容所属或适用于的{@link ScoreDirector＃getWorkingSolution（）}
     * @param selection 选择永远不会为空，{@ link PlanningEntity}，planningValue，{@ link Move}或{@link Selector}
     * @return 如果选择被接受（例如，它是可移动的），则返回true，
     *       *如果选择将被丢弃（例如，不可移动），则返回false
     */
    boolean accept(ScoreDirector<Solution_> scoreDirector, T selection);

}
