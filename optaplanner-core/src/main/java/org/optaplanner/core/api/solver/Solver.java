/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
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

package org.optaplanner.core.api.solver;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.score.FeasibilityScore;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.constraint.ConstraintMatch;
import org.optaplanner.core.api.solver.event.BestSolutionChangedEvent;
import org.optaplanner.core.api.solver.event.SolverEventListener;
import org.optaplanner.core.impl.score.director.ScoreDirector;
import org.optaplanner.core.impl.score.director.ScoreDirectorFactory;
import org.optaplanner.core.impl.solver.ProblemFactChange;
import org.optaplanner.core.impl.solver.termination.Termination;

/**
 * A Solver solves a planning problem.
 * 解算器解决了规划问题。
 * 客户通常会调用{@link #solve}然后调用{@link #getBestSolution（）}。
 * 这些方法不是线程安全的，应该从同一个线程调用，除了明确标记为线程安全的方法。
 * 请注意，尽管{@link #solve}对于此类的客户端来说不是线程安全的，但该方法可以在其自身内部进行多线程处理。
 * Build by a {@link SolverFactory}.
 * @param <Solution_> the solution type, the class with the {@link PlanningSolution} annotation
 * 由{@link SolverFactory}构建。
 * @param <Solution_>解决方案类型，带有{@link PlanningSolution}注释的类
 */
public interface Solver<Solution_> {

    /**
     * 最佳解决方案是在解决过程中找到的{@link PlanningSolution最佳解决方案}：它可能是也可能不是最佳，可行或甚至是初始化的。
     * <p>
     * {@link #solve} 可以返回最佳方案，但这种方法在罕见的异步情况下很有用（尽管如此
     * {@link SolverEventListener#bestSolutionChanged(BestSolutionChangedEvent)} 通常更合适).
     * <p>
     * 此方法是线程安全的。
     *  @return 永远不会为null，但它可以返回未初始化的{@link PlanningSolution}，其中{@link Score}为null。
     */

    Solution_ getBestSolution();//得到最佳结果

    /**
     * Returns the {@link Score} of the {@link #getBestSolution()}.
     * <p>
     * This is useful for generic code, which doesn't know the type of the {@link PlanningSolution}
     * to retrieve the {@link Score} from the {@link #getBestSolution()} easily.
     * 这对于通用代码很有用，它不知道{@link PlanningSolution}的类型，可以轻松地从{@link #getBestSolution（）}中检索{@link Score}。
     * <p>
     * This method is thread-safe.
     * @return null if the {@link PlanningSolution} is still uninitialized
     */
    Score getBestScore();//得到最好分数

    /**
     * Returns a diagnostic text that explains the {@link #getBestSolution()} through the {@link ConstraintMatch} API
     * to identify which constraints or planning entities cause that {@link #getBestScore()} quality.
     * In case of an {@link FeasibilityScore#isFeasible() infeasible} solution,
     * this can help diagnose the cause of that.
     * 返回一个诊断文本，通过{@link ConstraintMatch} API解释{@link #getBestSolution（）}，以确定哪些约束或计划实体导致{@link #getBestScore（）}质量。
     * 如果{@link FeasibilityScore＃isFeasible（）不可行}解决方案，这可以帮助诊断其原因。
     * <p>
     * Do not parse this string.
     * Instead, to provide this information in a UI or a service, use {@link #getScoreDirectorFactory()}
     * to retrieve {@link ScoreDirector#getConstraintMatchTotals()} and {@link ScoreDirector#getIndictmentMap()}
     * and convert those into a domain specific API.
     * 请勿解析此字符串。相反，要在UI或服务中提供此信息，请使用{@link #getScoreDirectorFactory（）}检索
     * {@link ScoreDirector＃getConstraintMatchTotals（）}和{@link ScoreDirector＃getIndictmentMap（）}和将它们转换为特定于域的API。
     * <p>
     * This method is thread-safe.
     * @return null if {@link #getBestScore()} returns null
     * @see ScoreDirector#explainScore()
     */
    String explainBestScore();

    /**
     * Returns the amount of milliseconds spent solving since the last start.
     * If it hasn't started it yet, it returns 0.
     * If it hasn't ended yet, it returns the time between the last start and now.
     * If it has ended already, it returns the time between the last start and the ending.
     * <p>
     * A {@link #addProblemFactChange(ProblemFactChange)} triggers a restart which resets this time.
     * <p>
     * This method is thread-safe.
     * @return the amount of milliseconds spent solving since the last (re)start, at least 0
     */
    long getTimeMillisSpent(); //解决规划问题并返回遇到的最佳解决方案（可能是也可能不是最佳的，可行的，甚至是初始化的）。

    /**
     * 解决规划问题并返回遇到的最佳解决方案（可能是也可能不是最佳的，可行的，甚至是初始化的）。
     * <p>
     * 此方法返回之前可能需要几秒，几分钟甚至几小时或几天，具体取决于{@link Termination}配置。
     * 要提前终止{@link Solver}，请致电{@link #terminateEarly（）}。
     * @param problem never null, usually its planning variables are uninitialized
     * @return never null, but it can return the original, uninitialized {@link PlanningSolution} with a {@link Score} null.
     * @see #terminateEarly()
     */
    Solution_ solve(Solution_ problem);

    /**
     * This method is thread-safe.
     * @return true if the {@link #solve} method is still running.
     */
    boolean isSolving();
    //通知解算器它应该尽早停止。 此方法立即返回，但需要一个不确定的时间
    /**
     * Notifies the solver that it should stop at its earliest convenience.
     * This method returns immediately, but it takes an undetermined time
     * for the {@link #solve} to actually return.
     * <p>
     * This method is thread-safe.
     * @return true if successful
     * @see #isTerminateEarly()
     * @see Future#cancel(boolean)
     */
    boolean terminateEarly();

    /**
     * This method is thread-safe.
     * @return true if terminateEarly has been called since the {@link Solver} started.
     * @see Future#isCancelled()
     */
    boolean isTerminateEarly();

    /**
     * Schedules a {@link ProblemFactChange} to be processed.
     * <p>
     * As a side-effect, this restarts the {@link Solver}, effectively resetting all {@link Termination}s,
     * but not {@link #terminateEarly()}.
     * <p>
     * This method is thread-safe.
     * Follows specifications of {@link BlockingQueue#add(Object)} with by default
     * a capacity of {@link Integer#MAX_VALUE}.
     * @param problemFactChange never null
     * @return true (as specified by {@link Collection#add})
     * @see #addProblemFactChanges(List)
     */
    boolean addProblemFactChange(ProblemFactChange<Solution_> problemFactChange);

    /**
     * Schedules multiple {@link ProblemFactChange}s to be processed.
     * <p>
     * As a side-effect, this restarts the {@link Solver}, effectively resetting all {@link Termination}s,
     * but not {@link #terminateEarly()}.
     * <p>
     * This method is thread-safe.
     * Follows specifications of {@link BlockingQueue#addAll(Collection)} with by default
     * a capacity of {@link Integer#MAX_VALUE}.
     * @param problemFactChangeList never null
     * @return true (as specified by {@link Collection#add})
     * @see #addProblemFactChange(ProblemFactChange)
     */
    boolean addProblemFactChanges(List<ProblemFactChange<Solution_>> problemFactChangeList);

    /**
     * Checks if all scheduled {@link ProblemFactChange}s have been processed.
     * <p>
     * This method is thread-safe.
     * @return true if there are no {@link ProblemFactChange}s left to do
     */
    boolean isEveryProblemFactChangeProcessed();

    /**
     * @param eventListener never null
     */
    void addEventListener(SolverEventListener<Solution_> eventListener);

    /**
     * @param eventListener never null
     */
    void removeEventListener(SolverEventListener<Solution_> eventListener);

    /**
     * Useful to reuse the {@link Score} calculation in a UI (or even to explain the {@link Score} in a UI).
     *
     * @return never null
     */
    /**
           *有助于在UI中重用{@link Score}计算（甚至可以在UI中解释{@link Score}）。
          *
           * @return永远不会为空
          */
    ScoreDirectorFactory<Solution_> getScoreDirectorFactory();

}
