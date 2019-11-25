package stream;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) {

        int v = Stream.iterate(0, i -> i + 1)
                .limit(100)
                .filter(i -> i % 2 == 0)
                .findFirst()
                .get();

        Map<Boolean, List<Integer>> m = Stream.iterate(0, i -> i + 1)
                .limit(100)
                .collect(Collectors.partitioningBy(i -> i % 2 == 0));

        int sum = 0;
        Stream.iterate(0, i -> i + 1)
                .limit(100)
//                .forEach(i -> sum += i)

//                .filter(i -> i % 2 == 0)
                .findFirst()
                .get();

        sum = Stream.iterate(0, i -> i + 1)
                .parallel()
                .limit(10000)
                .map(i -> (i % 2 == 0) ? i * 2 : i * 3)
                .reduce(0, Integer::sum);

        System.out.println("Parallel sum: " + sum);
    }
}
