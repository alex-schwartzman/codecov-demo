package com.solutions.codility;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static class Turtle {
        public int row, column;

        public Turtle(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public Turtle step() {
            Turtle result = null;
            if (column < width - 1) {
                //Go right, and mark that setReturnPathWaySign would be LEFT
                if (getReturnPathWaySign(row, column + 1) == UNDEFINED) {
                    result = new Turtle(row, column + 1);
                    result.setReturnPathWaySign(LEFT);
                }
            }

            if (row < height - 1) {
                row++;
                if (getReturnPathWaySign() == UNDEFINED) { //need to check for UNDEFINED, just for the case if some converging pathways yield the same number - we don't need to keep them both in this case
                    setReturnPathWaySign(UP);
                } else {
                    invalidate();
                }
            } else {
                invalidate();
            }

            return result;
        }

        private void invalidate() {
            row = -1;
        }

        public boolean isValid() {
            return row > 0;
        }

        private int getValue(int[][] a) {
            return a[row][column];
        }

        private void setReturnPathWaySign(byte direction) {
            returnPathwayMap[row][column] = direction;
        }

        private byte getReturnPathWaySign() {
            return returnPathwayMap[row][column];
        }

        private byte getReturnPathWaySign(int r, int c) {
            return returnPathwayMap[r][c];
        }
    }

    private static int width;
    private static int height;

    static byte[][] returnPathwayMap;
    static final byte UP = 3;
    static final byte LEFT = 4;
    static final byte UNDEFINED = 0;

    public String solution(int[][] A) {
        width = A[0].length;
        height = A.length;
        returnPathwayMap = new byte[height][width];

        List<Turtle> allActiveTurtles = new ArrayList<>();
        List<Turtle> nextGenerationTurtlesList = new ArrayList<>();
        allActiveTurtles.add(new Turtle(0, 0));
        int allActiveMaxValue = A[0][0];
        while (!allActiveTurtles.isEmpty()) {
            int maxValue = 0;
            for (Turtle t :
                    allActiveTurtles) {
                if (t.getValue(A) < allActiveMaxValue) {
                    //filter all active nodes on-the-fly, without saving them into collection
                    continue;
                }
                Turtle childNextGen = t.step();
                if (t.isValid()) {
                    maxValue = Math.max(t.getValue(A), maxValue);
                    nextGenerationTurtlesList.add(t);
                }
                if (childNextGen != null) {
                    maxValue = Math.max(childNextGen.getValue(A), maxValue);
                    nextGenerationTurtlesList.add(childNextGen);
                }
            }
            //swap two collections instead of deleting and re-creating, to avoid GC intervention
            List<Turtle> tmpList = allActiveTurtles;
            allActiveTurtles = nextGenerationTurtlesList;
            nextGenerationTurtlesList = tmpList;

            //after swap, don't forget to clean the foundation for next generation
            nextGenerationTurtlesList.clear();
            allActiveMaxValue = maxValue;
        }

        StringBuilder b = new StringBuilder();
        int row = height - 1;
        int column = width - 1;
        //traverse backwards, following arrows
        byte direction;
        while ((direction = returnPathwayMap[row][column]) != UNDEFINED) {
            b.append(A[row][column]);
            if (direction == LEFT) {
                column--;
            }
            if (direction == UP) {
                row--;
            }
        }
        b.append(A[row][column]);
        return b.reverse().toString();
    }
}
