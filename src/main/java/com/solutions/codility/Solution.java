package com.solutions.codility;

//final solution with award: https://app.codility.com/cert/view/cert3WKPWB-QFMAHUYMENFESZFQ/
public class Solution {
    public int solution(int[] H) {
        if (H.length == 0) {
            return 0;
        }

        if (H.length == 1) {
            return H[0];
        }

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

        int minArea = Integer.MAX_VALUE;
        for (int index = 1; index < H.length; index++) {
            final int leftSideWidth = index;
            final int rightSideWidth = H.length - index;
            final int leftSideMaxHeight = leftMaxValues[leftSideWidth];
            final int rightSideMaxHeight = rightMaxValues[rightSideWidth];
            final int currentArea = leftSideMaxHeight * leftSideWidth + rightSideMaxHeight * rightSideWidth;
            minArea = Math.min(currentArea, minArea);
        }
        return minArea;
    }
}
