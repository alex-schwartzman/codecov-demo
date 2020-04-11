package com.solutions.codility;

public class Solution {

    private int width;
    private int height;

    byte[][] knownDirectionsMap;
    static final byte DOWN = 1;
    static final byte RIGHT = 2;
    static final byte UNDEFINED = 0;

    public String solution(int[][] A) {
        width = A[0].length;
        height = A.length;
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

        while (knownDirectionsMap[height - 1][width - 1] == UNDEFINED) {
            turtleRace(A, 0, 0);
        }
        StringBuilder b = new StringBuilder();
        int row = 0;
        int column = 0;
        //small race
        knownDirectionsMap[height - 1][width - 1] = 0;
        while (knownDirectionsMap[row][column] != UNDEFINED) {
            b.append(A[row][column]);
            byte direction = knownDirectionsMap[row][column];
            if (direction == RIGHT) {
                column++;
            }
            if (direction == DOWN) {
                row++;
            }
        }
        b.append(A[row][column]);
        return b.toString();
    }

    private void turtleRace(int[][] A, int row, int column) {
        while (knownDirectionsMap[row][column] != UNDEFINED) {
            byte direction = knownDirectionsMap[row][column];
            if (direction == RIGHT) {
                column++;
            }
            if (direction == DOWN) {
                row++;
            }
        }

        int rightTurtleRow = row;
        int rightTurtleColumn = column + 1;
        int downTurtleRow = row + 1;
        int downTurtleColumn = column;
        while (turtleValid(A, rightTurtleRow, rightTurtleColumn) && turtleValid(A, downTurtleRow, downTurtleColumn)) {
            if (rightTurtleColumn == downTurtleColumn && rightTurtleRow == downTurtleRow) {
                //turtles collided - whe may choose whichever and quit;
                knownDirectionsMap[row][column] = DOWN;
                return;
            }
            int rightTurtleValue = A[rightTurtleRow][rightTurtleColumn];
            int downTurtleValue = A[downTurtleRow][downTurtleColumn];
            if (rightTurtleValue > downTurtleValue) {
                knownDirectionsMap[row][column] = RIGHT;
                return;
            }
            if (rightTurtleValue < downTurtleValue) {
                knownDirectionsMap[row][column] = DOWN;
                return;
            }

            byte rightTurtleDirection = knownDirectionsMap[rightTurtleRow][rightTurtleColumn];
            if (rightTurtleDirection == UNDEFINED) {
                //recursion to figure out where to go
                turtleRace(A, rightTurtleRow, rightTurtleColumn);
                rightTurtleDirection = knownDirectionsMap[rightTurtleRow][rightTurtleColumn];
            }
            //and after recursion - it should be clear;
            if (rightTurtleDirection == RIGHT) {
                rightTurtleColumn++;
            } else if (rightTurtleDirection == DOWN) {
                rightTurtleRow++;
            }

            byte downTurtleDirection = knownDirectionsMap[downTurtleRow][downTurtleColumn];
            if (downTurtleDirection == UNDEFINED) {
                //recursion to figure out where to go
                turtleRace(A, downTurtleRow, downTurtleColumn);
                downTurtleDirection = knownDirectionsMap[downTurtleRow][downTurtleColumn];
            }
            //and after recursion - it should be clear;
            if (downTurtleDirection == RIGHT) {
                downTurtleColumn++;
            } else if (downTurtleDirection == DOWN) {
                downTurtleRow++;
            }
        }
        //both became invalid (reached bottom right corner), so it does not matter anymore
        knownDirectionsMap[row][column] = DOWN;
    }

    private boolean turtleValid(int[][] A, int t1row, int t1column) {
        if (t1row > height - 1 || t1column > width - 1) {
            return false;
        }
        return true;
    }
}
