package org.optaplanner.examples.cloudmy.app;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import org.optaplanner.examples.cloudmy.domain.Balance;
import org.optaplanner.examples.cloudmy.domain.Computer;
import org.optaplanner.examples.cloudmy.domain.Process;
import org.optaplanner.examples.cloudmy.persistence.Generator;

/**
 * @program: optaplanner-examples
 * @description:
 * @author: Zhang
 * @create: 2019-09-27 14:32
 */
public class CloudAPP {
    public static void main(String[] args) {
// Build the Solver
        SolverFactory<Balance> solverFactory = SolverFactory.createFromXmlResource(
                "org/optaplanner/examples/cloud/cloudSolverConfig.xml");
        Solver<Balance> solver = solverFactory.buildSolver();
        Balance unsolvedCloudBalance = new Generator().createCloudBalance(4, 25);
        Balance solvedCloudBalance = solver.solve(unsolvedCloudBalance);
        // 显示结果
        System.out.println("\nSolved cloudBalance with 400 computers and 1200 processes:\n"
                + toDisplayString(solvedCloudBalance));
    }

    public static String toDisplayString(Balance cloudBalance) {
        StringBuilder displayString = new StringBuilder();
        for (Process process : cloudBalance.getProcessList()) {
            Computer computer = process.getComputer();
            displayString.append("  ").append(process.getLabel()).append(" -> ")
                    .append(computer == null ? null : computer.getLabel()).append("\n");
        }
        return displayString.toString();
    }

}
