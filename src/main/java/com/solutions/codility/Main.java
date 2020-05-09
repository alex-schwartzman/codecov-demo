package com.solutions.codility;

import java.util.ArrayDeque;
import java.util.Scanner;

//D - Alice, Bob and Candies https://codeforces.com/contest/1352/problem/D https://codeforces.com/contest/1352/submission/79586038
//public class Main {
//
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int count = in.nextInt();
//        for (int i = 0; i < count; i++) {
//            int candieCount = in.nextInt();
//            ArrayDeque<Integer> candiesRow = new ArrayDeque<>();
//            while (candieCount-- > 0) {
//                candiesRow.add(in.nextInt());
//            }
//
//            System.out.println(solve(candiesRow));
//        }
//    }
//
//    public static String solve(ArrayDeque<Integer> candiesRow) {
//        int turnCount = 0, aliceSize = 0, bobSize = 0, maxEatenInATurn = 0, currentlyEatenInATurn = 0;
//        while (!candiesRow.isEmpty()) {
//            if (currentlyEatenInATurn == 0) {
//                turnCount++;
//            }
//            if (turnCount % 2 == 1) {
//                //Alice moves
//                final int nextCandie = candiesRow.removeFirst();
//                aliceSize += nextCandie;
//                currentlyEatenInATurn += nextCandie;
//                if (currentlyEatenInATurn > maxEatenInATurn) {
//                    //switch to Bob
//                    maxEatenInATurn = currentlyEatenInATurn;
//                    currentlyEatenInATurn = 0;
//                }
//            } else {
//                //Bob moves
//                final int nextCandie = candiesRow.removeLast();
//                bobSize += nextCandie;
//                currentlyEatenInATurn += nextCandie;
//                if (currentlyEatenInATurn > maxEatenInATurn) {
//                    //switch to Alice
//                    maxEatenInATurn = currentlyEatenInATurn;
//                    currentlyEatenInATurn = 0;
//                }
//            }
//        }
//        return String.format("%d %d %d", turnCount, aliceSize, bobSize);
//    }
//}

//F - Binary String Reconstruction https://codeforces.com/contest/1352/problem/F https://codeforces.com/contest/1352/submission/79581910
//public class Main {
//
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int count = in.nextInt();
//        for (int i = 0; i < count; i++) {
//            int n0 = in.nextInt();
//            int n1 = in.nextInt();
//            int n2 = in.nextInt();
//            System.out.println(solve(n0, n1, n2));
//        }
//    }
//
//    public static String solve(int n0, int n1, int n2) {
//        StringBuilder result = new StringBuilder();
//        if (n1 == 0) {
//            if (n0 != 0) {
//                repetitiveAdd(result, "0", n0 + 1);
//            } else if (n2 != 0) {
//                repetitiveAdd(result, "1", n2 + 1);
//            } else {
//                return "Invalid Input";
//            }
//        } else {
//            if (n1 % 2 == 0 && n1 > 0) {
//                result.append('1');
//                n1--;
//            }
//            //from now on, n1 is odd
//            repetitiveAdd(result, "0", n0 + 1);
//            repetitiveAdd(result, "10", n1 / 2);
//            repetitiveAdd(result, "1", n2 + 1);
//        }
//        return result.toString();
//    }
//
//    private static void repetitiveAdd(StringBuilder result, String value, int count) {
//        while (count-- > 0) {
//            result.append(value);
//        }
//    }
//
//}


//C - K-th Not Divisible by n https://codeforces.com/contest/1352/problem/C https://codeforces.com/contest/1352/submission/79549060
//public class Main {
//
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int count = in.nextInt();
//        for (int i = 0; i < count; i++) {
//            int n = in.nextInt();
//            int k = in.nextInt();
//            System.out.println(solve(n, k));
//            ;
//        }
//    }
//
//    public static int solve(int n, int k) {
//        int remainder = k;
//        int start = 0;
//        while (remainder >= n) {
//            int skipPart = remainder / n;
//            remainder = remainder % n + skipPart;
//            start += skipPart * n;
//        }
//        return start + remainder;
//    }
//}

//
//    //B - Same Parity Summands https://codeforces.com/contest/1352/problem/B https://codeforces.com/contest/1352/submission/79525295
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int count = in.nextInt();
//        for (int i = 0; i < count; i++) {
//            int n = in.nextInt();
//            int k = in.nextInt();
//            LinkedList<String> results = solve(n, k);
//            if (results != null) {
//                System.out.println("YES");
//                System.out.println(String.join(" ", results));
//            } else {
//                System.out.println("NO");
//            }
//        }
//    }
//
//    public static LinkedList<String> solve(int n, int k) {
//        LinkedList<String> results = new LinkedList<>();
//
//        final boolean nIsEven = n % 2 == 0;
//        final boolean kIsEven = k % 2 == 0;
//        if (nIsEven) {
//            if (kIsEven) {
//                if (n < k) {
//                    return null;
//                }
//                while (k > 1) {
//                    k--;
//                    results.add("1");
//                    n -= 1;
//                }
//                results.add(String.valueOf(n));
//            } else {
//                //k is odd - therefore we cannot afford odd results => should check multiplication by 2
//                if (n < k * 2) {
//                    return null;
//                }
//                while (k > 1) {
//                    k--;
//                    results.add("2");
//                    n -= 2;
//                }
//                results.add(String.valueOf(n));
//            }
//        } else {
//            if (kIsEven) {
//                return null;
//            } else {
//                if (n < k) {
//                    return null;
//                }
//                while (k > 1) {
//                    k--;
//                    results.add("1");
//                    n -= 1;
//                }
//                results.add(String.valueOf(n));
//            }
//        }
//
//        return results;
//    }
//}
//

////A - Sum of Round Numbers https://codeforces.com/contest/1352/problem/A https://codeforces.com/contest/1352/submission/79494122
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int count = in.nextInt();
//        for (int i = 0; i < count; i++) {
//            int number = in.nextInt();
//            LinkedList<String> results = new LinkedList<>();
//            int multiplier = 1;
//            while (number > 0) {
//                int resultItem = number % 10;
//                if (resultItem != 0) {
//                    results.add(String.valueOf(resultItem * multiplier));
//                }
//                number /= 10;
//                multiplier*=10;
//            }
//            System.out.println(results.size());
//            System.out.println(String.join(" ", results));
//        }
//    }
