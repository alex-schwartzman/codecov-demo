package com.solutions.codility;

import javafx.util.Pair;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class SolutionTest {
    @Test
    public void testZeroMoves() {
        assertEquals(0, new Solution().solution(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    public void testZeroMoves100000() {
        final int size = 100000;
        int[] X = new int[size];
        int[] Y = new int[size];
        int sprinklersCount = 0;
        for (int x = 0; x < size; x++) {
            X[sprinklersCount] = x + 1;
            Y[sprinklersCount] = x + 1;
            sprinklersCount++;
        }
        assertEquals(0, new Solution().solution(X, Y));
    }

    @Test
    public void testAsDescribedInTask1() {
        assertEquals(5, new Solution().solution(new int[]{1, 2, 2, 3, 4}, new int[]{1, 1, 4, 5, 4}));
    }

    @Test
    public void testAsDescribedInTask2() {
        assertEquals(6, new Solution().solution(new int[]{1, 1, 1, 1}, new int[]{1, 2, 3, 4}));
    }

    @Test
    public void testColumnsOnlyRightToLeft() {
        assertEquals(6, new Solution().solution(new int[]{4, 4, 4, 4}, new int[]{1, 2, 3, 4}));
    }

    @Test
    public void testColumnsOnlyBottomToTop() {
        assertEquals(6, new Solution().solution(new int[]{1, 2, 3, 4}, new int[]{4, 4, 4, 4}));
    }


    @Test
    public void testColumnsCenter() {
        assertEquals(6, new Solution().solution(new int[]{3, 3, 3, 3, 3}, new int[]{1, 2, 3, 4, 5}));
    }


    @Test
    public void testAsDescribedInTask3() {
        assertEquals(5, new Solution().solution(new int[]{1, 2, 2, 3, 4}, new int[]{1, 1, 4, 5, 4}));
    }

    @Test
    public void testPerformanceOnRandomField100() {
        final int size = 100;
        int[] X = new int[size];
        int[] Y = new int[size];
        Set<Pair<Integer, Integer>> sprinklers = new HashSet<>();
        Random generator = new Random(42);
        int sprinklersCount = 0;
        while (sprinklersCount < size) {
            Pair<Integer, Integer> coord = new Pair<>(generator.nextInt(size) + 1, generator.nextInt(size) + 1);
            if (sprinklers.add(coord)) {
                X[sprinklersCount] = coord.getKey();
                Y[sprinklersCount] = coord.getValue();
                sprinklersCount++;
            }
        }
        assertEquals(549, new Solution().solution(X, Y));
    }

    @Test
    public void testPerformanceOnRandomField100000() {
        final int size = 100000;
        int[] X = new int[size];
        int[] Y = new int[size];
        Set<Pair<Integer, Integer>> sprinklers = new HashSet<>();
        Random generator = new Random(42);
        int sprinklersCount = 0;
        while (sprinklersCount < size) {
            Pair<Integer, Integer> coord = new Pair<>(generator.nextInt(size) + 1, generator.nextInt(size) + 1);
            if (sprinklers.add(coord)) {
                X[sprinklersCount] = coord.getKey();
                Y[sprinklersCount] = coord.getValue();
                sprinklersCount++;
            }
        }
        assertEquals(29356034, new Solution().solution(X, Y));
    }

    @Test
    public void testPerformanceAllInOneSpot2() {
        final int spotSize = 2;
        final int size = spotSize * spotSize;
        int[] X = new int[size];
        int[] Y = new int[size];
        int sprinklersCount = 0;
        for (int x = 0; x < spotSize; x++) {
            for (int y = 0; y < spotSize; y++) {
                X[sprinklersCount] = x + 1;
                Y[sprinklersCount] = y + 1;
                sprinklersCount++;
            }
        }
        assertEquals(8, new Solution().solution(X, Y));
    }


    @Test
    public void testPerformanceAllInOneSpot3() {
        final int spotSize = 3;
        final int size = spotSize * spotSize;
        int[] X = new int[size];
        int[] Y = new int[size];
        int sprinklersCount = 0;
        for (int x = 0; x < spotSize; x++) {
            for (int y = 0; y < spotSize; y++) {
                X[sprinklersCount] = x + 1;
                Y[sprinklersCount] = y + 1;
                sprinklersCount++;
            }
        }
        assertEquals(54, new Solution().solution(X, Y));
    }

    @Test
    public void testPerformanceAllInOppositeSpot3() {
        final int spotSize = 3;
        final int size = spotSize * spotSize;
        int[] X = new int[size];
        int[] Y = new int[size];
        int sprinklersCount = 0;
        for (int x = size-spotSize; x < size; x++) {
            for (int y = size-spotSize; y < size; y++) {
                X[sprinklersCount] = x + 1;
                Y[sprinklersCount] = y + 1;
                sprinklersCount++;
            }
        }
        assertEquals(54, new Solution().solution(X, Y));
    }

    @Test
    public void testPerformanceAllInOppositeSpot2() {
        final int spotSize = 2;
        final int size = spotSize * spotSize;
        int[] X = new int[size];
        int[] Y = new int[size];
        int sprinklersCount = 0;
        for (int x = size-spotSize; x < size; x++) {
            for (int y = size-spotSize; y < size; y++) {
                X[sprinklersCount] = x + 1;
                Y[sprinklersCount] = y + 1;
                sprinklersCount++;
            }
        }
        assertEquals(8, new Solution().solution(X, Y));
    }


}
