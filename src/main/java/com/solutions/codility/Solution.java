package com.solutions.codility;

import java.util.Arrays;

//https://app.codility.com/programmers/task/different_characters/
//Given a string, find the shortest substring that can be removed to yield a string that contains exactly K different characters.
//idea is to build histogram, and then try S.length()^2 combinations of substring, starting from shorter substring so longer
public class Solution {

    static class CharHistogram {
        private final static int size = 'z' - 'a' + 10;
        private int[] histogram = new int[size];
        private int[] backupHistogram = new int[size];
        int uniqueChars = 0;
        int uniqueCharsBackup = 0;

        public void add(char c) {
            if (histogram[c - 'a'] == 0) {
                uniqueChars++;
            }
            histogram[c - 'a']++;
        }

        public void remove(char c) {
            histogram[c - 'a']--;
            if (histogram[c - 'a'] == 0) {
                uniqueChars--;
            }
        }

        public void backup() {
            backupHistogram = Arrays.copyOf(histogram, histogram.length);
            uniqueCharsBackup = uniqueChars;
        }

        public void restore() {
            histogram = Arrays.copyOf(backupHistogram, histogram.length);
            uniqueChars = uniqueCharsBackup;
        }

    }

    public int solution(String S, int K) {
        CharHistogram histogram = new CharHistogram();

        for (int i = 0; i < S.length(); i++) {
            histogram.add(S.charAt(i));
        }

        if (histogram.uniqueChars < K) {
            return -1;
        }

        if (histogram.uniqueChars == K) {
            return 0;
        }

        if (K == 0) {
            return S.length();
        }

        histogram.backup();

        for (int substringLength = 1; substringLength < S.length() - K; substringLength++) {
            //first, adjust histogram to as if substring would be removed from the first bytes:
            for (int i = 0; i < substringLength; i++) {
                histogram.remove(S.charAt(i));
            }

            if (histogram.uniqueChars == K) {
                return substringLength;
            }

            int substringStart = 0;
            int substringEnd = substringLength;

            while (substringEnd < S.length()) {
                histogram.add(S.charAt(substringStart));
                histogram.remove(S.charAt(substringEnd));

                if (histogram.uniqueChars == K) {
                    return substringLength;
                }
                substringEnd++;
                substringStart++;
            }

            //In the end, adjust histogram to as if substring tail would be recovered:
            histogram.restore();
        }

        return S.length();
    }
}
