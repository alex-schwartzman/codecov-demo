package com.solutions.codility;

import java.util.LinkedList;
import java.util.Scanner;

//G - Special Permutation https://codeforces.com/contest/1352/problem/G https://codeforces.com/contest/1352/submission/79592733
//public class Main {
//
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int count = in.nextInt();
//        for (int i = 0; i < count; i++) {
//            System.out.println(solve(in.nextInt()));
//        }
//    }
//
//    public static String solve(int max) {
//        if (max <= 3) {
//            return "-1";
//        } else {
//            // ...max-8, max-6, max-4, max-2, max, max-3, max-1, max-5, max-7...
//            LinkedList<String> result = new LinkedList<>();
//            for (int growingSequence = 2 - max % 2; growingSequence <= max; growingSequence += 2) {
//                result.add(String.valueOf(growingSequence));
//            }
//            result.add(String.valueOf(max - 3));
//            result.add(String.valueOf(max - 1));
//            for (int reducingSequence = max - 5; reducingSequence > 0; reducingSequence -= 2) {
//                result.add(String.valueOf(reducingSequence));
//            }
//            return String.join(" ", result);
//        }
//    }
//}


//E - Special Elements https://codeforces.com/contest/1352/problem/E https://codeforces.com/contest/1352/submission/79589579
//public class Main {
//
//    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int count = in.nextInt();
//        for (int i = 0; i < count; i++) {
//            int elementsCount = in.nextInt();
//            int[] elementsRow = new int[elementsCount];
//            while (elementsCount-- > 0) {
//                elementsRow[elementsCount] = in.nextInt();
//            }
//            System.out.println(solve(elementsRow));
//        }
//    }
//
//    public static int solve(int[] elementsRow) {
//        int matchingNumbersCount = 0;
//        final boolean[] answers = new boolean[10000]; //it's guaranteed to have sum no bigger than 8000
//        for (int start = 0; start < elementsRow.length; start++) {
//            int sumFromStart = elementsRow[start];
//            for (int end = start + 1; end < elementsRow.length; end++) {
//                sumFromStart += elementsRow[end];
//                if (sumFromStart < answers.length) {
//                    answers[sumFromStart] = true;
//                }
//            }
//        }
//
//        for (int element : elementsRow) {
//            if (answers[element]) {
//                matchingNumbersCount++;
//            }
//        }
//        return matchingNumbersCount;
//    }
//
//}


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
