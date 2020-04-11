package com.solutions.codility;

import javafx.util.Pair;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void oneSizedArray() {
        assertEquals("6", new Solution().solution(new int[][]{{6}}));
    }

    @Test
    public void oneDimensionalArray() {
        assertEquals("123456789", new Solution().solution(new int[][]{{1, 2, 3, 4, 5, 6, 7, 8, 9}}));
    }

    @Test
    public void twoRowsArray() {
        assertEquals("1234567899", new Solution().solution(new int[][]{{1, 2, 3, 4, 5, 6, 7, 8, 9}, {1, 2, 3, 4, 5, 6, 7, 8, 9}}));
    }

    @Test
    public void testAsDescribedInTask1() {
        assertEquals("997952", new Solution().solution(new int[][]{{9, 9, 7}, {9, 7, 2}, {6, 9, 5}, {9, 1, 2}}));
    }

    @Test
    public void testPerformanceOnRandomField100x100() {
        final int size = 100;
        int[][] A = new int[size][size];
        Random generator = new Random(42);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                A[x][y] = generator.nextInt(9) + 1;
            }
        }
        assertEquals("Hello World", new Solution().solution(A));
    }

    @Test
    public void testPerformanceOnRandomField10x10() {
        final int size = 10;
        int[][] A = new int[size][size];
        Random generator = new Random(42);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                A[x][y] = generator.nextInt(9) + 1;
            }
        }
        assertEquals("9497976869986774795", new Solution().solution(A));
    }

    @Test
    public void testPerformanceOnRandomField30x30() {
        final int size = 30;
        int[][] A = new int[size][size];
        Random generator = new Random(42);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                A[x][y] = generator.nextInt(9) + 1;
            }
        }
        assertEquals("Hello World", new Solution().solution(A));
    }


}
