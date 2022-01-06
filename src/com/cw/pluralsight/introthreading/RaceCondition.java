package com.cooperweisbach.general;

public class RaceCondition {
    public static void main(String[] args) throws InterruptedException {
        LongWrapper longWrapper = new LongWrapper(0L);

        Runnable r = ()->{
            System.out.println(Thread.currentThread().getName());
            for(int i = 0; i< 1_0; i++){
                longWrapper.incrementValue();
            }
        };

        Thread[] t = new Thread[1_0];
        for (int i = 0; i< t.length; i++){
            t[i] = new Thread(r);
            t[i].start();
            t[i].join();
        }
//        for (int i = 0; i < t.length; i++){
//            t[i].join();
//        }
        System.out.println("Value = " + longWrapper.getValue());
    }
}
