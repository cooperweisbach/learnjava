package map;

import java.util.*;

public class Main {
    public static void main(String[] args){

        Person p1 = new Person("Alice", 23);
        Person p2 = new Person("Jim", 40);
        Person p3 = new Person("Missy", 29);
        Person p4 = new Person("Gene", 19);
        Person p5 = new Person("Beth", 12);
        Person p6 = new Person("Frank", 34);

        City tokyo = new City("Tokyo");
        City newYork = new City("New York");
        City barcelona = new City("Barcelona");


        Map<City, List<Person>> map1 = new HashMap<>();
        map1.computeIfAbsent(tokyo, city -> new ArrayList<>()).add(p1);
        map1.computeIfAbsent(newYork,city -> new ArrayList<>()).add(p2);
        map1.computeIfAbsent(newYork,city -> new ArrayList<>()).add(p3);


        System.out.println("People from Tokyo: " + map1.getOrDefault(tokyo, Collections.EMPTY_LIST));
        System.out.println("People from New York: " + map1.getOrDefault(newYork, Collections.EMPTY_LIST));
        System.out.println("People from Barcelona: " + map1.getOrDefault(barcelona, Collections.EMPTY_LIST));


        Map<City, List<Person>> map2 = new HashMap<>();
        map2.computeIfAbsent(tokyo, city -> new ArrayList<>()).add(p4);
        map2.computeIfAbsent(barcelona, city -> new ArrayList<>()).add(p5);
        map2.computeIfAbsent(barcelona, city -> new ArrayList<>()).add(p6);

        System.out.println("People from Tokyo: " + map2.getOrDefault(tokyo, Collections.EMPTY_LIST));
        System.out.println("People from New York: " + map2.getOrDefault(newYork, Collections.EMPTY_LIST));
        System.out.println("People from Barcelona: " + map2.getOrDefault(barcelona, Collections.EMPTY_LIST));

        map1.forEach((
                (city1, people1) -> map2.merge(city1, people1, (pm2, pm1) -> {
            pm1.addAll(pm2);
            return pm1;
        })));

        System.out.println("People from Tokyo: " + map2.getOrDefault(tokyo, Collections.EMPTY_LIST));
        System.out.println("People from New York: " + map2.getOrDefault(newYork, Collections.EMPTY_LIST));
        System.out.println("People from Barcelona: " + map2.getOrDefault(barcelona, Collections.EMPTY_LIST));


        //Exercise 1
//        List<Person> people = new ArrayList(Arrays.asList(p1, p2, p3, p4, p5, p6));
//
//        people.removeIf(person -> person.getAge() < 20);
//
//        people.replaceAll(person -> new Person(person.getName().toUpperCase(), person.getAge()));
//
//        people.sort(Comparator.comparing(Person::getAge));
//        //Method Reference
//        people.forEach(System.out::println);
//
//        //Lambda Function
//        people.forEach(p -> System.out.println(p));




    }

}
