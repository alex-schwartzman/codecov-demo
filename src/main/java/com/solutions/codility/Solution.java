package com.solutions.codility;

import java.util.HashMap;
import java.util.TreeMap;

//https://app.codility.com/programmers/task/replacing_books/
//Given a list of integers, return the maximum number of consecutive integers equal to each other after replacing at most K of them.
public class Solution {

    static class BooksSlidingIntervalWithSubstitutionDone {
        //intervals are [start, stop) for convenience (right-exclusive)
        TreeMap<Integer, Integer> myIntervalsInThePast = new TreeMap<>();

        int remainingBooks = k;
        final int bookNum;
        int currentIntervalStartedAt; //current start of the whole interval
        int repeatingSequenceStart;

        //constructor is called when this booknumber is first encountered in the row.
        BooksSlidingIntervalWithSubstitutionDone(int bookNum, int position) {
            this.bookNum = bookNum;
            int booksBeforeToReplace = position;
            int maxBooksWeMayReplace = Math.min(booksBeforeToReplace, k);
            remainingBooks = k - maxBooksWeMayReplace;
            currentIntervalStartedAt = position - maxBooksWeMayReplace;
            registerExistingRowSize(currentIntervalStartedAt, position + 1);
            repeatingSequenceStart = position;
        }

        //calculating [start, stop) interval size
        private int registerExistingRowSize(int start, int stop) {
            int size = stop - start;
            result = Math.max(result, size);
            return size;
        }

        void step(int position) {
            int inBook = inputBooksArray[position];
            int inPreviousBook = position == 0 ? -1 : inputBooksArray[position - 1];

            //first things first - maintain map of contiguous areas
            if (inBook != inPreviousBook) {
                if (inBook == bookNum) {
                    repeatingSequenceStart = position;
                } else {
                    if (inPreviousBook == bookNum) {
                        myIntervalsInThePast.put(repeatingSequenceStart, position);
                    }
                }
            }
            //maintenance done - now there will be guarantee that in the repeating sequence which just finished, will be availble for calculations (k=0 sensitive case)

            //then figure out if we slide the window or shrink it
            if (inBook == bookNum) {
                registerExistingRowSize(currentIntervalStartedAt, position + 1);
            } else {
                if (remainingBooks > 0) {
                    remainingBooks--;
                    registerExistingRowSize(currentIntervalStartedAt, position + 1);
                } else {
                    if (inputBooksArray[currentIntervalStartedAt] != bookNum) {
                        currentIntervalStartedAt++;
                    } else {
                        //there is a guarantee that the interval will be found, as we already created entry
                        Integer previousContiguousIntervalEnd = myIntervalsInThePast.get(currentIntervalStartedAt);
                        if (previousContiguousIntervalEnd == null) {
                            System.out.println("unexpected condition - no contiguous row found for position " + String.valueOf(position));
                        }
                        currentIntervalStartedAt = previousContiguousIntervalEnd + 1;
                    }
                }
            }
        }

    }

    static int[] inputBooksArray;
    static int k;
    static int result = 0;

    public int solution(int[] A, int K) {
        inputBooksArray = A;
        k = K;

        HashMap<Integer, BooksSlidingIntervalWithSubstitutionDone> uniqueBooks = new HashMap<>();

        for (int position = 0; position < inputBooksArray.length; position++) {
            for (BooksSlidingIntervalWithSubstitutionDone book : uniqueBooks.values()) {
                book.step(position);
            }
            int bookNumber = inputBooksArray[position];
            if (!uniqueBooks.containsKey(bookNumber)) {
                uniqueBooks.put(bookNumber, new BooksSlidingIntervalWithSubstitutionDone(bookNumber, position));
            }
        }

        return result;
    }
}
