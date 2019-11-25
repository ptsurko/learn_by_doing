package java8;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> strs = Arrays.asList("1", "2", "3");
        strs.stream().map(x -> "_" + x).forEach(System.out::println);
        System.out.println("hello from java8");
    }
}
