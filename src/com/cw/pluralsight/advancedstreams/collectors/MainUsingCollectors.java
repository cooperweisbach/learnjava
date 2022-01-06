package com.weisbach.collectors;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainUsingCollectors {

    public static void main(String... args) throws IOException {

        Path shakespearePath = Paths.get("./files/words.shakespeare.txt");
        Path ospdPath = Paths.get("./files/ospd.txt");

        try(Stream<String> ospd = Files.lines(ospdPath);
            Stream<String> shakespeare = Files.lines(shakespearePath);) {

            Set<String> scrabbleWords = ospd.collect(Collectors.toSet());
            Set<String> shakespeareWords = shakespeare.collect(Collectors.toSet());

            System.out.println("Scrabble: " + scrabbleWords.size());
            System.out.println("Shakespeare: " + shakespeareWords.size());

            int[] letterScores = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};

            int[] scrabbleENDistribution  =
                    {9, 2, 2, 1, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};

            Function<String, Integer> score = word -> word.toLowerCase()
                    .chars()
                    .map(letter -> letterScores[letter - 'a'])
                    .sum();

            Function<String, Map<Integer,Long>> histoWord = word ->
                    word.chars().boxed().collect(Collectors.groupingBy(letter -> letter, Collectors.counting()));

            Function<String, Long> nBlanks =
                    word -> histoWord.apply(word)
                            .entrySet()
                            .stream()
                            .mapToLong(entry ->
                                    Long.max(
                                            entry.getValue() -
                                            (long) scrabbleENDistribution[entry.getKey() - 'a'],
                                            0L))
                            .sum();

            Function<String,Integer> newScore =
                    word -> histoWord.apply(word)
                            .entrySet()
                            .stream()
                            .mapToInt(entry ->
                                    Integer.min(entry.getValue().intValue(),
                                            scrabbleENDistribution[entry.getKey() - 'a'])
                                            * letterScores[entry.getKey() -'a'])
                            .sum();

            shakespeareWords.stream().parallel()
                    .filter(scrabbleWords::contains)
                    .filter(word -> nBlanks.apply(word) <= 2)
                    .collect(Collectors.groupingBy(
                            newScore
                    )).entrySet()
                    .stream()
                    .sorted(Comparator.comparing(set -> -set.getKey()))
                    .limit(3).forEach(entry -> System.out.println(entry.getKey() + "-" + entry.getValue()));;

        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

}
