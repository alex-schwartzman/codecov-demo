package com.solutions.codility;

import java.util.ArrayList;

public class Solution {

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

    byte[][] knownDirectionsMap;
    static final byte DOWN = 1;
    static final byte RIGHT = 2;

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
        return toString(solution(A, 0, 0).path);
    }

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
