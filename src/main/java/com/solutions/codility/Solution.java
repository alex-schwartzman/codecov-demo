package com.solutions.codility;


import java.util.ArrayList;

public class Solution {

    static ArrayList<Integer>[] edges;

    public int solution(int[] A) {
        edges = new ArrayList[A.length];
        for (int i = 0; i < A.length; i++) {
            edges[i] = new ArrayList<>();
        }

        for (int i = 0; i < A.length; i++) {
            int from = i;
            int to = A[i];
            if (from != to) {
                edges[from].add(to);
                edges[to].add(from);
            }
        }

        int result = A.length;
        for (int startDate = 0; startDate < A.length; startDate++) {
            for (int endDate = startDate + 1; endDate < A.length; endDate++) {
                if (isConnectedGraph(startDate, endDate)) {
                    result++;
                }
            }
        }

        return result;
    }

    private boolean isConnectedGraph(int startDate, int endDate) {
        if (countOrphans(startDate, endDate) > 0) {
            return false;
        }
        return traverse(startDate, startDate, endDate, -1) == endDate - startDate + 1;
    }

    private int countOrphans(int startDate, int endDate) {
        //this is insufficient. We actually need to count isolated islands of size 1..N, not only islands of size N
        int accumulator = 0;
        for (int i = startDate; i <= endDate; i++) {
            boolean isOrphan = true;
            for (int nextHop : edges[i]) {
                if (startDate <= nextHop && nextHop <= endDate) {
                    isOrphan = false;
                    break;
                }
            }
            if (isOrphan) {
                accumulator++;
            }
        }
        return accumulator;
    }

    private int traverse(int startFrom, int lowerBoundary, int upperBoundary, int exceptEdge) {
        int accumulator = 1;
        for (int nextHop : edges[startFrom]) {
            if (nextHop == exceptEdge || nextHop > upperBoundary || nextHop < lowerBoundary) {
                continue;
            }
            accumulator += traverse(nextHop, lowerBoundary, upperBoundary, startFrom);
        }
        return accumulator;
    }
}
