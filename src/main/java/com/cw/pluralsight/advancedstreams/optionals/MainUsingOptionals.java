package com.cw.pluralsight.advancedstreams.optionals;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainUsingOptionals {
    public static void main(String[] args){
//        List<Double> result = new ArrayList<>();
//        ThreadLocalRandom.current().doubles(10_000).boxed().parallel()
//                .forEach(d -> NewMath.inv(d)
//                              .ifPresent(
//                        inv -> NewMath.sqrt(inv).ifPresent(sqrt -> result.add(sqrt))
//        ));
//        System.out.println("# results: " + result.size());

//    This function is meant to
//
//
        Function<Double, Stream<Double>> flatmapper =
                d -> NewMath.inv(d)
                        .flatMap(inv -> NewMath.sqrt(inv))
                        .map(sqrt -> Stream.of(sqrt))
                        .orElseGet(() -> Stream.empty());
//
//
//
//
        List<Double> correctResult = ThreadLocalRandom
                .current()
                .doubles(10_000)
                .map(d -> d*20 - 10)
                .boxed()
                .parallel()
                .flatMap(flatmapper)
                .collect(Collectors.toList());

        System.out.println("# Correct Result = " + correctResult.size());

    }
}
