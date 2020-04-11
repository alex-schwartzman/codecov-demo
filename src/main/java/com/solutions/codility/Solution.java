package com.solutions.codility;

import java.util.ArrayList;

public class Solution {

    static class Result {
        public ArrayList<Integer> path = new ArrayList<>();
        public int sum = 0;

        public Result append(int i) {
            path.add(i);
            sum += i;
            return this;
        }
    }

    public String solution(int[][] A) {
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
        Result rightOption = null;
        Result downOption = null;
        if (A.length - 1 > row) {
            //excercise down option - next row
            downOption = solution(A, row + 1, column);
        }
        if (A[row].length - 1 > column) {
            //excercise right option - next item in the same row (or we say next column)
            rightOption = solution(A, row, column + 1);
        }

        Result optimalResult = null;

        if (rightOption == null && downOption == null) {
            optimalResult = new Result();
        } else if (rightOption == null) {
            optimalResult = downOption;
        } else if (downOption == null) {
            optimalResult = rightOption;
        } else {
            optimalResult = rightOption.sum > downOption.sum ? rightOption : downOption;
        }
        return optimalResult.append(A[row][column]);
    }


}
