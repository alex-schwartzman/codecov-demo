package com.solutions.codility;

import org.junit.Test;

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
        assertEquals(2, new Solution().solution(new int[][]{{0,0,0,0,0,0},{0,0,0,0,0,0},{0,1,0,0,0,0},{0,1,1,0,0,0},{0,1,1,1,0,0}}));
    }

    @Test
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
        B[2]=A[2];
        assertEquals(1, new Solution().solution(new int[][]{A, B}));
    }

}
