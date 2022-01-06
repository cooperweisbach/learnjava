package com.cw.pluralsight.introstreams.predicate;


public class Main {

    public static void main(String[] args){
        Predicate<String> p1 = s -> s.trim().length() < 20 ;
        Predicate<String> p2 = s -> s.trim().length() > 5 ;
        Predicate<String> p3 = p1.or(p2);
//
//        boolean b = p3.test("     Hello    ");
//        System.out.println("Hello is shorter than 20 chars and greater than 5 chars: " + b);



        Predicate<String> p4 = Predicate.isEqualTo("Yes");
        System.out.println(p4.test("YES"));
    }
}
