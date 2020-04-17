package com.solutions.codility;

//https://app.codility.com/programmers/task/flipping_matrix/
//A matrix of binary values is given. We can flip the values in selected columns. What is the maximum number of rows that we can obtain that contain all the same values?
//idea is that we go column-by-column and match only the rows which stayed alive after previous iterations. That's thanks to reversibility of XOR 0xFF
public class Solution {
    public int solution(int[][] A) {
        if (A.length <= 1) {
            return A.length;
        }
        if (A[0].length <= 1) {
            return A.length;
        }

        //this is column with answers, full of zeroes. From now on zero - is the right answer which survived xoring next column
        int[] answers = new int[A.length];

        for (int column = 1; column < A[0].length; column++) {
            int plainMatchCounter = 0;
            int reverseMatchCounter = 0;
            for (int row = 0; row < A.length; row++) {
                if (answers[row] == 0) {
                    if (A[row][column - 1] == A[row][column]) {
                        plainMatchCounter++;
                    } else {
                        reverseMatchCounter++;
                    }
                }
            }

            for (int row = 0; row < A.length; row++) {
                int leftCellValue = A[row][column - 1];
                int rightCellValue = A[row][column];
                if (answers[row] == 0) {
                    if (reverseMatchCounter >= plainMatchCounter) { //at this point things complicate - we need to choose which rows to sacrifice, but we don't know which ones will get masked in the future, and which ones won't
                        rightCellValue = 1 - rightCellValue;
                    }
                    if (answers[row] == 0 && rightCellValue != leftCellValue) {
                        answers[row] = 1; //this row is done and will never be compared anymore, since it fails to form a row up to column number int "column"
                    }
                }
            }
        }

        int result = 0;
        for (int a : answers) {
            if (a == 0) {
                result++;
            }
        }

        return result;
    }
}
