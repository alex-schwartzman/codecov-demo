package com.solutions.codility;

import java.util.ArrayList;

public class Solution {

    static ArrayList<Integer>[] edges;
    int result = 0;

    public int solution(int[] A) {
        //noinspection unchecked
        edges = new ArrayList[A.length];

        for (int i = 0; i < A.length; i++) {
            edges[i] = new ArrayList<>();
        }

        for (int i = 0; i < A.length; i++) {
            //noinspection UnnecessaryLocalVariable
            int from = i;
            int to = A[i];
            if (from != to) {
                edges[from].add(to);
                edges[to].add(from);
            }
        }

        for (int from = 0; from < A.length; from++) {
            int[] edgeCounters = new int[A.length];
            for (int to = from; to < A.length; to++) {
                for (Integer v : edges[to]) {
                    edgeCounters[v]++;
                }
                int connectinsCount = 0;
                for (int i = from; i <= to; i++) {
                    connectinsCount += edgeCounters[i];
                }
                boolean connectedOK = (connectinsCount == (to - from) * 2);
                if (connectedOK) {
                    result++;
                }
            }
        }
        return result;
    }
}
