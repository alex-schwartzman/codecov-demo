package com.solutions.codility;

import java.util.*;

//https://app.codility.com/programmers/task/sprinklers_arrangement/
//Solution turns out to be N^2
public class Solution {
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
        for (int x = 0; x < fieldSize; x++) {
            ArrayList<Integer> currentColumn = columns[x];
            while (currentColumn.size() != 1) {
                if (currentColumn.size() > 1) {
                    //destination column = x + nextEmptyColumnOffset, source column = x
                    int nextEmptyColumnOffset = findNextEmptyColumnOffset(x);
                    int destinationColumn = x + nextEmptyColumnOffset;
                    int sprinklerToBeMoved = trimTail(currentColumn);
                    X[sprinklerToBeMoved] = destinationColumn;
                    columns[destinationColumn].add(sprinklerToBeMoved);
                    stepsCount += nextEmptyColumnOffset;
                } else {
                    //destination column = x, source column = x + nextSprinklerColumnOffset
                    int nextSprinklerColumnOffset = findNextSprinklerColumnOffset(x);
                    int sprinklerToBeMoved = trimTail(columns[x + nextSprinklerColumnOffset]);
                    X[sprinklerToBeMoved] = x;
                    columns[x].add(sprinklerToBeMoved);
                    stepsCount += nextSprinklerColumnOffset;
                }
            }
        }

        //walk through rows and move them left and right to get even distribution
        for (int y = 0; y < fieldSize; y++) {
            ArrayList<Integer> currentRow = rows[y];
            while (currentRow.size() != 1) {
                if (currentRow.size() > 1) {
                    //destination row = y + nextEmptyRowOffset, source row = y
                    int nextEmptyRowOffset = findNextEmptyRowOffset(y);
                    int destinationRow = y + nextEmptyRowOffset;
                    int sprinklerToBeMoved = trimTail(currentRow);
                    Y[sprinklerToBeMoved] = destinationRow;
                    rows[destinationRow].add(sprinklerToBeMoved);
                    stepsCount += nextEmptyRowOffset;
                } else {
                    //destination row = y, source row = y + nextSprinklerRowOffset
                    int nextSprinklerRowOffset = findNextSprinklerRowOffset(y);
                    int sprinklerToBeMoved = trimTail(rows[y + nextSprinklerRowOffset]);
                    Y[sprinklerToBeMoved] = y;
                    rows[y].add(sprinklerToBeMoved);
                    stepsCount += nextSprinklerRowOffset;
                }
            }
        }

        return stepsCount;
    }

    private int trimTail(ArrayList<Integer> currentRow) {
        return currentRow.remove(currentRow.size() - 1);
    }

    private int findNextSprinklerColumnOffset(int columnToStart) {
        for (int column = columnToStart + 1; column < fieldSize + 1; column++) {
            if (columns[column].size() > 1) {
                return column - columnToStart;
            }
        }
        System.out.println("impossible condition - sprinklers count less than field size (findNextSprinklerColumnOffset)");
        return 0;
    }

    private int findNextEmptyColumnOffset(int columnToStart) {
        for (int column = columnToStart + 1; column < fieldSize + 1; column++) {
            if (columns[column].isEmpty()) {
                return column - columnToStart;
            }
        }
        System.out.println("impossible condition - sprinklers count less than field size (findNextEmptyColumnOffset)");
        return fieldSize;
    }

    private int findNextSprinklerRowOffset(int rowToStart) {
        for (int row = rowToStart + 1; row < fieldSize + 1; row++) {
            if (rows[row].size() > 1) {
                return row - rowToStart;
            }
        }
        System.out.println("impossible condition - sprinklers count less than field size (findNextSprinklerRowOffset)");
        return 0;
    }

    private int findNextEmptyRowOffset(int rowToStart) {
        for (int row = rowToStart + 1; row < fieldSize + 1; row++) {
            if (rows[row].isEmpty()) {
                return row - rowToStart;
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
