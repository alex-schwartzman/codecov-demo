package com.solutions.codility;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void testSingleVillage() {
        assertEquals(1, new Solution().solution(new int[]{0}));
    }


    @Test
    public void testAsDescribedInTask1() {
        assertEquals(12, new Solution().solution(new int[]{2, 0, 2, 2, 1, 0}));
    }

    @Test
    public void testTwoVillages() {
        assertEquals(3, new Solution().solution(new int[]{1, 1}));
    }

    @Test
    public void testThreeVillagesInARow() {
        assertEquals(6, new Solution().solution(new int[]{1, 2, 2}));
    }

    @Test
    public void testThreeVillages021() {
        assertEquals(5, new Solution().solution(new int[]{2, 2, 2}));
    }

}
