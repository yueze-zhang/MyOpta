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

package org.optaplanner.core.api.domain.solution;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.optaplanner.core.api.domain.autodiscover.AutoDiscoverMemberType;
import org.optaplanner.core.api.domain.constraintweight.ConstraintConfigurationProvider;
import org.optaplanner.core.api.domain.lookup.LookUpStrategyType;
import org.optaplanner.core.api.domain.solution.cloner.SolutionCloner;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactProperty;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Specifies that the class is a planning solution.
 * A solution represents a problem and a possible solution of that problem.
 * A possible solution does not need to be optimal or even feasible.
 * A solution's planning variables might not be initialized (especially when delivered as a problem).
 * 指定该类是计划解决方案。
 * 解决方案代表了该问题以及该问题的可能解决方案。
 * 可能的解决方案不需要是最佳的或甚至不可行的。
 * 解决方案的计划变量可能未初始化（特别是在作为问题交付时）。
 * <p>
 * A solution is mutable.
 * For scalability reasons (to facilitate incremental score calculation),
 * the same solution instance (called the working solution per move thread) is continuously modified.
 * It's cloned to recall the best solution.
 * 解决方案是可变的。
 * 在可扩展性的原因（便于增量分数计算），相同的解决方案实例（称为移动每线程的工作溶液中）连续修改。
 * 它被克隆以回忆起最佳解决方案。
 * <p>
 * 每个计划解决方案必须具有1个{@link PlanningScore}属性。
 * <p>
 * 每个计划解决方案必须至少有1个{@link PlanningEntityCollectionProperty}或{@link PlanningEntityProperty}属性。
 * <p>
 * 建议每个规划解决方案也具有1个{@link ConstraintConfigurationProvider}属性。
 * <p>
 * 与Drools得分计算一起使用的每个计划解决方案必须至少有1个{@link ProblemFactCollectionProperty}或{@link ProblemFactProperty}属性。
 * <p>
 * 该类应该有一个公共的无参数构造函数，因此可以克隆它（除非指定了{@link #solutionCloner（）}）。
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface PlanningSolution {

    /**
     * Enable reflection through the members of the class
     * to automatically assume {@link PlanningScore}, {@link PlanningEntityCollectionProperty},
     * {@link PlanningEntityProperty}, {@link ProblemFactCollectionProperty}, {@link ProblemFactProperty}
     * and {@link ConstraintConfigurationProvider} annotations based on the member type.
     * @return never null
     *
     * 通过类成员启用反射，以自动假设基于以下内容的{@link PlanningScore}，{@link PlanningEntityCollectionProperty}，
     * {@link PlanningEntityProperty}，{@link ProblemFactCollectionProperty}，
     * {@link ProblemFactProperty}和{@link ConstraintConfigurationProvider}注释成员类型。
     */
    AutoDiscoverMemberType autoDiscoverMemberType() default AutoDiscoverMemberType.NONE;

    /**
     * 复写默认的{@link SolutionCloner}以实现自定义的{@link PlanningSolution}克隆实现。
     * <p>
     * 如果未指定，则使用默认的基于反射的{@link SolutionCloner}，因此您不必担心它。
     * @return {@link NullSolutionCloner} when it is null (workaround for annotation limitation)
     */
    Class<? extends SolutionCloner> solutionCloner()
            default NullSolutionCloner.class;

    /** Workaround for annotation limitation in {@link #solutionCloner()}. */
    interface NullSolutionCloner extends SolutionCloner {}

    /**
     * @return never null
     */
    LookUpStrategyType lookUpStrategyType() default LookUpStrategyType.PLANNING_ID_OR_NONE;

}
