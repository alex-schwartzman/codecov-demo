package com.solutions.codility;

import java.util.Arrays;

//https://app.codility.com/programmers/task/flipping_matrix/
//A matrix of binary values is given. We can flip the values in selected columns. What is the maximum number of rows that we can obtain that contain all the same values?
//no idea
public class Solution {
    int[][] A;
    int[] histogram;
    private int result;
    private int rowLength;

    public int solution(int[][] A) {
        if (A.length <= 1) {
            return A.length;
        }
        if (A[0].length <= 1) {
            return A.length;
        }

        this.A = A;
        histogram = new int[A.length];
        rowLength = A[0].length;

        for (int rowNumber = 0; rowNumber < A.length; rowNumber++) {
            for (int c : A[rowNumber]) {
                histogram[rowNumber] += c;
            }
        }

        int column = 0;
        compareColumnOptions(column, histogram);

        return result;
    }

    private void compareColumnOptions(int column, int[] histogram) {
        if (column == rowLength) {
            result = Math.max(result, countFullRows(histogram));
            return;
        }
        compareColumnOptions(column + 1, histogram); //pass histogram unchanged to the next step
        int[] antiHistogram = Arrays.copyOf(histogram, histogram.length);
        for (int rowNumber = 0; rowNumber < A.length; rowNumber++) {
            if (A[rowNumber][column] == 1) {
                antiHistogram[rowNumber]--;
            } else {
                antiHistogram[rowNumber]++;
            }
        }
        compareColumnOptions(column + 1, antiHistogram); //pass alternative histogram to the next step
    }

    private int countFullRows(int[] histogram) {
        int counter = 0;
        for (int v : histogram) {
            if (v == rowLength || v == 0) {
                counter++;
            }
        }
        return counter;
    }
}

