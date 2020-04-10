package com.solutions.codility;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Solution {
    public int solution(int[] H) {
        return IntStream.range(1, H.length).parallel().map(index -> coverAllByTwoBanners(H, index)).min().getAsInt();
    }

    private int coverAllByTwoBanners(int[] H, int index) {
        final int leftSideMaxHeight = Arrays.stream(H).limit(index).max().getAsInt();
        final int rightSideMaxHeight = Arrays.stream(H).skip(index).max().getAsInt();
        final int leftSideWidth = index;
        final int rightSideWidth = H.length - index;
        return leftSideMaxHeight * leftSideWidth + rightSideMaxHeight * rightSideWidth;
    }
}
