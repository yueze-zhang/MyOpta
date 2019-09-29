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

package org.optaplanner.examples.cloudbalancing.app;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.examples.cloudbalancing.domain.CloudBalance;
import org.optaplanner.examples.cloudbalancing.swingui.CloudBalancingPanel;
import org.optaplanner.examples.common.app.CommonApp;
import org.optaplanner.persistence.common.api.domain.solution.SolutionFileIO;
import org.optaplanner.persistence.xstream.impl.domain.solution.XStreamSolutionFileIO;

/**
 * For an easy example, look at {@link CloudBalancingHelloWorld} instead.
 */
public class CloudBalancingApp extends CommonApp<CloudBalance> {

    //求解器位置所在
    public static final String SOLVER_CONFIG
            = "org/optaplanner/examples/cloudbalancing/solver/cloudBalancingSolverConfig.xml";
    //数据文件存放位置名字
    public static final String DATA_DIR_NAME = "cloudbalancing";

    public static void main(String[] args) {
        prepareSwingEnvironment();
        new CloudBalancingApp().init();
    }

    public CloudBalancingApp() {
        super("Cloud balancing",  //这些内容将会显示到首页的说明中
                "将进程分配给计算机.\n" +
                "每台计算机都必须有足够的硬件来运行其所有进程.\n" +
                "每台使用中的计算机都会产生维护成本.",
                SOLVER_CONFIG, DATA_DIR_NAME,
                CloudBalancingPanel.LOGO_PATH);
    }

    @Override
    protected Solver<CloudBalance> createSolver() {
        SolverFactory<CloudBalance> solverFactory = SolverFactory.createFromXmlResource(SOLVER_CONFIG);
        return solverFactory.buildSolver();
    }

    @Override
    protected CloudBalancingPanel createSolutionPanel() {
        return new CloudBalancingPanel();
    }

    @Override
    public SolutionFileIO<CloudBalance> createSolutionFileIO() {
        return new XStreamSolutionFileIO<>(CloudBalance.class);
    }
}
