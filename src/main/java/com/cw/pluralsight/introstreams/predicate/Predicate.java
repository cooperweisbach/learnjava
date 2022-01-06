package com.cw.pluralsight.introstreams.predicate;

@FunctionalInterface
public interface Predicate<T> {

    boolean test(T t);

    default Predicate<T> and(Predicate<T> p){
        return s -> test(s) && p.test(s);
    }

    default Predicate<T> or(Predicate<T> p){
        return s -> test(s) || p.test(s);
    }

    static <U> Predicate<U> isEqualTo(U u){
        return s -> s.toString().equalsIgnoreCase(u.toString().toLowerCase());
//        return s -> s.toString().toLowerCase().equals(u.toString().toLowerCase());
    }
}
