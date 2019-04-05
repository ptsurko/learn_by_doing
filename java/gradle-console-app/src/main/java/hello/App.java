package hello;

public class App {
    public static String getGreeting() {
        return "Hello";
    }

    public static void main(String[] args) {
        System.out.println(App.getGreeting() + " " + "world!");
    }
}
