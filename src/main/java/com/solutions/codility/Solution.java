package com.solutions.codility;

import java.util.ArrayList;
import java.util.TreeSet;

public class Solution {

    static ArrayList<Integer>[] edges;
    static byte[][] answers;
    static byte NO = 2;
    static byte INTERCONNECTED = 1;
    static byte UNDEFINED = 0;
    static int size = 0;
    int result = 0;

    void setAnswer(int from, int to, byte answer) {
        if (from > to) {
            //swap
            final int tmp = to;
            to = from;
            from = tmp;
        }
        if (answers[from][to] != answer) {
            answers[from][to] = answer;
            if (answer == INTERCONNECTED) {
//                System.out.printf(" %d:%d %d\n", from, to, answer);
                result++;
            }
        }
    }

    byte getAnswer(int from, int to) {
        if (from > to) {
            //swap
            final int tmp = to;
            to = from;
            from = tmp;
        }
        return answers[from][to];
    }

    public int solution(int[] A) {
        size = A.length;
        //noinspection unchecked
        edges = new ArrayList[size];
        answers = new byte[size][size];

        for (int i = 0; i < size; i++) {
            edges[i] = new ArrayList<>();
        }

        for (int i = 0; i < size; i++) {
            //noinspection UnnecessaryLocalVariable
            int from = i;
            int to = A[i];
            if (from != to) {
                edges[from].add(to);
                edges[to].add(from);
            }
        }

        for (int from = size - 1; from >= 0; from--) {
            for (int to = from; to < size; to++) {
                if (getAnswer(from, to) == UNDEFINED) { //otherwise, we already calculated this item, as a side-effect of recursive propagation search, uff, saved come time
                    if (isConnectedGraph(from, to)) {
                        setAnswer(from, to, INTERCONNECTED);
                        searchForConnectedNeighbors(from, to, null);
                    }
                }
            }
        }
        return result;
    }

    static int counter;

    private boolean isConnectedGraph(int from, int to) {
        if (from == to) {
            return true;
        }
        int connectionsCount = 0;

        for (int vertex = from; vertex <= to; vertex++) {
            for (Integer v : edges[vertex]) {
                if (from <= v && v <= to) {
                    connectionsCount++;
                }
            }
        }

        counter++;
        return connectionsCount == (to - from) * 2;
    }

    private void searchForConnectedNeighbors(int from, int to, TreeSet<Integer> allNeighbors) {
        if (allNeighbors == null) {
            allNeighbors = new TreeSet<Integer>();
            for (int i = from; i <= to; i++) {
                for (Integer neighbor : edges[i]) {
                    if (neighbor < from || to < neighbor) {
                        allNeighbors.add(neighbor);
                    }
                }
            }
        }
        while (to < size - 1) {
            final int leftSizeVertex = from - 1;
            if (leftSizeVertex >= 0) {
                if (getAnswer(leftSizeVertex, to) == UNDEFINED) {
                    if (allNeighbors.contains(leftSizeVertex)) {
                        //recursively attempt to grow the range to the left. AllNeighbors will need to be recalculated.
                        setAnswer(leftSizeVertex, to, INTERCONNECTED);
                        cleanupAllNeighbors(from, to, allNeighbors);
                        //noinspection unchecked
                        TreeSet<Integer> newNeighborsList = (TreeSet<Integer>) allNeighbors.clone();
                        newNeighborsList.addAll(edges[leftSizeVertex]);
                        searchForConnectedNeighbors(leftSizeVertex, to, newNeighborsList);
                    } else {
                        setAnswer(leftSizeVertex, to, NO);
                    }
                }
            }
            to++;
            if (getAnswer(from, to) != UNDEFINED) {
                break;
            }
            if (getAnswer(from, to) == INTERCONNECTED || allNeighbors.contains(to)) {
                cleanupAllNeighbors(from, to, allNeighbors);
                allNeighbors.addAll(edges[to]);
                setAnswer(from, to, INTERCONNECTED);
            } else {
                setAnswer(from, to, NO);
                break;
            }
        }

//        cleanupAllNeighbors(from, to, allNeighbors);
//
//        Integer nextFrom = allNeighbors.floor(from);
//        if (nextFrom != null && nextFrom < from) {
//            for (int ifrom = nextFrom; ifrom < from; ifrom++) {
//                setAnswer(ifrom, to, NO);
//            }
//        }
//        Integer nextTo = allNeighbors.ceiling(to);
//        if (nextTo != null && nextTo > to) {
//            for (int ito = to + 1; ito <= nextTo; ito++) {
//                setAnswer(from, ito, NO);
//            }
//        }
    }

    private void cleanupAllNeighbors(final int from, final int to, TreeSet<Integer> allNeighbors) {
        allNeighbors.removeIf(i -> (from <= i && i <= to));
    }
}
