package com.solutions.codility;

public class Solution {
    int result = 0;

    public int solution(int[] A) {
        int[] edgeCounts = new int[A.length];
        int[][] hardEdges = new int[A.length][A.length];
        for (int i = 0; i < A.length; i++) {
            int from = i;
            int to = A[i];
            if (from != to) {
                hardEdges[from][edgeCounts[from]] = to;
                hardEdges[to][edgeCounts[to]] = from;
                edgeCounts[from]++;
                edgeCounts[to]++;
            }
        }

        int rowEnd = A.length;
        for (int from = 0; from < A.length; from++) {
            int[] edgeCounters = new int[A.length];
            for (int to = from; to < rowEnd; to++) {
                for (int j = 0; j < edgeCounts[to]; j++) {
                    edgeCounters[hardEdges[to][j]]++;
                }
                int connectionsCount = 0;
                for (int i = from; i <= to; i++) {
                    connectionsCount += edgeCounters[i];
                }
                boolean connectedOK = (connectionsCount == (to - from) * 2);
                if (connectedOK) {
                    result++;
                }
            }
            //backwards
            for (int leftSideSliding = from; leftSideSliding < rowEnd; leftSideSliding++) {
                for (int j = 0; j < edgeCounts[leftSideSliding]; j++) {
                    edgeCounters[hardEdges[leftSideSliding][j]]--;
                }
                int connectionsCount = 0;
                for (int i = leftSideSliding + 1; i < rowEnd; i++) {
                    connectionsCount += edgeCounters[i];
                }
                boolean connectedOK = (connectionsCount == (rowEnd - leftSideSliding - 2) * 2);
                if (connectedOK) {
                    result++;
                }
            }
            rowEnd--;
        }
        return result;
    }
}
