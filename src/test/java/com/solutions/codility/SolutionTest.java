package com.solutions.codility;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void testAsDescribedInTask1() {
        assertEquals(3, new Solution().solution("abaacbca", 2));
    }

    @Test
    public void testAsDescribedInTask2() {
        assertEquals(5, new Solution().solution("aabcabc", 1));
    }

    @Test
    public void testAsDescribedInTask3() {
        assertEquals(1, new Solution().solution("zaaaa", 1));
    }

    @Test
    public void testAsDescribedInTask4() {
        assertEquals(-1, new Solution().solution("aaaa", 2));
    }
}
