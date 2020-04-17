package com.solutions.codility;

import java.util.Arrays;

//https://app.codility.com/programmers/task/different_characters/
//Given a string, find the shortest substring that can be removed to yield a string that contains exactly K different characters.
//idea is to build histogram, and then try S.length()^2 combinations of substring, starting from shorter substring so longer
public class Solution {

    static class CharHistogram {
        private final static int size = 'z' - 'a' + 10;
        private int[] histogram = new int[size];
        int uniqueChars = 0;

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

        public void copyFrom(CharHistogram other) {
            this.histogram = Arrays.copyOf(other.histogram, other.histogram.length);
            this.uniqueChars = other.uniqueChars;
        }
    }

    public int solution(String S, int K) {
        char[] inputString = S.toCharArray();
        CharHistogram histogram = new CharHistogram();

        for (char c : inputString) {
            histogram.add(c);
        }

        if (histogram.uniqueChars < K) {
            return -1;
        }

        if (histogram.uniqueChars == K) {
            return 0;
        }

        if (K == 0) {
            return  inputString.length;
        }

        CharHistogram backupHistogram = new CharHistogram();
        backupHistogram.copyFrom(histogram);

        for (int substringLength = 1; substringLength <=  inputString.length - K; substringLength++) {
            //first, adjust histogram to as if substring would be removed from the first bytes:
            for (int i = 0; i < substringLength; i++) {
                histogram.remove(inputString[i]);
            }

            if (histogram.uniqueChars == K) {
                return substringLength;
            }

            int substringStart = 0;
            int substringEnd = substringLength;

            while (substringEnd < inputString.length) {
                histogram.add(inputString[substringStart]);
                histogram.remove(inputString[substringEnd]);

                if (histogram.uniqueChars == K) {
                    return substringLength;
                }
                substringEnd++;
                substringStart++;
            }

            histogram.copyFrom(backupHistogram);
        }

        return 8998899; //should never get here
    }
}
