package com.solutions.codility;

import java.util.Arrays;

public class Solution {
    public int solution(int[] A) {
        Arrays.sort(A);
        int result = 1;
        for (int i : A) {
            if (i < result) {
                continue;
            }
            else if (i == result) {
                result++;
            }
            else if (i > result) {
                break;
            }
        }
        return result;
    }
}
