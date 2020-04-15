package com.solutions.codility;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

//https://app.codility.com/programmers/task/replacing_books/
//Given a list of integers, return the maximum number of consecutive integers equal to each other after replacing at most K of them.
public class Solution {

    //The main idea is to convert one sequence of book numbers into N sequences of book repetitions, like:
    // 1,3,3,3,3,1,1,1,1,3,5 gets converted into 3 sequences:
    // book 1: 10000111100
    // book 3: 01111000010
    // book 5: 00000000001
    // after that, the sequences may be compacted into repetition counters:
    // book 1: 1,4,4,2
    // book 3: 1,4,4,1,1
    // book 5: 10,1
    // however, in order to make sure the odd counter always signifies replaceable books, we need to add trailing and leading zeroes:
    // book 1: 0,1,4,4,2
    // book 3: 1,4,4,1,1
    // book 5: 10,1,0
    // and then we just slide the window, with step of 2 through the sequence
    class BinarySequenceIntervalsCounter {
        LinkedList<Integer> intervalsInPast = new LinkedList<>();
        //in intervalsInPast odds are always replaceable books, evens are always reference books
        int nonBookCount = 0;
        int bookCount = 0;
        int repeatingSequenceStart;
        int repeatingSequenceStop;

        //constructor is called when this booknumber is first encountered in the row.
        BinarySequenceIntervalsCounter(int position) {
            repeatingSequenceStart = position;
            addIntervalAndRecalculate(position);
        }

        private void addIntervalAndRecalculate(int count) {
            intervalsInPast.add(count);

            if (intervalsInPast.size() % 2 == 1) {
                nonBookCount += count;
            } else {
                bookCount += count;
            }

            if (intervalsInPast.size() < 3) {
                return;
            }

            if (nonBookCount >= k) {
                setResult(bookCount + k);
                //truncate if we used up too many replacement options for non-books
                //be careful to not truncate too many, in a sequence 1,1,1,3,1,3,3,3,1 and k=2 we don't want to immediately truncate the heading sequence
                if (intervalsInPast.getFirst() < (nonBookCount - k)) {
                    nonBookCount -= intervalsInPast.removeFirst();
                    bookCount -= intervalsInPast.removeFirst();
                }
            } else {
                setResult(bookCount + nonBookCount);
            }
        }

        private void setResult(int count) {
            result = Math.max(count, result);
        }

        void reOpenSequence(int position) {
            addIntervalAndRecalculate(position - repeatingSequenceStop);
            repeatingSequenceStart = position;
        }

        //called the next item after this book number sequence finished
        //therefore, the ranges are [start, stop) which is very convenient for calculation
        void closeSequence(int position) {
            addIntervalAndRecalculate(position - repeatingSequenceStart);
            repeatingSequenceStop = position;
        }

        void theEnd(int position, boolean finishedWithReferenceBook) {
            if (finishedWithReferenceBook) {
                addIntervalAndRecalculate(position - repeatingSequenceStart);
                addIntervalAndRecalculate(0);
            } else {
                addIntervalAndRecalculate(position - repeatingSequenceStop);
            }
        }

    }

    int k;
    int result = 0;

    public int solution(int[] A, int K) {
        k = K;
        HashMap<Integer, BinarySequenceIntervalsCounter> uniqueBooks = new HashMap<>();

        int previousBookNumber = -1;
        for (int position = 0; position < A.length; position++) {
            final int currentBookNumber = A[position];
            if (previousBookNumber != currentBookNumber) {
                if (!uniqueBooks.containsKey(currentBookNumber)) {
                    uniqueBooks.put(currentBookNumber, new BinarySequenceIntervalsCounter(position));
                } else {
                    uniqueBooks.get(currentBookNumber).reOpenSequence(position);
                }
                if (previousBookNumber != -1) {
                    uniqueBooks.get(previousBookNumber).closeSequence(position);
                }
                previousBookNumber = currentBookNumber;
            }
        }

        for (Map.Entry<Integer, BinarySequenceIntervalsCounter> b : uniqueBooks.entrySet()) {
            b.getValue().theEnd(A.length, previousBookNumber == b.getKey());
        }

        return result;
    }
}
