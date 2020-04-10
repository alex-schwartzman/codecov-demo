package com.solutions.codility;

import java.util.*;

//https://app.codility.com/programmers/task/sprinklers_arrangement/
//Solution turns out to be N^2
public class Solution {
    public static class Coord {
        public int column, row;

        public Coord(int column, int row) {
            this.column = column;
            this.row = row;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Coord)) return false;
            Coord coord = (Coord) o;
            return column == coord.column &&
                    row == coord.row;
        }

        @Override
        public int hashCode() {
            return Objects.hash(column, row);
        }
    }

    int stepsCount = 0;
    Map<Integer, Set<Integer>> rows = new HashMap<>();
    Map<Integer, Set<Integer>> columns = new HashMap<>();
    int fieldSize;

    public int solution(int[] X, int[] Y) {
        fieldSize = X.length;

        initColumnsRows(fieldSize);

        for (int i = 0; i < fieldSize; i++) {
            addSprinkler(new Coord(X[i] - 1, Y[i] - 1));
        }

        //walk through columns and move them left and right to get even distribution
        for (int x = 0; x < fieldSize; x++) {
            Set<Integer> currentColumn = columns.get(x);
            while (currentColumn.size() != 1) {
                if (currentColumn.size() > 1) {
                    Coord sprinklerToBeMoved = new Coord(x, currentColumn.iterator().next()); //?
                    int nextEmptyColumnOffset = findNextEmptyColumnOffset(x);
                    moveSprinklerRight(sprinklerToBeMoved, nextEmptyColumnOffset);
                    stepsCount += nextEmptyColumnOffset;
                } else {
                    int nextSprinklerColumnOffset = findNextSprinklerColumnOffset(x);
                    int row = columns.get(x + nextSprinklerColumnOffset).iterator().next();
                    Coord from = new Coord(x + nextSprinklerColumnOffset, row);
                    moveSprinklerRight(from, -nextSprinklerColumnOffset);
                    stepsCount += nextSprinklerColumnOffset;
                }
            }
        }

        //walk through rows and move them left and right to get even distribution
        for (int y = 0; y < fieldSize; y++) {
            Set<Integer> currentRow = rows.get(y);
            while (currentRow.size() != 1) {
                if (currentRow.size() > 1) {
                    Coord sprinklerToBeMoved = new Coord(currentRow.iterator().next(), y); //?
                    int nextEmptyRowOffset = findNextEmptyRowOffset(y);
                    moveSprinklerDown(sprinklerToBeMoved, nextEmptyRowOffset);
                    stepsCount += nextEmptyRowOffset;
                } else {
                    int nextSprinklerRowOffset = findNextSprinklerRowOffset(y);
                    int column = rows.get(y + nextSprinklerRowOffset).iterator().next();
                    Coord from = new Coord(column, y + nextSprinklerRowOffset);
                    moveSprinklerDown(from, -nextSprinklerRowOffset);
                    stepsCount += nextSprinklerRowOffset;
                }
            }
        }

        return stepsCount;
    }

    private int findNextSprinklerColumnOffset(int columnToStart) {
        for (int column = columnToStart + 1; column < fieldSize + 1; column++) {
            if (columns.get(column).size() > 1) {
                return column - columnToStart;
            }
        }
        System.out.println("impossible condition - sprinklers count less than field size (findNextSprinklerColumnOffset)");
        return 0;
    }

    private int findNextEmptyColumnOffset(int columnToStart) {
        for (int column = columnToStart + 1; column < fieldSize + 1; column++) {
            if (columns.get(column).isEmpty()) {
                return column - columnToStart;
            }
        }
        System.out.println("impossible condition - sprinklers count less than field size (findNextEmptyColumnOffset)");
        return fieldSize;
    }

    private int findNextSprinklerRowOffset(int rowToStart) {
        for (int row = rowToStart + 1; row < fieldSize + 1; row++) {
            if (rows.get(row).size() > 1) {
                return row - rowToStart;
            }
        }
        System.out.println("impossible condition - sprinklers count less than field size (findNextSprinklerRowOffset)");
        return 0;
    }

    private int findNextEmptyRowOffset(int rowToStart) {
        for (int row = rowToStart + 1; row < fieldSize + 1; row++) {
            if (rows.get(row).isEmpty()) {
                return row - rowToStart;
            }
        }
        System.out.println("impossible condition - sprinklers count less than field size (findNextEmptyRowOffset)");
        return 0;
    }

    private void initColumnsRows(int fieldSize) {
        for (int i = 0; i < fieldSize + 1; i++) {
            columns.put(i, new HashSet<>());
            rows.put(i, new HashSet<>());
        }
    }

    private void addSprinkler(Coord coord) {
        columns.get(coord.column).add(coord.row);
        rows.get(coord.row).add(coord.column);
    }

    private void removeSprinkler(Coord coord) {
        columns.get(coord.column).remove(coord.row);
        rows.get(coord.row).remove(coord.column);
    }


    private void moveSprinkler(Coord from, Coord to) {
        removeSprinkler(from);
        addSprinkler(to);
    }

    private void moveSprinklerDown(Coord from, int count) {
        Coord to = new Coord(from.column, from.row + count);
        moveSprinkler(from, to);
    }

    private void moveSprinklerRight(Coord from, int count) {
        Coord to = new Coord(from.column + count, from.row);
        moveSprinkler(from, to);
    }

}
