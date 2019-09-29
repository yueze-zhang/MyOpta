package org.optaplanner.examples.mynqueen.app;

import org.optaplanner.examples.mynqueen.domain.NQueens;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.examples.mynqueen.domain.Queen;
import org.optaplanner.examples.mynqueen.persistence.NQueensGenerator;
import java.util.List;


/**
 * @program: optaplanner-examples
 * @description:
 * @author: Zhang
 * @create: 2019-09-24 16:52
 */
public class NQueensHelloWorld {
    public static void main(String[] args) {
        // Build the Solver
        SolverFactory<NQueens> solverFactory = SolverFactory.createFromXmlResource(
                "org/optaplanner/examples/mynqueen/solver/nqueensSolverConfig.xml");
        Solver<NQueens> solver = solverFactory.buildSolver();

        // Load a problem with 8 queens
        NQueens unsolved8Queens = new NQueensGenerator().createNQueens(8);

        // Solve the problem
        NQueens solved8Queens = solver.solve(unsolved8Queens);

        // Display the result
        System.out.println("\nSolved 8 queens:\n" + toDisplayString(solved8Queens));
    }


    public static String toDisplayString(NQueens nQueens) {
        StringBuilder displayString = new StringBuilder();
        int n = nQueens.getN();
        List<Queen> queenList = nQueens.getQueenList();
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < n; column++) {
                Queen queen = queenList.get(column);
                if (queen.getColumn().getIndex() != column) {
                    throw new IllegalStateException("queenList的顺序不正确。");
                }
                displayString.append(" ");
                if (queen.getRow() != null && queen.getRow().getIndex() == row) {
                    displayString.append("Q");
                } else {
                    displayString.append("_");
                }
            }
            displayString.append("\n");
        }
        return displayString.toString();
    }
}
