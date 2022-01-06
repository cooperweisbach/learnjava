package com.weisbach.customCollectors;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CustomCollector implements Collector<List<Movie>, Map<Actor, Long>, Map<Actor, Long>> {

    private Set<Characteristics> characteristics;

    public CustomCollector() {
        this.characteristics.add(Characteristics.IDENTITY_FINISH);
        this.characteristics.add(Characteristics.UNORDERED);
    }

    @Override
    public Supplier<Map<Actor, Long>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<Actor, Long>, List<Movie>> accumulator() {
        return (map, movieList) -> movieList.stream()
                .flatMap(movie -> movie.getActors().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    @Override
    public BinaryOperator<Map<Actor, Long>> combiner() {
        return (map1, map2) -> {map2.forEach((act, num) -> map1.put(act, num)); return map1;};
    }

    @Override
    public Function<Map<Actor, Long>, Map<Actor, Long>> finisher() {
        return null;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return characteristics;
    }
}
