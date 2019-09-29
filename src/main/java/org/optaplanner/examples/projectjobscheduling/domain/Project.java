/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
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

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.optaplanner.examples.common.domain.AbstractPersistable;
import org.optaplanner.examples.projectjobscheduling.domain.resource.LocalResource;

@XStreamAlias("PjsProject")
public class Project extends AbstractPersistable {

    //IIRC，releaseDate是我们被允许开始第一份工作的第一天。
    // 例如：您必须创建一本书，但只能在下周一获得实际的最终内容，
    // 因此releaseDate在下周一（您不能在该日期之前开始任何工作）。
    private int releaseDate;

    //CriticalPathDuration是理论上的最小持续时间（如果我们可以愉快地忽略资源IIRC）。
    // 例如：如果作业A需要5天，而作业B需要2天，而B必须在A之后完成，则关键路径持续时间为7天。
    // 添加作业C需要1天的时间，并且可以与其他作业并行完成，因此不影响此操作。
    private int criticalPathDuration;

    private List<LocalResource> localResourceList;  //本地资源列表
    private List<Job> jobList;

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getCriticalPathDuration() {
        return criticalPathDuration;
    }

    public void setCriticalPathDuration(int criticalPathDuration) {
        this.criticalPathDuration = criticalPathDuration;
    }

    public List<LocalResource> getLocalResourceList() {
        return localResourceList;
    }

    public void setLocalResourceList(List<LocalResource> localResourceList) {
        this.localResourceList = localResourceList;
    }

    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public int getCriticalPathEndDate() {
        return releaseDate + criticalPathDuration;
    }

    public String getLabel() {
        return "Project " + id;
    }

}
