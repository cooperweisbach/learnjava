package com.cw.pluralsight.advancedstreams.forkJoinPool;

public class ParallelStreams {
    public static void main(String[] args){
//          First Example - Observe the differences in output when the stream is parallel and sequential.
//          Output for parallel will be randomly ordered in the output
//          Executed in the Common Fork Join pool
//          We can use the peek() method to see the thread that is being used for the computation
//          We can also add the System property below to limit the number of CFJP threads that are utilized

//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism",  "2");
//        Stream.iterate("+",  s -> s + "+")
//                .parallel()
//                .limit(6)
//                .peek(s -> System.out.println(s + " processed in the thread " + Thread.currentThread().getName()))
//                .forEach(System.out::println);



//          Second Example - Non Thread Safe testing with parallel streams and race conditions
//          Running the following block of code without the parallel method will result in a proper
//          output equal to the limit
//          Running the following block of code with the parallel method will result in an array
//          with a size less than the limit or an exception for ArrayIndexOutOfBoundsException
//          because some threads will interrupt other threads to access indexes that don't exist during resizing
//          of the "strings" array. We can use a concurrent aware list like CopyOnWriteArrayList
//          but this is not recommended as it is poor practice and bad for performance. Lastly, to avoid the
//          concerns of thread safety entirely, just use the collections framework

////        List<String> strings = new CopyOnWriteArrayList<>();
////        List<String> strings = new ArrayList<>();
//        List<String> strings =
//        Stream.iterate("+", s -> s + "+")
//                .parallel()
//                .limit(1000)
////                .forEach(strings::add);
//                .collect(Collectors.toList());
//        System.out.println("# " + strings.size());

    }
}
