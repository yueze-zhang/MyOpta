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

package org.optaplanner.core.impl.domain.variable.listener;

import org.optaplanner.core.impl.domain.variable.supply.Supply;
import org.optaplanner.core.impl.score.director.ScoreDirector;

/**
 * Changes shadow variables when a genuine planning variable changes.
 * <p>
 * Important: it must only change the shadow variable(s) for which it's configured!
 * It should never change a genuine variable or a problem fact.
 * It can change its shadow variable(s) on multiple entity instances
 * (for example: an arrivalTime change affects all trailing entities too).
 * <p>
 * Each {@link ScoreDirector} has a different {@link VariableListener} instance, so it can be stateful.
 * If it is stateful, it must implement {@link StatefulVariableListener}.
 * *当真正的计划变量更改时，更改阴影变量。
 *
 *   *重要提示：它只能更改为其配置的阴影变量！
 *   *绝对不要更改真实变量或问题事实。
 *   *它可以在多个实体实例上更改其影子变量
 *   *（例如：arrivingTime更改也会影响所有尾随实体）。
 *
 *   *每个{@link ScoreDirector}都有一个不同的{@link VariableListener}实例，因此它可以是有状态的。
 *   *如果它是有状态的，则必须实现{@link StatefulVariableListener}。
 */
public interface VariableListener<Entity_> extends Supply {

    /**
     * When set to {@code true}, this has a slight performance loss in Planner.
     * When set to {@code false}, it's often easier to make the listener implementation correct and fast.
     * @return true to guarantee that each of the before/after methods will only be called once per entity instance
     * per operation type (add, change or remove).
     */

    /**
                  *设置为{@code true}时，这在Planner中会造成轻微的性能损失。
                  *设置为{@code false}时，通常更容易使侦听器实现正确且快速。
                  * @return true以确保每个entry / after方法在每个实体实例中仅被调用一次
      *每个操作类型（添加，更改或删除）。
    */
    default boolean requiresUniqueEntityEvents() {
        return false;
    }

    /**
     * @param scoreDirector never null
     * @param entity never null
     */
    void beforeEntityAdded(ScoreDirector scoreDirector, Entity_ entity);

    /**
     * @param scoreDirector never null
     * @param entity never null
     */
    void afterEntityAdded(ScoreDirector scoreDirector, Entity_ entity);

    /**
     * @param scoreDirector never null
     * @param entity never null
     */
    void beforeVariableChanged(ScoreDirector scoreDirector, Entity_ entity);

    /**
     * @param scoreDirector never null
     * @param entity never null
     */
    void afterVariableChanged(ScoreDirector scoreDirector, Entity_ entity);

    /**
     * @param scoreDirector never null
     * @param entity never null
     */
    void beforeEntityRemoved(ScoreDirector scoreDirector, Entity_ entity);

    /**
     * @param scoreDirector never null
     * @param entity never null
     */
    void afterEntityRemoved(ScoreDirector scoreDirector, Entity_ entity);

}
