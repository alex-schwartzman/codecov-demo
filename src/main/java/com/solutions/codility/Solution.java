package com.solutions.codility;

import java.util.ArrayList;

public class Solution {

    static byte[][] answers;
    static ArrayList<Integer>[] edges;
    int result = 0;
    static byte UNDEFINED = 0; //because in Java uninitialized array is always initialized with 0s
    static byte YES = 1;
    static byte NO = 2;
    static int size = 0;

    public int solution(int[] A) {
        size = A.length;
        //noinspection unchecked
        edges = new ArrayList[A.length];
        answers = new byte[A.length][A.length];

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

        for (int from = A.length - 1; from >= 0; from--) { //from should be looped from higher values, so that recursive calls to lowers froms are possible
            int[] edgeCounters = new int[A.length];
            for (int to = from; to < A.length; to++) {
                for (Integer v : edges[to]) {
                    edgeCounters[v]++;
                }
                if (answers[from][to] != UNDEFINED) {
                    continue;
                }
                int connectionsCount = 0;
                for (int i = from; i <= to; i++) {
                    connectionsCount += edgeCounters[i];
                }
                if (connectionsCount == (to - from) * 2) {
                    answers[from][to] = YES;
                    result++;
                    traverseInterconnectedTowardsLowerFrom(from, to);
                } else {
                    answers[from][to] = NO;
                    traverseDisconnectedTowardsLowerFrom(from, to);
                }
            }
        }
        return result;
    }

    private void traverseDisconnectedTowardsLowerFrom(int from, int to) {
        boolean noWayThisMayBeInterconnected = true;
        while (noWayThisMayBeInterconnected) {
            from--;
            if (from < 0 || answers[from][to] != UNDEFINED) {
                break;
            }

            noWayThisMayBeInterconnected = true;
            for (int e : edges[from]) {
                if (from <= e && e <= to) {
                    noWayThisMayBeInterconnected = false;
                    break;
                }
            }

            if (noWayThisMayBeInterconnected) {
                answers[from][to] = NO;
//                traverseDisconnectedTowardsHigherTo(from, to);
            }
        }
    }

    private void traverseDisconnectedTowardsHigherTo(int from, int to) {
        boolean noWayThisMayBeInterconnected = true;
        while (noWayThisMayBeInterconnected) {
            noWayThisMayBeInterconnected = false;
            to++;

            if (to >= size || answers[from][to] != UNDEFINED) {
                return;
            }

            for (int e : edges[to]) {
                if (e < from || to < e) {
                    noWayThisMayBeInterconnected = true;
                    break;
                }
            }
            if (noWayThisMayBeInterconnected) {
                answers[from][to] = NO;
                traverseDisconnectedTowardsLowerFrom(from, to);
            }
        }
    }

    //to be called only if from-to are interconnected
    private void traverseInterconnectedTowardsLowerFrom(int from, int to) {
        boolean isInterconnected = true;
        while (isInterconnected) {
            isInterconnected = false;
            from--;

            if (from < 0 || answers[from][to] != UNDEFINED) {
                break;
            }
            for (int e : edges[from]) {
                if (from <= e && e <= to) {
                    isInterconnected = true;
                    break;
                }
            }
            answers[from][to] = isInterconnected ? YES : NO;
            if (isInterconnected) {
                result++;
                traverseInterconnectedTowardsHigherTo(from, to);
            }
        }
    }

    //to be called only if from-to are interconnected
    private void traverseInterconnectedTowardsHigherTo(int from, int to) {
        boolean isInterconnected = true;
        while (isInterconnected) {
            isInterconnected = false;
            to++;

            if (to >= size || answers[from][to] != UNDEFINED) {
                return;
            }

            for (int e : edges[to]) {
                if (from <= e && e <= to) {
                    isInterconnected = true;
                    break;
                }
            }
            answers[from][to] = isInterconnected ? YES : NO;
            if (isInterconnected) {
                result++;
                traverseInterconnectedTowardsLowerFrom(from, to);
            }
        }
    }
}
