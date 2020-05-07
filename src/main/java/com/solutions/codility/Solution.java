package com.solutions.codility;

//challenge for minimal-size implementation
//given N, draw in-layered squares using ASCII-art o and #
public class Solution {
    public int solution(int[] A) {
        int N = 10;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int maxBorder = Math.max(Math.abs(i * 2 - N + 1), Math.abs(j * 2 - N + 1));
                System.out.print((maxBorder / 2) % 2 == 0 ? "o" : "#");
            }
            System.out.println("");
        }
        return 42;
    }
}
