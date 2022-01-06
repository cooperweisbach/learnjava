public class Anon {

    public static void main(String[] args){

        AnonymousInterface anonInterface = new AnonymousInterface() {
            @Override
            public void doSomething() {
                System.out.println("doing something");
            }

            @Override
            public void doSomethingElse() {
                System.out.println("doing something else");
            }
        };

        AnonymousClass anonClass = new AnonymousClass() {
            @Override
            public void doSomethingElseClassy() {
                System.out.println("*wave fart away*");
            }
        };

        LambdaInterface lambdaAnon = new LambdaInterface() {
            @Override
            public void myMethod() {
                System.out.println("My Method Implemented with Anon Class");
            }
        };

        LambdaInterface lambdaImplemented = () -> System.out.println("My Method Implemented with Lambda Exp");
        lambdaImplemented.myMethod();
        
    }
}
