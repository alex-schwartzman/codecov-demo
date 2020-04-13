package com.solutions.codility;

import java.util.ArrayList;

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

        setAnswer(A.length - 1, A.length - 1, INTERCONNECTED);
        for (int from = 0; from < A.length - 1; from++) {
            setAnswer(from, from, INTERCONNECTED);
            for (int to = from + 1; to < A.length; to++) {
                if (isConnectedGraph(from, to)) {
                    setAnswer(from, to, INTERCONNECTED);
                }
            }
        }
        return result;
    }

    //looks awesome, but is inherently incorrect
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
