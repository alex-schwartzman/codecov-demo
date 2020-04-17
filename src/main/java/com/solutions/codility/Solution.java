package com.solutions.codility;

import java.util.Arrays;
import java.util.Comparator;

//https://app.codility.com/programmers/task/dream_team/
//Divide developers into two teams to maximize their total contribution.
//idea is to sort developers by best contribution to fronted team agains backend, and pick first F. Use TreeSet for it.
public class Solution {

    int[] A;
    int[] B;
    int[] C;
    Integer[] N;

    public int solution(int[] A, int[] B, int F) {
        this.A = A;
        this.B = B;

        N = new Integer[A.length];
        C = new int[A.length];

        for (int i = 0; i < A.length; i++) {
            N[i] = i;
            C[i] = A[i] - B[i];
        }

        Arrays.sort(N, new Comparator<Integer>() {
            @Override
            public int compare(Integer n1, Integer n2) {
                int contributionDifference = C[n2] - C[n1];
                if (contributionDifference != 0) {
                    return contributionDifference > 0 ? 1 : -1;
                } else {
                    return n1.compareTo(n2);
                }
            }

            @Override
            public boolean equals(Object o) {
                return false;
            }
        });

        int counter = 0;
        int result = 0;
        for (Integer n : N) {
            if (counter < F) {
                result += A[n];
            } else {
                result += B[n];
            }
            counter++;
        }
        return result;
    }
}
