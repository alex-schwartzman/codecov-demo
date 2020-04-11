package com.solutions.codility;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class Solution {

    Deque<Turtle> allUnknownTurtlesPending = new LinkedList<>();

    static class Result {
        public ArrayList<Integer> path = new ArrayList<>();

        public Result append(int i) {
            path.add(i);
            return this;
        }

        public boolean isBiggerOrEqualThan(Result otherPath) {
            if (this.path.size() < otherPath.path.size()) {
                System.out.println("Impossible condition - comparison of paths of different lengths");
                return false;
            }
            for (int i = path.size() - 1; i >= 0; i--) {
                if (this.path.get(i) < otherPath.path.get(i)) {
                    return false;
                }
                if (this.path.get(i) > otherPath.path.get(i)) {
                    return true;
                }
            }
            return true;
        }
    }

    class Turtle {

        int row;
        int column;

        public Turtle(int row, int column) {
            this.row = row;
            this.column = column;
        }

        boolean step() {
            byte direction = getKnownDirection();
            if (direction == RIGHT) {
                column++;
                return true;
            }
            if (direction == DOWN) {
                row++;
                return true;
            }
            return false;
        }

        public boolean isValid() {
            return !(column >= knownDirectionsMap[0].length || row >= knownDirectionsMap.length || isInRightBottomCorner());
        }

        private boolean isInRightBottomCorner() {
            return column == knownDirectionsMap[0].length - 1 && row == knownDirectionsMap.length - 1;
        }

        public byte getKnownDirection() {
            if (column >= knownDirectionsMap[0].length || row >= knownDirectionsMap.length) {
                return UNDEFINED;
            }
            return knownDirectionsMap[row][column];
        }

        public int getValue(int[][] a) {
            return a[row][column];
        }

        public boolean isEqual(Turtle other) {
            return this.column == other.column && this.row == other.row;
        }
    }

    byte[][] knownDirectionsMap;
    static final byte DOWN = 1;
    static final byte RIGHT = 2;
    static final byte UNDEFINED = 0;
    static final byte MARKEDUNKNOWN = 3;

    public String solution(int[][] A) {
        final int width = A[0].length;
        final int height = A.length;
        knownDirectionsMap = new byte[height][width];
        for (int i = 0; i < width - 1; i++) {
            knownDirectionsMap[height - 1][i] = RIGHT;
        }
        for (int i = 0; i < A.length - 1; i++) {
            knownDirectionsMap[i][width - 1] = DOWN;
        }
        for (int row = 0; row < height - 1; row++) {
            for (int column = 0; column < width - 1; column++) {
                int numberOnTheRight = A[row][column + 1];
                int numberDownWards = A[row + 1][column];
                if (numberDownWards != numberOnTheRight) {
                    knownDirectionsMap[row][column] = numberOnTheRight > numberDownWards ? RIGHT : DOWN;
                }
            }
        }

        markAllUnknowns(0, 0);


        while (unknownTurtles.size() > 0) {
            Turtle currentTurtle = unknownTurtles.remove(unknownTurtles.size() - 1);
            Turtle rightTurtle = new Turtle(currentTurtle.row, currentTurtle.column + 1);
            Turtle downTurtle = new Turtle(currentTurtle.row + 1, currentTurtle.column);
            while (rightTurtle.step() && downTurtle.step()) {
                int rightTurtleValue = rightTurtle.getValue(A);
                int downTurtleValue = downTurtle.getValue(A);
                if (rightTurtleValue > downTurtleValue || rightTurtle.isEqual(downTurtle)) {
                    knownDirectionsMap[currentTurtle.row][currentTurtle.column] = RIGHT;
                    break;
                } else if (downTurtleValue > rightTurtleValue) {
                    knownDirectionsMap[currentTurtle.row][currentTurtle.column] = DOWN;
                    break;
                }
            }
        }

        return toString(solution(A, 0, 0).path);
    }

    private void markAllUnknowns(int row, int column) {
        Turtle t = new Turtle(row, column);
        allUnknownTurtlesPending.addLast(t);

        while(!allUnknownTurtlesPending.isEmpty()){
            t = allUnknownTurtlesPending.removeFirst();
            if (!t.isValid()) {
                return;
            }
            while (t.step()) {
            }
            if (t.isValid()) {
                knownDirectionsMap[t.row][t.column] = MARKEDUNKNOWN;
                unknownTurtles.add(t);
                allUnknownTurtlesPending.addLast(new Turtle(t.row + 1, t.column));
                allUnknownTurtlesPending.addLast(new Turtle(t.row, t.column + 1));
            }
        }
    }

    ArrayList<Turtle> unknownTurtles = new ArrayList<>();

    private String toString(ArrayList<Integer> array) {
        StringBuilder b = new StringBuilder();
        for (int i : array) {
            b.append(i);
        }
        return b.reverse().toString();
    }

    private Result solution(int[][] A, int row, int column) {
        if (row == A.length - 1 && column == A[row].length - 1) {
            return new Result().append(A[row][column]);
        }

        byte knownDirection = knownDirectionsMap[row][column];
        if (knownDirection == RIGHT) {
            return solution(A, row, column + 1).append(A[row][column]);
        }
        if (knownDirection == DOWN) {
            return solution(A, row + 1, column).append(A[row][column]);
        }

        Result optimalResult;

        //complicated! - need to calculate both
        Result rightOption = solution(A, row, column + 1);
        Result downOption = solution(A, row + 1, column);
        boolean rightIsBigger = rightOption.isBiggerOrEqualThan(downOption);
        optimalResult = rightIsBigger ? rightOption : downOption;
        knownDirectionsMap[row][column] = rightIsBigger ? RIGHT : DOWN; //don't forget to write it down, in order to not need recalculate again

        return optimalResult.append(A[row][column]);
    }
}
