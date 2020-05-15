package com.solutions.codility;

import java.util.HashSet;
import java.util.Objects;

//Find the number of steps for the knight to travel on the board from (x0,y0) to (x1,y1)
public class Solution {

    //naiive solution, to double-check the tests
    public int solution1(int x0, int y0, int x1, int y1) {
        int deltaX = Math.abs(x1 - x0);
        int deltaY = Math.abs(y1 - y0);
        if (deltaY > deltaX) {
            //swap
            int tmp = deltaX;
            deltaX = deltaY;
            deltaY = tmp;
        }
        return goToZeroFrom(deltaX, deltaY, 10, new HashSet<>());
    }

    public int solution(int x0, int y0, int x1, int y1) {
        int deltaX = Math.abs(x1 - x0);
        int deltaY = Math.abs(y1 - y0);
        if (deltaY > deltaX) {
            //swap
            int tmp = deltaX;
            deltaX = deltaY;
            deltaY = tmp;
        }

        int horizontalShift = 0;
        if (deltaX > 2 * deltaY) {
            //we may drift left by a two-step maneuver (2,1)+(2,-1)
            //every such maneuver of (+4,0) shift of start, add 2 steps to horizontalShift
            //the amount of this shifts should be minimal
            //all the math should be discrete with granularity 4 here, so get ready for an unreadable integer math.

            int missingShiftLeft = deltaX - deltaY * 2;
            if (missingShiftLeft % 4 != 0) {
                missingShiftLeft += 4;
            }
            int maneuversCount = missingShiftLeft / 4;
            horizontalShift = maneuversCount * 2;
            deltaX -= maneuversCount * 4;
        }

        int totalPath = deltaX + deltaY;

        if (totalPath % 3 == 0) {
            return horizontalShift + totalPath / 3;
        }

        int skipRecursionForSteps = totalPath / 3 - 2;
        int newDeltaX = totalPath % 3 + 3;
        int newDeltaY = 3;

        return horizontalShift + skipRecursionForSteps + goToZeroFrom(newDeltaX, newDeltaY, 6, new HashSet<>());
    }

    int[][] moves = new int[][]{{-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}, {-2, 1}};

    public static class Coord {
        public final int x;
        public final int y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Coord)) return false;
            Coord coord = (Coord) o;
            return x == coord.x &&
                    y == coord.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public int goToZeroFrom(int x, int y, int recursionLimit, HashSet<Coord> visitedList) {
        Coord currentPosition = new Coord(x, y);
        if (x + y > recursionLimit * 3 //strip off all wanders in the wrong direction (4^recursionLimit speed-up)
                || visitedList.contains(currentPosition) //skip all cyclical wanders
                || recursionLimit == 0) {
            return Integer.MAX_VALUE;
        }
        if (x == 0 && y == 0) {
            return 0;
        }

        visitedList.add(currentPosition);
        int result = Integer.MAX_VALUE;
        for (int[] move : moves) {
            result = Math.min(result, goToZeroFrom(x + move[0], y + move[1], recursionLimit - 1, visitedList));
        }

        visitedList.remove(currentPosition);

        if (result == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        } else {
            return result + 1;
        }
    }
}
