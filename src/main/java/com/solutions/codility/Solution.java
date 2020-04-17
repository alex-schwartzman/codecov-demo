package com.solutions.codility;

import java.util.Arrays;
import java.util.TreeSet;

//https://app.codility.com/programmers/task/dream_team/
//Divide developers into two teams to maximize their total contribution.
//idea is to sort developers by best contribution to fronted team agains backend, and pick first F. Use TreeSet for it.
public class Solution {

    static class Developer implements Comparable<Developer> {
        public int a;
        public int b;
        public int n;

        public Developer(int a, int b, int n) {
            this.a = a;
            this.b = b;
            this.n = n;
        }

        public int getOverallContribution() {
            return a - b; //returns frontend-backend, i.e. if we get first F - we'll have best team
        }

        @Override
        public int compareTo(Developer other) {
            int contributionDifference = other.getOverallContribution() - this.getOverallContribution();
            if (contributionDifference != 0) {
                return contributionDifference > 0 ? 1 : -1;
            } else {
                return other.n > this.n ? 1 : -1;
            }
        }
    }

    public int solution(int[] A, int[] B, int F) {

        TreeSet<Developer> developers = new TreeSet<>();
        for (int i = 0; i < A.length; i++) {
            developers.add(new Developer(A[i], B[i], i));
        }

        int counter = 0;
        int result = 0;
        for (Developer d : developers) {
            if (counter < F) {
                result += d.a;
            } else {
                result += d.b;
            }
            counter++;
        }
        return result;
    }
}
