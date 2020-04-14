package com.solutions.codility;

import java.util.TreeSet;

//https://app.codility.com/programmers/task/replacing_books/
//Given a list of integers, return the maximum number of consecutive integers equal to each other after replacing at most K of them.
public class Solution {
    public int solution(int[] A, int K) {
        TreeSet<Integer> uniqueBooks = new TreeSet<>();
        for (int a : A) {
            uniqueBooks.add(a);
        }
        int result = 0;
        for (int currentBoook : uniqueBooks) {
            for (int from = 0; from < A.length; from++) {
                int kLocal = K;
                int counter = 0;
                for (int to = from; to < A.length && kLocal >= 0; to++) {
                    if (A[to] != currentBoook) {
                        kLocal--;
                    }
                    if (kLocal >= 0) {
                        counter++;
                    }
                }
                result = Math.max(result, counter);
            }
        }
        return result;
    }
}
