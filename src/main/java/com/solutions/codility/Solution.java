package com.solutions.codility;

import java.util.HashMap;
import java.util.Map;
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


        void reOpenSequence(int position) {

            Integer previousContiguousIntervalEnd = myIntervalsInThePast.get(repeatingSequenceStart);
            if (previousContiguousIntervalEnd == null) {
                System.out.println("unexpected condition - no contiguous row found for position " + position);
                return;
            }

            final int gapToReplace = position - previousContiguousIntervalEnd;

            if (remainingBooks < gapToReplace) {
                //finalize previous sequence
                int currentIntervalEndsAt = previousContiguousIntervalEnd + remainingBooks;
                registerExistingRowSize(currentIntervalStartedAt, currentIntervalEndsAt);

                //just for the case if some books were spent on the left from the first sequence, get them back:
                Map.Entry<Integer, Integer> firstSequenceToSkip = myIntervalsInThePast.ceilingEntry(-1);
                remainingBooks += firstSequenceToSkip.getKey() - currentIntervalStartedAt;


                //start new one - shift currentIntervalStartedAt by as little as possible by throwing away chunk by chunk left sequences
                while (remainingBooks < gapToReplace && myIntervalsInThePast.size() > 1) {
                    firstSequenceToSkip = myIntervalsInThePast.ceilingEntry(-1);
                    remainingBooks += firstSequenceToSkip.getKey() - currentIntervalStartedAt;
                    Map.Entry<Integer, Integer> secondSequence = myIntervalsInThePast.ceilingEntry(firstSequenceToSkip.getValue());
                    remainingBooks += secondSequence.getKey() - firstSequenceToSkip.getValue(); //we are not replacing anymore those books inbetween first and second sequences
                    currentIntervalStartedAt = secondSequence.getKey();
                    myIntervalsInThePast.remove(firstSequenceToSkip.getKey());
                }

                if (remainingBooks < gapToReplace) {
                    //restart from scratch, but with a guarantee, that the range to the left is bigger than we will replace
                    currentIntervalStartedAt = position - remainingBooks;
                    myIntervalsInThePast.clear();
                } else {
                    //gapToReplace amount of books will go to cover the gap
                    int booksToCoverLeft = remainingBooks - gapToReplace;
                    //remaining books will be used to replace what's left on left
                    currentIntervalStartedAt -= booksToCoverLeft;
                    //TODO: scenario of 0101010001 K=3 is not covered yet- what if remainingbooks=gapToReplace
                }

                remainingBooks = 0;
            } else {
                remainingBooks -= gapToReplace;
            }
            //maintenance of myIntervalsInThePast
            repeatingSequenceStart = position;
            //maintenance done
        }

        //called the next item after this book number sequence finished
        //therefore, the ranges are [start, stop) which is very convenient for calculation
        void closeSequence(int position) {
            //maintenance of myIntervalsInThePast
            myIntervalsInThePast.put(repeatingSequenceStart, position);
            //maintenance done
            registerExistingRowSize(currentIntervalStartedAt, position);
        }

        //invoked after the end of array
        void theEnd(int position) {
            if (bookNum == inputBooksArray[position - 1]) {
                registerExistingRowSize(currentIntervalStartedAt, position);
            } else {
                if (remainingBooks > 0) {
                    Map.Entry<Integer, Integer> lastKnownInterval = myIntervalsInThePast.floorEntry(position);
                    int lastIntervalEndedAt = lastKnownInterval.getValue();
                    int maxBooksToReplace = Math.min(position - lastIntervalEndedAt, remainingBooks);
                    registerExistingRowSize(currentIntervalStartedAt, lastIntervalEndedAt + maxBooksToReplace);
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

        int previousBookNumber = -1;
        for (int position = 0; position < inputBooksArray.length; position++) {
            final int currentBookNumber = inputBooksArray[position];
            if (previousBookNumber != currentBookNumber) {
                if (!uniqueBooks.containsKey(currentBookNumber)) {
                    uniqueBooks.put(currentBookNumber, new BooksSlidingIntervalWithSubstitutionDone(currentBookNumber, position));
                } else {
                    uniqueBooks.get(currentBookNumber).reOpenSequence(position);
                }
                if (previousBookNumber != -1) {
                    uniqueBooks.get(previousBookNumber).closeSequence(position);
                }
                previousBookNumber = currentBookNumber;
            }
        }

        for (BooksSlidingIntervalWithSubstitutionDone b : uniqueBooks.values()) {
            b.theEnd(inputBooksArray.length);
        }

        return result;
    }
}
