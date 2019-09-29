/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
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

package org.optaplanner.examples.projectjobscheduling.domain;

//这是实现技巧。SOURCE和SINK基本上是虚拟的。
// 因为一个项目可能从多个并行的作业开始，所以一个SOURCE作业放在它的前面，只有一个根。最后也一样：它可以以多个结尾，
// 因此紧随其后的是SINK作业，只有一条尾巴。这样可以更轻松，更快速地确定制造时间等。
public enum JobType {
    SOURCE, //入口
    STANDARD,//内容
    SINK; //出口
}
