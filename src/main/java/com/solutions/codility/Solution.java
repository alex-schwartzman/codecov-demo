package com.solutions.codility;

import java.util.ArrayList;
import java.util.TreeSet;

public class Solution {

    static ArrayList<Integer>[] edges;
    static byte[][] answers;
    static byte NO = 2;
    static byte INTERCONNECTED = 1;
    static byte UNDEFINED = 0;
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
            result++;
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

        for (int i = 0; i < A.length; i++) {
            setAnswer(i, i, INTERCONNECTED);
            searchForConnectedNeighbors(i, i, new TreeSet<Integer>(edges[i]));
        }

        return result;
    }

    private void searchForConnectedNeighbors(int from, int to, TreeSet<Integer> allNeighbors) {
        while (from > 0) {
            while (allNeighbors.contains(from - 1)) {
                from--;
                if (allNeighbors.contains(from)) {
                    setAnswer(from, to, INTERCONNECTED);
                    allNeighbors.addAll(edges[from]);
                } else {
                    setAnswer(from, to, NO);
                }
            }

            Integer nextSuitableNeighbor = allNeighbors.floor(from - 1);
            if (nextSuitableNeighbor != null) {
                if (isConnectedGraph(nextSuitableNeighbor, to)) {
                    setAnswer(nextSuitableNeighbor, to, INTERCONNECTED);
                    for (int vertex = nextSuitableNeighbor; vertex < lastConnectedPoint; vertex++) {
                        //add all neighbors for the gap
                        allNeighbors.addAll(edges[vertex]);
                    }
                } else {
                    setAnswer(nextSuitableNeighbor, to, NO);
                }

                from = nextSuitableNeighbor;
            }
        }
    }

    private boolean isConnectedGraph(int from, int to) {
        byte[] results = new byte[to - from + 1];
        for (int vertex = from; vertex <= to; vertex++) {
            for (Integer v : edges[vertex]) {
                if (from <= v && v <= to) {
                    results[v - from] = 1;
                }
            }
        }

        for (byte b : results) {
            if (b != 1) {
                return false;
            }
        }
        return true;
    }
}
