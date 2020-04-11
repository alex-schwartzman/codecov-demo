package com.solutions.codility;

import java.util.*;

//https://app.codility.com/programmers/task/sprinklers_arrangement/
public class Solution {

    static int MODULO_BASE = 1000000007;
    int stepsCount = 0;
    ArrayList<Integer>[] rows;
    ArrayList<Integer>[] columns;
    int fieldSize;

    public int solution(int[] X, int[] Y) {

        fieldSize = X.length;
        initColumnsRows(fieldSize);

        for (int i = 0; i < fieldSize; i++) {
            X[i] = X[i] - 1;
            Y[i] = Y[i] - 1;
            addSprinkler(X[i], Y[i], i);
        }

        //walk through columns and move them left and right to get even distribution
        int lastSeenEmptyColumn = 0;
        int lastSeenFilledColumn = 0;
        for (int x = 0; x < fieldSize; x++) {
            ArrayList<Integer> currentColumn = columns[x];
            while (currentColumn.size() != 1) {
                if (currentColumn.size() > 1) {
                    //destination column = x + nextEmptyColumnOffset, source column = x
                    int destinationColumn = findNextEmptyColumn(Math.max(lastSeenEmptyColumn, x));
                    lastSeenEmptyColumn = destinationColumn;
                    int sprinklerToBeMoved = trimTail(currentColumn);
                    X[sprinklerToBeMoved] = destinationColumn;
                    columns[destinationColumn].add(sprinklerToBeMoved);
                    stepsCount += destinationColumn - x;
                } else {
                    //destination column = x, source column = x + nextSprinklerColumnOffset
                    int nextSprinklerColumn = findNextSprinklerColumn(Math.max(x, lastSeenFilledColumn));
                    lastSeenFilledColumn = nextSprinklerColumn;
                    int sprinklerToBeMoved = trimTail(columns[nextSprinklerColumn]);
                    X[sprinklerToBeMoved] = x;
                    columns[x].add(sprinklerToBeMoved);
                    stepsCount += nextSprinklerColumn - x;
                }
            }
            if(stepsCount> MODULO_BASE){
                stepsCount=Math.floorMod(stepsCount, MODULO_BASE);
            }
        }

        //walk through rows and move them left and right to get even distribution
        int lastSeenEmptyRow = 0;
        int lastSeenFilledRow = 0;
        for (int y = 0; y < fieldSize; y++) {
            ArrayList<Integer> currentRow = rows[y];
            while (currentRow.size() != 1) {
                if (currentRow.size() > 1) {
                    //destination row = y + nextEmptyRowOffset, source row = y
                    int destinationRow = findNextEmptyRow(Math.max(y, lastSeenEmptyRow));
                    lastSeenEmptyRow = destinationRow;
                    int sprinklerToBeMoved = trimTail(currentRow);
                    Y[sprinklerToBeMoved] = destinationRow;
                    rows[destinationRow].add(sprinklerToBeMoved);
                    stepsCount += destinationRow - y;
                } else {
                    //destination row = y, source row = y + nextSprinklerRowOffset
                    int nextSprinklerRow = findNextSprinklerRow(Math.max(y, lastSeenFilledRow));
                    lastSeenFilledRow = nextSprinklerRow;
                    int sprinklerToBeMoved = trimTail(rows[nextSprinklerRow]);
                    Y[sprinklerToBeMoved] = y;
                    rows[y].add(sprinklerToBeMoved);
                    stepsCount += nextSprinklerRow - y;
                }
            }
            if (stepsCount > MODULO_BASE) {
                stepsCount = Math.floorMod(stepsCount, MODULO_BASE);
            }
        }

        return Math.floorMod(stepsCount, MODULO_BASE);
    }

    private int trimTail(ArrayList<Integer> currentRow) {
        return currentRow.remove(currentRow.size() - 1);
    }

    private int findNextSprinklerColumn(int columnToStart) {
        for (int column = columnToStart; column < fieldSize; column++) {
            if (columns[column].size() > 1) {
                return column;
            }
        }
        System.out.println("impossible condition - sprinklers count less than field size (findNextSprinklerColumnOffset)");
        return 0;
    }

    private int findNextEmptyColumn(int columnToStart) {
        for (int column = columnToStart; column < fieldSize; column++) {
            if (columns[column].isEmpty()) {
                return column;
            }
        }
        System.out.println("impossible condition - sprinklers count less than field size (findNextEmptyColumnOffset)");
        return fieldSize;
    }

    private int findNextSprinklerRow(int rowToStart) {
        for (int row = rowToStart; row < fieldSize; row++) {
            if (rows[row].size() > 1) {
                return row;
            }
        }
        System.out.println("impossible condition - sprinklers count less than field size (findNextSprinklerRowOffset)");
        return 0;
    }

    private int findNextEmptyRow(int rowToStart) {
        for (int row = rowToStart; row < fieldSize; row++) {
            if (rows[row].isEmpty()) {
                return row;
            }
        }
        System.out.println("impossible condition - sprinklers count less than field size (findNextEmptyRowOffset)");
        return 0;
    }

    private void initColumnsRows(int fieldSize) {
        rows = new ArrayList[fieldSize];
        columns = new ArrayList[fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            columns[i] = new ArrayList<>();
            rows[i] = new ArrayList<>();
        }
    }

    private void addSprinkler(int column, int row, int index) {
        columns[column].add(index);
        rows[row].add(index);
    }
}
