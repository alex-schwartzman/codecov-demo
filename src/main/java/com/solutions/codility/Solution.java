package com.solutions.codility;

import java.util.Set;
import java.util.TreeSet;

//https://app.codility.com/programmers/task/leader_slice_inc/
//this challenge is to just build histogram and then move the window one-by-one item, tracking only what's happened on edges
public class Solution {

    public int[] solution(int K, int M, int[] A) {
        Set<Integer> resultsSet = new TreeSet<>();
        int[] histogram = new int[M + 2];

        for (int i = 0; i < K; i++) {
            A[i]++;
        }

        for (int value : A) {
            histogram[value]++;
            if (histogram[value] > A.length / 2) {
                resultsSet.add(value);
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

            if (histogram[A[endPosition]] * 2 > A.length) {
                resultsSet.add(A[endPosition]);
            }
            if (histogram[A[startPosition]] * 2 > A.length) {
                resultsSet.add(A[startPosition]);
            }
            endPosition++;
            startPosition++;
        }

        int[] resultInt = new int[resultsSet.size()];
        int index = 0;
        for (Integer r : resultsSet) {
            resultInt[index] = r;
            index++;
        }
        return resultInt;
    }
}
