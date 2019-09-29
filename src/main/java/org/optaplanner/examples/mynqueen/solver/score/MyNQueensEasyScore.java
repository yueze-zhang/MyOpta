package org.optaplanner.examples.mynqueen.solver.score;

import org.optaplanner.core.api.score.buildin.simple.SimpleScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;
import org.optaplanner.examples.mynqueen.domain.NQueens;
import org.optaplanner.examples.mynqueen.domain.Queen;

import java.util.List;

/**
 * @program: optaplanner-examples
 * @description:
 * @author: Zhang
 * @create: 2019-09-24 17:05
 */
public class MyNQueensEasyScore implements EasyScoreCalculator<NQueens> {
    @Override
    public SimpleScore calculateScore(NQueens nQueens) {
        int n = nQueens.getN();
        List<Queen> queenList = nQueens.getQueenList();

        int score = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Queen leftQueen = queenList.get(i);
                Queen rightQueen = queenList.get(j);
                if (leftQueen.getRow() != null && rightQueen.getRow() != null) {
                    if (leftQueen.getRowIndex() == rightQueen.getRowIndex()) {
                        score--;
                    }
                    if (leftQueen.getAscendingDiagonalIndex() == rightQueen.getAscendingDiagonalIndex()) {
                        score--;
                    }
                    if (leftQueen.getDescendingDiagonalIndex() == rightQueen.getDescendingDiagonalIndex()) {
                        score--;
                    }
                }
            }
        }
        return SimpleScore.valueOf(score);
    }
}
