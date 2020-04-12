package com.solutions.codility;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

    public class Turtle {
        public int row, column;

        public Turtle(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public ArrayList<Turtle> step() {
            ArrayList<Turtle> result = new ArrayList<>(2);
            if (row < height - 1) {
                //Go down, and mark that setReturnPathWaySign would be UP
                Turtle t = new Turtle(row + 1, column);
                if (t.getReturnPathWaySign() == UNDEFINED) { //need to check for UNDEFINED, just for the case if some converging pathways yield the same number - we don't need to keep them both in this case
                    t.setReturnPathWaySign(UP);
                    result.add(t);
                }
            }
            if (column < width - 1) {
                //Go right, and mark that setReturnPathWaySign would be LEFT
                Turtle t = new Turtle(row, column + 1);
                if (t.getReturnPathWaySign() == UNDEFINED) {
                    t.setReturnPathWaySign(LEFT);
                    result.add(t);
                }
            }
            return result;
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
    }

    private int width;
    private int height;

    byte[][] returnPathwayMap;
    static final byte UP = 3;
    static final byte LEFT = 4;
    static final byte UNDEFINED = 0;

    public String solution(int[][] A) {
        width = A[0].length;
        height = A.length;
        returnPathwayMap = new byte[height][width];

        List<Turtle> allActiveTurtles = new ArrayList<Turtle>();
        allActiveTurtles.add(new Turtle(0, 0));
        while (!allActiveTurtles.isEmpty()) {
            ArrayList<Turtle> nextGenerationTurtlesList = new ArrayList<>();
            nextGenerationTurtlesList.ensureCapacity(allActiveTurtles.size() * 2);
            int maxValue = 0;
            for (Turtle t :
                    allActiveTurtles) {
                for (Turtle childNextGen : t.step()) {
                    maxValue = Math.max(childNextGen.getValue(A), maxValue);
                    nextGenerationTurtlesList.add(childNextGen);
                }
            }
            final int finalMaxValue = maxValue;
            allActiveTurtles = nextGenerationTurtlesList.stream().filter(t->t.getValue(A)== finalMaxValue).collect(Collectors.toList());
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
