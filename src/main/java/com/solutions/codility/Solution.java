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
    static int unnecessaryKittens = 0;

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
                    unnecessaryKittens++;
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
            noWayThisMayBeInterconnected = isStillDisconnectedFromNextItem(from, to, from);
            if (noWayThisMayBeInterconnected) {
                answers[from][to] = NO;
                traverseDisconnectedTowardsHigherTo(from, to);
            }
        }
    }

    private boolean isStillDisconnectedFromNextItem(int from, int to, int nextItem) {
        boolean noWayThisMayBeInterconnected;
        noWayThisMayBeInterconnected = true;
        for (int e : edges[nextItem]) {
            if (from <= e && e <= to) {
                noWayThisMayBeInterconnected = false;
                break;
            }
        }
        return noWayThisMayBeInterconnected;
    }

    private void traverseDisconnectedTowardsHigherTo(int from, int to) {
        boolean noWayThisMayBeInterconnected = true;
        while (noWayThisMayBeInterconnected) {
            to++;

            if (to >= size || answers[from][to] != UNDEFINED) {
                return;
            }

            noWayThisMayBeInterconnected = isStillDisconnectedFromNextItem(from, to, to);

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
            from--;

            if (from < 0 || answers[from][to] != UNDEFINED) {
                break;
            }
            isInterconnected = isInterconnectedWithNextItem(from, to, from);
            answers[from][to] = isInterconnected ? YES : NO;
            if (isInterconnected) {
                result++;
                traverseInterconnectedTowardsHigherTo(from, to);
            }
        }
    }

    private boolean isInterconnectedWithNextItem(int from, int to, int nextItem) {
        boolean isInterconnected;
        isInterconnected = false;
        for (int e : edges[nextItem]) {
            if (from <= e && e <= to) {
                isInterconnected = true;
                break;
            }
        }
        return isInterconnected;
    }

    //to be called only if from-to are interconnected
    private void traverseInterconnectedTowardsHigherTo(int from, int to) {
        boolean isInterconnected = true;
        while (isInterconnected) {
            to++;

            if (to >= size || answers[from][to] != UNDEFINED) {
                return;
            }

            isInterconnected = isInterconnectedWithNextItem(from, to, to);
            answers[from][to] = isInterconnected ? YES : NO;
            if (isInterconnected) {
                result++;
                traverseInterconnectedTowardsLowerFrom(from, to);
            }
        }
    }
}
