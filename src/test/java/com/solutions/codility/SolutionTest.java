package com.solutions.codility;

import org.junit.Test;

import java.util.Random;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void testAsDescribedInTask1() {
        assertEquals(10, new Solution().solution(new int[]{3, 1, 4}));
    }

    @Test
    public void testAsDescribedInTask2() {
        assertEquals(17, new Solution().solution(new int[]{5, 3, 2, 4}));
    }

    @Test
    public void testAsDescribedInTask3() {
        assertEquals(19, new Solution().solution(new int[]{5, 3, 5, 2, 1}));
    }

    @Test
    public void testAsDescribedInTask4() {
        assertEquals(35, new Solution().solution(new int[]{7, 7, 3, 7, 7}));
    }

    @Test
    public void testAsDescribedInTask5() {
        assertEquals(30, new Solution().solution(new int[]{1, 1, 7, 6, 6, 6}));
    }

    @Test
    public void performanceTest100() {
        int[] input = new Random(42).ints(100, 1, 10000).toArray();
        assertEquals(989177, new Solution().solution(input));
    }

    @Test
    public void performanceTest1000() {
        int[] input = new Random(42).ints(1000, 1, 10000).toArray();
        assertEquals(9980960, new Solution().solution(input));
    }

    @Test
    public void performanceTest10000() {
        int[] input = new Random(42).ints(10000, 1, 10000).toArray();
        assertEquals(99954540, new Solution().solution(input));
    }

    @Test
    public void performanceTest100000() {
        int[] input = new Random(42).ints(100000, 1, 10000).toArray();
        assertEquals(999887758, new Solution().solution(input));
    }

    @Test
    public void twoBuildingsTest() {
        assertEquals(3, new Solution().solution(new int[]{2, 1}));
    }

}
