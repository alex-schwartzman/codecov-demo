package com.solutions.codility;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void testAsDescribedInTask(){
        assertEquals(5, new Solution().solution(new int[]{1, 3, 6, 4, 1, 2}));
        assertEquals(4, new Solution().solution(new int[]{1,2,3}));
        assertEquals(1, new Solution().solution(new int[]{-1,-3}));
     }

    @Test
    public void testOnBiggerArrays(){
        assertEquals(5, new Solution().solution(new int[]{1,2,3,4}));
    }

}
