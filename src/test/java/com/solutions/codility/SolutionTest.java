package com.solutions.codility;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void testAsDescribedInTask1() {
        assertEquals(2, new Solution().solution(new int[]{1, 1, 3, 4, 3, 3, 4}, 2));
    }

    @Test
    public void testAsDescribedInTask2() {
        assertEquals(2, new Solution().solution(new int[]{4, 5, 5, 4, 2, 2, 4}, 0));
    }

    @Test
    public void testAsDescribedInTask3() {
        assertEquals(4, new Solution().solution(new int[]{1, 3, 3, 2}, 2));
    }

    @Test
    public void testOnBiggerContinuousArray() {
        final int size = 100;
        int[] A = new int[size];
        for (int i = 0; i < A.length; i++) {
            A[i] = A.length - i;
        }
        assertEquals(6, new Solution().solution(A, 5));
    }

}
