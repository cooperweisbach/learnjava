package reductionStep;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

public class Main {

    public static void main(String[] args){

        List<Integer> ints = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        List<Integer> ints1 = Arrays.asList(0, 1, 2, 3, 4);
        List<Integer> ints2 = Arrays.asList(5, 6, 7, 8, 9);

        //Associative
        BinaryOperator<Integer> op1 = (i1, i2) -> i1 + i2;
        BinaryOperator<Integer> op2 = (i1, i2) -> Math.max(i1, i2); //No identity element
        BinaryOperator<Integer> op4 = (i1, i2) -> i1;

        //Non-Associative
        BinaryOperator<Integer> op3 = (i1, i2) -> (i1 + i2)/2;

        //Parallel
        int reduction1 = reduce(ints1, 0, op3);
        int reduction2 = reduce(ints2, 0, op3);
        //Series
//        int reduction = reduce(Arrays.asList(reduction1, reduction2), 0, op3);
        int reduction = reduce(ints, 0, op3);

        System.out.println("Reduction: " + reduction);
    }

    private static int reduce(List<Integer> ints,
                              int identityElement,
                              BinaryOperator<Integer> op) {
        int result = identityElement;
        for(Integer i : ints){
            result = op.apply(i, result);
        }
        return result;
    }
}
