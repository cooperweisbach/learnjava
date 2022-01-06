package com.cw.pluralsight.introstreams.comparator;

import java.util.function.Function;

public class MainComparator {

    public static void main(String[] args){

        Comparator<Person> cmpAge = (p1, p2) -> p1.getAge() - p2.getAge();
        Comparator<Person> cmpFirstName = (p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName());
        Comparator<Person> cmpLastName = (p1, p2) -> p1.getLastName().compareTo(p2.getLastName());

        //Instead of having to write the extraction and comparison, use a function of type person

//        Function<Person, Integer> f1 = p -> p.getAge();
//        Function<Person, String> f2 = p -> p.getFirstName();
//        Function<Person, String> f3 = p -> p.getLastName();
        Function<Person, Integer> f1 = Person::getAge;
        Function<Person, String> f2 = Person::getFirstName;
        Function<Person, String> f3 = Person::getLastName;

//        Comparator<Person> cmpPerson = Comparator.comparing(f1);
//        Comparator<Person> cmpPerson = Comparator.comparing(p -> p.getAge());
        Comparator<Person> cmpPersonAge = Comparator.comparing(Person::getAge);
        Comparator<Person> cmpPersonLastName = Comparator.comparing(Person::getLastName);
        Comparator<Person> cmpPersonFirstName = Comparator.comparing(Person::getFirstName);

        Comparator<Person> cmpPersonChain = Comparator.comparing(Person::getAge)
                                            .thenComparing(Person::getLastName)
                                            .thenComparing(Person::getFirstName);

        int result = cmpPersonChain.compare(new Person("John", "Smit", 12), new Person("John", "Smith", 12));

        System.out.println(result);
    }
}
