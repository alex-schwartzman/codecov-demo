package com.solutions.codility;

import java.util.stream.IntStream;

public class Solution {
    public int solution(int[] H) {
        int[] rightMaxValues = new int[H.length];
        int[] leftMaxValues = new int[H.length];
        int leftCurrentMax = Integer.MIN_VALUE;
        int rightCurrentMax = Integer.MIN_VALUE;
        for (int i = 0; i < H.length - 1; i++) {
            leftCurrentMax = Math.max(leftCurrentMax, H[i]);
            rightCurrentMax = Math.max(rightCurrentMax, H[H.length - 1 - i]);
            leftMaxValues[i + 1] = leftCurrentMax; //sorry, 1-based array :)
            rightMaxValues[i + 1] = rightCurrentMax;
        }

        return IntStream.range(1, H.length).map(index -> {
            final int leftSideWidth = index;
            final int rightSideWidth = H.length - index;
            final int leftSideMaxHeight = leftMaxValues[leftSideWidth];
            final int rightSideMaxHeight = rightMaxValues[rightSideWidth];
            return leftSideMaxHeight * leftSideWidth + rightSideMaxHeight * rightSideWidth;
        }).min().getAsInt();
    }
}
