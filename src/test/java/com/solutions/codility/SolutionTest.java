package com.solutions.codility;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void testAsDescribedInTask1() {
        assertEquals(5, new Solution().solution(new int[]{1, 3, 6, 4, 1, 2}));
    }

    @Test
    public void testAsDescribedInTask2() {
        assertEquals(4, new Solution().solution(new int[]{1, 2, 3}));
    }

    @Test
    public void testAsDescribedInTask3() {
        assertEquals(1, new Solution().solution(new int[]{-1, -3}));
    }

    @Test
    public void testOnBiggerContinuousArray() {
        final int size = 100000;
        int[] A = new int[size];
        for (int i = 0; i < A.length; i++) {
            A[i] = A.length - i;
        }
        assertEquals(size + 1, new Solution().solution(A));
    }

    @Test
    public void testOnBiggerLastItem() {
        final int size = 100000;
        int[] A = new int[size];
        for (int i = 0; i < A.length; i++) {
            A[i] = A.length - i;
        }

        int gapIndex = 2;
        int expectedResult = A[gapIndex];
        A[gapIndex] = -1;
        assertEquals(expectedResult, new Solution().solution(A));
    }

    @Test
    public void testOnBiggerArrayAlmostLast() {
        final int size = 100000;
        int[] A = new int[size];
        for (int i = 0; i < A.length; i++) {
            A[i] = A.length - i;
        }
        int gapIndex = size / 4;
        int expectedResult = A[gapIndex];
        A[gapIndex] = -1;
        assertEquals(expectedResult, new Solution().solution(A));
    }

    @Test
    public void testOnBiggerArrayHalfWay() {
        final int size = 100000;
        int[] A = new int[size];
        for (int i = 0; i < A.length; i++) {
            A[i] = A.length - i;
        }

        int gapIndex = size / 2;
        int expectedResult = A[gapIndex];
        A[gapIndex] = -1;
        assertEquals(expectedResult, new Solution().solution(A));

    }

    @Test
    public void testOnBiggerArrayInBeginning() {
        final int size = 100000;
        int[] A = new int[size];
        for (int i = 0; i < A.length; i++) {
            A[i] = A.length - i;
        }

        int gapIndex = 3 * size / 4;
        int expectedResult = A[gapIndex];
        A[gapIndex] = -1;
        assertEquals(expectedResult, new Solution().solution(A));
    }
}
