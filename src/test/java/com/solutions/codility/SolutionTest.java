package com.solutions.codility;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void testAsDescribedInTask1() {
        assertEquals(2, new Solution().solution(new int[][]{{0, 0, 0, 0}, {0, 1, 0, 0}, {1, 0, 1, 1}}));
    }

    @Test
    public void testAsDescribedInTask2() {
        assertEquals(4, new Solution().solution(new int[][]{{0, 1, 0, 1}, {1, 0, 1, 0}, {0, 1, 0, 1}, {1, 0, 1, 0}}));
    }

    @Test
    public void testATrapOfIncorrectMaskChoice() {
        assertEquals(2, new Solution().solution(new int[][]{{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 0}, {0, 1, 1, 0, 0, 0}, {0, 1, 1, 1, 0, 0}}));
    }

    @Test
    @Ignore //will stack-overflow on dumb solution
    public void testOneLiner() {
        final int size = 100000;
        int[] A = new int[size];
        int[] B = new int[size];
        for (int i = 0; i < A.length; i++) {
            A[i] = i % 2;
            B[i] = 1 - i % 2;
        }
        assertEquals(2, new Solution().solution(new int[][]{A, B}));
        assertEquals(1, new Solution().solution(new int[][]{A}));
        B[2] = A[2];
        assertEquals(1, new Solution().solution(new int[][]{A, B}));
    }

    @Test
    public void testPerformanceOnRandomField25x25() {
        final int size = 25;
        int[][] A = new int[size][size];
        Random generator = new Random(42);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                A[x][y] = generator.nextInt(2);
            }
        }
        assertEquals(1, new Solution().solution(A));
    }


    @Test
    public void testPerformanceOnRandomField25x25Max7rows() {
        final int size = 25;
        int[][] A = new int[size][size];
        Random generator = new Random(42);
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if (row % 4 == 0) {
                    A[row][column] = column % 2;
                } else {
                    A[row][column] = generator.nextInt(2);
                }
            }
        }
        assertEquals(7, new Solution().solution(A));
    }

}
