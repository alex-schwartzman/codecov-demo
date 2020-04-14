package com.solutions.codility;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void testAsDescribedInTask1() {
        assertEquals(5, new Solution().solution(new int[]{1, 1, 3, 4, 3, 3, 4}, 2));
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

    @Test
    public void testOnBiggerRandomArray() {
        final int size = 100;
        int[] A = new int[size];
        Random generator = new Random(42);
        for (int i = 0; i < A.length; i++) {
            A[i] = generator.nextInt(size / 2) + 1;
        }
        assertEquals(8, new Solution().solution(A, 5));
    }

    @Test
    public void testOn1kRandomArray() {
        final int size = 1000;
        int[] A = new int[size];
        Random generator = new Random(42);
        for (int i = 0; i < A.length; i++) {
            A[i] = generator.nextInt(size / 2) + 1;
        }
        assertEquals(7, new Solution().solution(A, 5));
    }

    @Test
    public void testOn10kRandomArray() {
        final int size = 10000;
        int[] A = new int[size];
        Random generator = new Random(42);
        for (int i = 0; i < A.length; i++) {
            A[i] = generator.nextInt(size / 2) + 1;
        }
        assertEquals(53, new Solution().solution(A, 50));
    }

    @Test
    @Ignore //for performance reasons :)
    public void testOn100kRandomArray() {
        final int size = 100000;
        int[] A = new int[size];
        Random generator = new Random(42);
        for (int i = 0; i < A.length; i++) {
            A[i] = generator.nextInt(size / 2) + 1;
        }
        assertEquals(7, new Solution().solution(A, 500));
    }

    @Test
    public void testOnBiggerOddEvenArray() {
        final int size = 100;
        int[] A = new int[size];
        for (int i = 0; i < A.length; i++) {
            A[i] = i % 2 == 0 ? 1 : 2;
        }
        assertEquals(95, new Solution().solution(A, size / 2 - 3));
    }

    @Test
    public void testOn1kOddEvenArray() {
        final int size = 10000;
        int[] A = new int[size];
        for (int i = 0; i < A.length; i++) {
            A[i] = i % 2 == 0 ? 1 : 2;
        }
        assertEquals(9995, new Solution().solution(A, size / 2 - 3));
    }

    @Test
    public void testOn10kOddEvenArray() {
        final int size = 100000;
        int[] A = new int[size];
        for (int i = 0; i < A.length; i++) {
            A[i] = i % 2 == 0 ? 1 : 2;
        }
        assertEquals(99995, new Solution().solution(A, size / 2 - 3));
    }


    @Test
    @Ignore //for performance reasons :)
    public void testOn30kOddEvenArray() {
        final int size = 300000;
        int[] A = new int[size];
        for (int i = 0; i < A.length; i++) {
            A[i] = i % 2 == 0 ? 1 : 2;
        }
        assertEquals(99995, new Solution().solution(A, size / 2 - 3));
    }

    @Test
    public void testOn1kContinuousArray() {
        final int size = 1000;
        int[] A = new int[size];
        for (int i = 0; i < A.length; i++) {
            A[i] = A.length - i;
        }
        assertEquals(6, new Solution().solution(A, 5));
    }

    @Test
    public void testOn10kContinuousArray() {
        final int size = 10000;
        int[] A = new int[size];
        for (int i = 0; i < A.length; i++) {
            A[i] = A.length - i;
        }
        assertEquals(6, new Solution().solution(A, 5));
    }


    @Test
    @Ignore //for performance reasons :)
    public void testOn100kContinuousArray() {
        final int size = 100000;
        int[] A = new int[size];
        for (int i = 0; i < A.length; i++) {
            A[i] = A.length - i;
        }
        assertEquals(6, new Solution().solution(A, 5));
    }

}
