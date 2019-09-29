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

package org.optaplanner.examples.mynqueen.persistence;

import org.optaplanner.examples.common.app.CommonApp;
import org.optaplanner.examples.common.app.LoggingMain;
import org.optaplanner.examples.common.persistence.AbstractSolutionImporter;

import org.optaplanner.examples.mynqueen.domain.Column;
import org.optaplanner.examples.mynqueen.domain.NQueens;
import org.optaplanner.examples.mynqueen.domain.Queen;
import org.optaplanner.examples.mynqueen.domain.Row;
import org.optaplanner.persistence.common.api.domain.solution.SolutionFileIO;
import org.optaplanner.persistence.xstream.impl.domain.solution.XStreamSolutionFileIO;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class NQueensGenerator extends LoggingMain {

    public static void main(String[] args) {
        NQueensGenerator generator = new NQueensGenerator();
        generator.writeNQueens(4);
        generator.writeNQueens(8);
        generator.writeNQueens(16);
        generator.writeNQueens(32);
        generator.writeNQueens(64);
        generator.writeNQueens(256);
    }

    protected final SolutionFileIO<NQueens> solutionFileIO;
    //protected final File outputDir;

    public NQueensGenerator() {
        solutionFileIO = new XStreamSolutionFileIO<>(NQueens.class);
        //outputDir = new File(CommonApp.determineDataDir(NQueensApp.DATA_DIR_NAME), "unsolved");
    }

    private void writeNQueens(int n) {
        String outputFileName = n + "queens.xml";
        //File outputFile = new File(outputDir, outputFileName);
        NQueens nQueens = createNQueens(n);
        //solutionFileIO.write(nQueens, outputFile);
        //logger.info("Saved: {}", outputFile);
    }

    public NQueens createNQueens(int n) {
        NQueens nQueens = new NQueens();
        nQueens.setId(0L);
        nQueens.setN(n);
        nQueens.setColumnList(createColumnList(nQueens));
        nQueens.setRowList(createRowList(nQueens));
        nQueens.setQueenList(createQueenList(nQueens));
        //valueOf() 方法用于返回给定参数的原生 Number 对象值，参数可以是原生数据类型, String等。
        //pow() 方法用于返回第一个参数的第二个参数次方。
        //创建 可能的解决方案大小
        BigInteger possibleSolutionSize = BigInteger.valueOf(nQueens.getN()).pow(nQueens.getN());
        logger.info("N皇后问题搜索空间大小为{}.",
                AbstractSolutionImporter.getFlooredPossibleSolutionSize(possibleSolutionSize));
        return nQueens;
    }

    //针对nQueens问题，得到n ,并创建列索引号
    private List<Column> createColumnList(NQueens nQueens) {
        int n = nQueens.getN();
        List<Column> columnList = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            Column column = new Column();
            column.setId((long) i);
            //列索引号
            column.setIndex(i);
            columnList.add(column);
        }
        return columnList;
    }

    //创建行索引号。放置到rowlist里面
    private List<Row> createRowList(NQueens nQueens) {
        int n = nQueens.getN();
        List<Row> rowList = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            Row row = new Row();
            row.setId((long) i);
            row.setIndex(i);
            rowList.add(row);
        }
        return rowList;
    }

    private List<Queen> createQueenList(NQueens nQueens) {
        int n = nQueens.getN();
        List<Queen> queenList = new ArrayList<>(n);
        long id = 0;
        for (Column column : nQueens.getColumnList()) {
            Queen queen = new Queen();
            queen.setId(id);
            id++;
            queen.setColumn(column);
            // Notice that we leave the PlanningVariable properties on null
            //我们将PlanningVariable属性保留为null
            //创建的queen包含列索引号
            //也就是说，queenList含有N个queen，每个queen都有唯一的一个column（列索引号）
            queenList.add(queen);
        }
        return queenList;
    }

}
