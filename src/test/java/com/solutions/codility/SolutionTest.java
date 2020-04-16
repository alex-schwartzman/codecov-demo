package com.solutions.codility;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void testAsDescribedInTask1() {
        Assert.assertArrayEquals(new int[]{2, 3}, new Solution().solution(3, 5, new int[]{2, 1, 3, 1, 2, 2, 3}));
    }

    @Test
    public void testAsDescribedInTask2() {
        Assert.assertArrayEquals(new int[]{2, 3}, new Solution().solution(4, 2, new int[]{1, 2, 2, 1, 2}));
    }

    @Test
    public void testOnBiggerArray() {
        final int size = 100000;
        int[] A = new int[size];
        for (int i = 0; i < A.length; i++) {
            A[i] = A.length - i;
        }

        assertEquals(0, new Solution().solution(5, size, A).length);
    }
}
