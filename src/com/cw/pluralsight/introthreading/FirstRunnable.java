package com.cooperweisbach.general;

public class FirstRunnable {

    public static void main(String[] args){
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName());
        };

        Thread thread = new Thread(runnable);
        thread.setName("MyThread");
        thread.run();

    }
}
