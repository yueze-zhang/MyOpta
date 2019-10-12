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

package org.optaplanner.core.impl.score.director;

import java.util.Collection;
import java.util.Map;

import org.optaplanner.core.api.domain.lookup.LookUpStrategyType;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.core.api.score.FeasibilityScore;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.constraint.ConstraintMatch;
import org.optaplanner.core.api.score.constraint.ConstraintMatchTotal;
import org.optaplanner.core.api.score.constraint.Indictment;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.impl.domain.variable.descriptor.VariableDescriptor;
import org.optaplanner.core.impl.heuristic.move.Move;
import org.optaplanner.core.impl.solver.ProblemFactChange;

/**
 * The ScoreDirector holds the {@link PlanningSolution working solution}
 * and calculates the {@link Score} for it.
 * @param <Solution_> the solution type, the class with the {@link PlanningSolution} annotation
 *   * ScoreDirector拥有{@link PlanningSolution 工作解决方案}
 *   *并为其计算{@link Score}。
 *   * @param <Solution_>解决方案类型，带有{@link PlanningSolution}批注的类
 */
public interface ScoreDirector<Solution_> extends AutoCloseable {

    /**
     * The {@link PlanningSolution} that is used to calculate the {@link Score}.
     * <p>
     * Because a {@link Score} is best calculated incrementally (by deltas),
     * the {@link ScoreDirector} needs to be notified when its {@link PlanningSolution working solution} changes.
     * <p>
     * If the {@link PlanningSolution working solution} has been changed since {@link #calculateScore} was called,
     * its {@link Score} won't be correct.
     * @return never null
     * *用于计算{@link Score}的{@link PlanningSolution}。
     *       * <p>
     *       *由于{@link Score}最好以增量方式（按增量）计算，
     *       *当{@link PlanningSolution 工作解决方案}发生变化时，需要通知{@link ScoreDirector}。
     *       * <p>
     *       *如果自调用{@link #calculateScore}以来{@link PlanningSolution 工作解决方案}已更改，
     *       *其{@link得分}不正确。
     *       * @return永远不会为空
     */
    Solution_ getWorkingSolution();

    /**
     * The {@link PlanningSolution working solution} must never be the same instance as the
     * {@link PlanningSolution best solution}, it should be a (un)changed clone.
     * <p>
     * Only call this method on a separate {@link ScoreDirector} instance,
     * built by {@link Solver#getScoreDirectorFactory()},
     * not on the one used inside the {@link Solver} itself.
     * @param workingSolution never null
     * {@link PlanningSolution 工作解决方案}绝对不能与{@link PlanningSolution 最佳解决方案}是同一实例，它应该是（未更改的）克隆。
     *       * <p>
     *       *仅在单独的{@link ScoreDirector}实例上调用此方法，
     *       *由{@link Solver＃getScoreDirectorFactory（）}构建，
     *       *不在{@link Solver}本身内部使用的那一项上。
     *       * @param workingSolution永远不会为空
     */
    void setWorkingSolution(Solution_ workingSolution);

    /**
     * Calculates the {@link Score} and updates the {@link PlanningSolution working solution} accordingly.
     * @return never null, the {@link Score} of the {@link PlanningSolution working solution}
     */
    Score calculateScore();

    /**
     * @return true if {@link #getConstraintMatchTotals()} can be called
     */
    boolean isConstraintMatchEnabled();

    /**
     * Explains the {@link Score} of {@link #calculateScore()} by splitting it up per constraint type
     * (which is usually a score rule).
     * <p>
     * The sum of {@link ConstraintMatchTotal#getScore()} equals {@link #calculateScore()}.
     * <p>
     * Call {@link #calculateScore()} before calling this method,
     * unless that method has already been called since the last {@link PlanningVariable} changes.
     * @return never null
     * @throws IllegalStateException if {@link #isConstraintMatchEnabled()} returns false
     */
    Collection<ConstraintMatchTotal> getConstraintMatchTotals();

    /**
     * Explains the impact of each planning entity or problem fact on the {@link Score}.
     * An indictment is basically the inverse of {@link #getConstraintMatchTotals()}:
     * it is a {@link Score} total for each justification {@link Object}
     * in {@link ConstraintMatch#getJustificationList()}.
     * <p>
     * Warning: In practice, it often doesn't include the full impact on the {@link Score},
     * for example in DRL score rules with accumulate, the accumulate elements won't be indicted.
     * <p>
     * The sum of {@link ConstraintMatchTotal#getScore()} differs from {@link #calculateScore()}
     * because each {@link ConstraintMatch#getScore()} is counted
     * for each justification in {@link ConstraintMatch#getJustificationList()}.
     * <p>
     * Call {@link #calculateScore()} before calling this method,
     * unless that method has already been called since the last {@link PlanningVariable} changes.
     * @return never null
     * @throws IllegalStateException if {@link #isConstraintMatchEnabled()} returns false
     */
    Map<Object, Indictment> getIndictmentMap();

    /**
     * Returns a diagnostic text that explains the {@link Score} through the {@link ConstraintMatch} API
     * to identify which constraints or planning entities cause that score quality.
     * In case of an {@link FeasibilityScore#isFeasible() infeasible} solution,
     * this can help diagnose the cause of that.
     * <p>
     * Do not parse this string.
     * Instead, to provide this information in a UI or a service,
     * use {@link #getConstraintMatchTotals()} and {@link #getIndictmentMap()}
     * and convert those into a domain specific API.
     * <p>
     * This automatically calls {@link #calculateScore()} first.
     * @return never null
     * @throws IllegalStateException if {@link #isConstraintMatchEnabled()} returns false
     */
    String explainScore();

    void beforeEntityAdded(Object entity);

    void afterEntityAdded(Object entity);

    void beforeVariableChanged(Object entity, String variableName);

    void afterVariableChanged(Object entity, String variableName);

    // TODO VariableDescriptor is not likely to go to public API

    void beforeVariableChanged(VariableDescriptor variableDescriptor, Object entity);

    void afterVariableChanged(VariableDescriptor variableDescriptor, Object entity);

    void changeVariableFacade(VariableDescriptor variableDescriptor, Object entity, Object newValue);

    void triggerVariableListeners();

    void beforeEntityRemoved(Object entity);

    void afterEntityRemoved(Object entity);

    // TODO extract this set of methods into a separate interface, only used by ProblemFactChange

    void beforeProblemFactAdded(Object problemFact);

    void afterProblemFactAdded(Object problemFact);

    void beforeProblemPropertyChanged(Object problemFactOrEntity);

    void afterProblemPropertyChanged(Object problemFactOrEntity);

    void beforeProblemFactRemoved(Object problemFact);

    void afterProblemFactRemoved(Object problemFact);

    /**
     * Translates an entity or fact instance (often from another {@link Thread} or JVM)
     * to this {@link ScoreDirector}'s internal working instance.
     * Useful for {@link Move#rebase(ScoreDirector)} and in a {@link ProblemFactChange}.
     * <p>
     * Matching is determined by the {@link LookUpStrategyType} on {@link PlanningSolution}.
     * Matching uses a {@link PlanningId} by default.
     * @param externalObject sometimes null
     * @return null if externalObject is null or if there is no workingObject for externalObject
     * @throws IllegalArgumentException if it cannot be looked up or if the externalObject's class is not supported
     * @throws IllegalStateException if it cannot be looked up
     * @param <E> the object type
     */
    <E> E lookUpWorkingObject(E externalObject);

    /**
     * Needs to be called after use because some implementations need to clean up their resources.
     */
    @Override
    void close();

    /**
     * @deprecated in favor of {@link #close()}
     */
    @Deprecated
    default void dispose() {
        close();
    }

}
