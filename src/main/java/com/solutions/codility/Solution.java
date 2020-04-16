package com.solutions.codility;

import java.util.Set;
import java.util.TreeSet;

//https://app.codility.com/programmers/task/leader_slice_inc/
//this challenge is to just build histogram and then move the window one-by-one item, tracking only what's happened on edges
public class Solution {

    public int[] solution(int K, int M, int[] A) {
        Set<Integer> results = new TreeSet<>();
        int[] histogram = new int[M + 2];

        for (int i = 0; i < K; i++) {
            A[i]++;
        }

        for (int value : A) {
            histogram[value]++;
            if (histogram[value] > A.length / 2) {
                results.add(value);
            }
        }

        //it is like [start, end)
        int startPosition = 0;
        int endPosition = K;

        while (endPosition < A.length) {
            histogram[A[startPosition]]--;
            A[startPosition]--;
            histogram[A[startPosition]]++;

            histogram[A[endPosition]]--;
            A[endPosition]++;
            histogram[A[endPosition]]++;

            if (histogram[A[endPosition]] > A.length / 2) {
                results.add(A[endPosition]);
            }
            endPosition++;
            startPosition++;
        }

        return results.stream().sorted().mapToInt(i -> i).toArray();
    }
}
