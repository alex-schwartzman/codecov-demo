package com.solutions.codility;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

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

    @Test
    public void testOnLongArray10000() {
        int size = 10000;
        char[] A = new char[size];
        Random generator = new Random(42);
        for (int i = 0; i < size; i++) {
            A[i] = (char) ('a' + (char) (generator.nextInt(25)));
        }
        final String input = new String(A);
        assertEquals(9906, new Solution().solution(input, 24));
        assertEquals(9972, new Solution().solution(input, 14));
        assertEquals(9995, new Solution().solution(input, 5));
    }

    @Test
    @Ignore //takes 10s on my machine
    public void testOnLongArray100000() {
        int size = 50000;
        char[] A = new char[size];
        Random generator = new Random(42);
        for (int i = 0; i < size; i++) {
            A[i] = (char) ('a' + (char) (generator.nextInt(25)));
        }
        final String input = new String(A);
        assertEquals(49888, new Solution().solution(input, 24));
        assertEquals(49973, new Solution().solution(input, 14));
    }
}
