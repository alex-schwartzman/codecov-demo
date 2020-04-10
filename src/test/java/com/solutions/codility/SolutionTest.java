package com.solutions.codility;

import org.junit.Test;

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
}
