package org.optaplanner.examples.mynqueen.domain.solution;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory;
import org.optaplanner.examples.mynqueen.domain.NQueens;
import org.optaplanner.examples.mynqueen.domain.Queen;

/**
 * @program: optaplanner-examples
 * @description:
 * @author: Zhang
 * @create: 2019-09-25 12:44
 */
public class QueenDifficultyWeightFactory implements SelectionSorterWeightFactory<NQueens, Queen> {
    @Override
    public QueenDifficultyWeight createSorterWeight(NQueens nQueens, Queen queen) {
        int distanceFromMiddle = calculateDistanceFromMiddle(nQueens.getN(), queen.getColumnIndex());
        return new QueenDifficultyWeight(queen, distanceFromMiddle);
    }

    private static int calculateDistanceFromMiddle(int n, int columnIndex) {
        int middle = n / 2;
        int distanceFromMiddle = Math.abs(columnIndex - middle);
        if ((n % 2 == 0) && (columnIndex < middle)) {
            distanceFromMiddle--;
        }
        return distanceFromMiddle;
    }

    public static class QueenDifficultyWeight implements Comparable<QueenDifficultyWeight> {

        private final Queen queen;
        private final int distanceFromMiddle;

        public QueenDifficultyWeight(Queen queen, int distanceFromMiddle) {
            this.queen = queen;
            this.distanceFromMiddle = distanceFromMiddle;
        }

        @Override
        public int compareTo(QueenDifficultyWeight other) {
            return new CompareToBuilder()
                    // The more difficult queens have a lower distance to the middle
                    //难度较大的王后与中间人物的距离较小。
                    .append(other.distanceFromMiddle, distanceFromMiddle) // Decreasing
                    // Tie breaker
                    .append(queen.getColumnIndex(), other.queen.getColumnIndex())
                    .toComparison();
        }

    }
}
