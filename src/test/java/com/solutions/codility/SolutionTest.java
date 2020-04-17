package com.solutions.codility;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void testAsDescribedInTask1() {
        assertEquals(10, new Solution().solution(new int[]{4, 2, 1}, new int[]{2, 5, 3}, 2));
    }

    @Test
    public void testAsDescribedInTask2() {
        assertEquals(18, new Solution().solution(new int[]{7, 1, 4, 4}, new int[]{5, 3, 4, 3}, 2));
    }

    @Test
    public void testAsDescribedInTask3() {
        assertEquals(15, new Solution().solution(new int[]{5, 5, 5}, new int[]{5, 5, 5}, 1));
    }

    @Test
    @Ignore
    public void testOnBiggerArrayInBeginning() {
        final int size = 500000;
        int[] A = new int[size];
        int[] B = new int[size];
        for (int i = 0; i < A.length; i++) {
            A[i] = (A.length - i) % 1000;
            B[i] = (i) % 1000;
        }
        assertEquals(2, new Solution().solution(A, B, size / 2));
    }
}
