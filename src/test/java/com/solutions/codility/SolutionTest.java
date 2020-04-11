package com.solutions.codility;

import javafx.util.Pair;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void oneSizedArray() {
        assertEquals("6", new Solution().solution(new int[][]{{6}}));
    }

    @Test
    public void oneDimensionalArray() {
        assertEquals("123456789", new Solution().solution(new int[][]{{1, 2, 3, 4, 5, 6, 7, 8, 9}}));
    }

    @Test
    public void twoRowsArray() {
        assertEquals("1234567899", new Solution().solution(new int[][]{{1, 2, 3, 4, 5, 6, 7, 8, 9}, {1, 2, 3, 4, 5, 6, 7, 8, 9}}));
    }

    @Test
    public void testAsDescribedInTask1() {
        assertEquals("997952", new Solution().solution(new int[][]{{9, 9, 7}, {9, 7, 2}, {6, 9, 5}, {9, 1, 2}}));
    }

    @Test
    public void testPerformanceOnRandomField100x100() {
        final int size = 100;
        int[][] A = new int[size][size];
        Random generator = new Random(42);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                A[x][y] = generator.nextInt(9) + 1;
            }
        }
        assertEquals("9449582879867596466585977659658386884369858625659957589853967626694598174898993285679998799869566722798757253568439686798579622998589497284782286988685668186568788597846349878648655745489757424364211", new Solution().solution(A));
    }

    @Test
    public void testPerformanceOnRandomField10x10() {
        final int size = 10;
        int[][] A = new int[size][size];
        Random generator = new Random(42);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                A[x][y] = generator.nextInt(9) + 1;
            }
        }
        assertEquals("9497976869986774795", new Solution().solution(A));
    }

    @Test
    public void testPerformanceOnRandomField30x30() {
        final int size = 30;
        int[][] A = new int[size][size];
        Random generator = new Random(42);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                A[x][y] = generator.nextInt(9) + 1;
            }
        }
        assertEquals("95464735945984865828375288588477972488558848964278568651581", new Solution().solution(A));
    }

    @Test
    public void testPerformanceOnRandomField1000x1000() {
        final int size = 1000;
        int[][] A = new int[size][size];
        Random generator = new Random(42);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                A[x][y] = generator.nextInt(9) + 1;
            }
        }
        new Solution().solution(A);
    }

    @Test
    public void testPerformanceOnRandomField300x300LowVariability() {
        final int size = 300;
        int[][] A = new int[size][size];
        Random generator = new Random(42);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                A[x][y] = generator.nextInt(2) + 1;
            }
        }
        assertEquals("22222212222222222222222222222222212222212122222221222221221221221222222222222212222222222222212122222222221222121222221222222122222222222222212222222221222222222212222222112222222221222222221222222222222222222221222121122222222222122222222222222222222122222222222222222122222121222222222222221222221222212222222222222221222122222222222211222222222222212222222222221222112122112222222212222222222222222222222212222222212122222212222212221222122222222222222222222222222212222222222222222222122212222212221222211122222122122222222222222222222222222121222222212212221222221221222222112222212212121212222", new Solution().solution(A));
    }
}
