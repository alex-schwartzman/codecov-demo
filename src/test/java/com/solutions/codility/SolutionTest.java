package com.solutions.codility;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void testTrivialZeroSteps() {
        assertEquals(0, new Solution().solution(1, 1, 1, 1));
    }

    @Test
    public void testOnlyRecursiveShort() {
        assertEquals(2, new Solution().solution(1, 1, 1, 3));
    }


    @Test
    public void testTrivialDiagonalTravel() {
        assertEquals(2, new Solution().solution(1, 1, 5, 3));
    }

    @Test
    public void testTrivialHorizontalTravel() {
        assertEquals(2, new Solution().solution(1, 1, 5, 1));
    }

    @Test
    public void testTrivialDiagonalAndHorizontalTravelSimple() {
        assertEquals(4, new Solution().solution(0, 0, 8, 2));
    }

    @Test
    public void testTrivialDiagonalAndHorizontalTravelSimpleX1000() {
        assertEquals(4000, new Solution().solution(0, 0, 8000, 2000));
    }

    @Test
    public void testTrivialDiagonalAndHorizontalTravel2() {
        assertEquals(5, new Solution().solution(0, 0, 9, 4));
    }

    @Test
    public void testTrivialDiagonalAndHorizontalTravelx10() {
        assertEquals(45, new Solution().solution(0, 0, 89, 40));
    }

    @Test
    public void testTrivialDiagonalAndHorizontalTravelx100() {
        assertEquals(450, new Solution().solution(0, 0, 900, 400));
    }

    @Test
    public void testTrivialDiagonalAndHorizontalTravelx1000() {
        assertEquals(4500, new Solution().solution(0, 0, 9000, 4000));
    }

    @Test
    public void testTrivialDiagonalAndHorizontalTravelx10000() {
        assertEquals(45000, new Solution().solution(0, 0, 90000, 40000));
    }

    @Test
    public void testUnTrivialDiagonalAndHorizontalTravelx10000() {
        assertEquals(45001, new Solution().solution(0, 0, 90001, 40000));
    }

    @Test
    public void testUnTrivialDiagonalAndHorizontalTravelx10000i2() {
        assertEquals(45001, new Solution().solution(0, 0, 89999, 40000));
    }

    @Test
    public void testTrivialDiagonalAndHorizontalTravel3() {
        assertEquals(7, new Solution().solution(0, 0, 13, 4));
    }


    @Test
    public void testTrivialDiagonalAndHorizontalTravelButBoardNeedsRotation() {
        assertEquals(4, new Solution().solution(8, 2, 0, 0));
    }

}
