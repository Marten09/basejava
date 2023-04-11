package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamMain {
    private static final int[] VALUES = new int[]{1, 3, 3, 5, 2, 9, 8, 8, 9};

    public static void main(String[] args) {
        System.out.println(minValue(VALUES));
        System.out.println(oddOrEven(List.of(1, 2, 3, 4, 5)));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (left, right) -> left * 10 + right);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().mapToInt(Integer::valueOf).sum() % 2;
        return integers.stream().filter(predicate -> predicate % 2 != sum).collect(Collectors.toList());
    }
}
