package com.weisbach.customCollectors;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainMoviesActors {
    public static void main(String... args) throws IOException{

        Set<Movie> movies = new HashSet<>();
        Stream<String> lines = Files.lines(Paths.get("files","movies-mpaa.txt"));

        lines.forEach(
                (String line) -> {
                    String[] elements = line.split("/");
                    String title = elements[0].substring(0, elements[0].lastIndexOf("(")).trim();
                    String releaseYear = elements[0].substring(elements[0].lastIndexOf("(")+1, elements[0].lastIndexOf(")"));
                    if(releaseYear.contains(",")) {
                        return;
                    }
                    Movie movie = new Movie(title, Integer.valueOf(releaseYear));
                    for( int i = 1; i < elements.length; i++){
                        String[] name = elements[i].split(", ");
                        String lastName = name[0].trim();
                        String firstName = "";
                        if(name.length > 1) {
                            firstName = name[1].trim();
                        }

                        Actor actor = new Actor(lastName, firstName);
                        movie.addActor(actor);
                    }
                    movies.add(movie);
                }
        );
        System.out.println("# of movies = " + movies.size());

        // # of  actors

        long numberOfActors = movies.stream()
                .flatMap(movie -> movie.getActors().stream()) //Stream<Actors>
                .distinct()
                .count();

        System.out.println("# of actors : " + numberOfActors);

        //The number of actors that played in the greatest number of movies

        Map.Entry<Actor, Long> mostViewedActor =
        movies.stream()
                //From Stream<Set<Actors>> to Stream<Stream<Actors>> to Stream<Actors>
                .flatMap(movie -> movie.getActors().stream())
                //From Stream<Actor> to Map<Actor,List<Actor>> to Map<Actor,Long>
                .collect(Collectors.groupingBy(Function.identity()
                                    , Collectors.counting()))
                //Get the set of all entries, Set<Map.Entry<?,?>>
                .entrySet()
                //Turn set into Stream<Map.Entry<?,?>>
                .stream()
                .max(
                    Map.Entry.comparingByValue()
                ).get();
//                .sorted(Comparator.comparing(entry -> -entry.getValue()))
//                .limit(3)
//                .forEach(entry -> System.out.println(entry.getKey() + "-" + entry.getValue()));
        System.out.println("Most Viewed Actor");
        System.out.println(mostViewedActor.getKey() + " - " + mostViewedActor.getValue());

        //Actor that played in the greatest number of movies during a year Map<release years, Map<Actor, # of movies during release year>>
        Supplier<Map<Actor, Long>> supplier = () -> new HashMap<>();
        BiConsumer<Map<Actor, Long>, List<Movie>> accumulator =
                (map, movieList) -> movieList.stream()
                .flatMap(movie -> movie.getActors().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        BinaryOperator<Map<Actor, Long>> combiner = (map1, map2) -> {map2.forEach((act, num) -> map1.put(act, num)); return map1;};

        Collector customCollector = Collector.of(supplier,
                                                accumulator, combiner, Collector.Characteristics.IDENTITY_FINISH);

        Map<Integer, Map.Entry<Actor, Long>> result =
        movies.stream()
                .collect(
                        Collectors.groupingBy(
                                Movie::getReleaseYear,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        (list) ->{
                                            return list.stream()
                                                    .flatMap(movie -> movie.getActors().stream())
                                                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                                    .entrySet().stream()
                                                    .max(Map.Entry.comparingByValue()).get();
                                        })));

        result.entrySet().forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));


        //Given Solution
        //COLLECTOR COMPONENTS
        Supplier<HashMap<Actor, AtomicLong>> supplier1 = () -> new HashMap<Actor, AtomicLong>();
        BiConsumer<HashMap<Actor, AtomicLong>, Movie> accumulator1 = (map, movie) -> {
            movie.getActors().forEach(
                    actor -> map.computeIfAbsent(actor, a -> new AtomicLong()).incrementAndGet()
            );
        };
        BinaryOperator<HashMap<Actor, AtomicLong>> combiner1 =
                (map1, map2) -> {
                    map2.entrySet()
                            .forEach(entry2 -> map1.merge(entry2.getKey(), entry2.getValue(), (al1, al2) -> {
                        al1.addAndGet(al2.get());
                        return al1;
                    }));
                    return map1;
                };

        Map<Integer, Map.Entry<Actor, AtomicLong>> collect =
        movies.stream()
                .collect(
                        Collectors.groupingBy(
                                movie -> movie.getReleaseYear(),//Key Extractor
                                Collector.of(
                                        supplier1,//supplier; Atomic Long allows for incrementing and is commonly used in concurrent programming
                                        accumulator1,//accumulator
                                        combiner1,//combiner
                                        Collector.Characteristics.IDENTITY_FINISH
                                )))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> entry.getValue().entrySet().stream()
                                .max(
                                        Map.Entry.comparingByValue(Comparator.comparing(al -> al.intValue()))
                                ).get()
                ));

        Map.Entry<Integer, Map.Entry<Actor, AtomicLong>> finalResult =
        collect.entrySet().stream().max(
                Map.Entry.comparingByValue(Comparator.comparing(p -> p.getValue().intValue()))).get();

        System.out.println(finalResult);

//
//        collect.entrySet().stream().forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));



    }
}
