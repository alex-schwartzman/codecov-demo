package com.solutions.codility;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Solution {
    public int solution(int[] A) {
        AtomicInteger result = new AtomicInteger(1);

        //Fancy stream-only way, potentially buggy, and anti-functional (in functional programming we avoid side effects, whereas here we utilize a side-effect of rewriting result)
        IntStream.of(A).filter(x -> x > 0).sorted().distinct().forEachOrdered(i -> {
            if (i == result.get()) {
                result.set(i + 1);
            }
        });
        return result.get();
    }
}
