package com.solutions.codility;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    static int[] village1000LinedUp;
    static int[] village1000OddEven;

    @BeforeClass
    public static void warmUpAllVillages() {
        //1000 villages in a row
        final int size = 1000;
        village1000LinedUp = new int[size];
        for (int i = 0; i < size; i++) {
            village1000LinedUp[i] = i + 1;
        }
        village1000LinedUp[size - 1] = size - 1;

        //1000 villages first-all-odd, then-all-even
        village1000OddEven = new int[size];
        for (int i = 0; i < size - 2; i++) {
            village1000OddEven[i] = i + 2;
        }
        village1000OddEven[size - 1] = size - 1;
        village1000OddEven[size - 2] = size - 1;


    }

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

    @Test
    public void test1000VillagesInARow() {
        assertEquals(500500, new Solution().solution(village1000LinedUp));
    }

    @Test
    public void test1000VillagesOddEven() {
        assertEquals(1999, new Solution().solution(village1000OddEven));
    }

}
