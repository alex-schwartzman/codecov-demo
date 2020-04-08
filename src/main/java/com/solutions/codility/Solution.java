package com.solutions.codility;

import java.util.stream.IntStream;

public class Solution {
    public int solution(int[] A) {
        int[] B = IntStream.of(A).filter(x -> x > 0).sorted().distinct().toArray();
        for (int i = 0; i < B.length; i++) {
            if (i + 1 != B[i]) {
                return i + 1;
            }
        }
        return B.length + 1;
    }
}
