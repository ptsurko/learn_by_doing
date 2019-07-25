package com.company;

//import java.util.*;

//import com.company.collections.ArrayList;

import com.company.collections.LinkedList2;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    interface Predicate {
        boolean test(int a1, int a2);
    }

    public static void func(Predicate p) {

    }

    public static void main(String[] args) {
        // write your code here

        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        func((a1, a2) -> a1 == a2);

        int value = list.stream()
                .filter(x -> x % 2 == 0)
                .mapToInt(x -> x)
                .sum();

        Map<String, Integer> map = new HashMap<>();
        map.put("k1", 10);
        map.put("k2", 20);

        map.compute("k1", (k, v) -> v * 10);

        System.out.println(map.get("k1"));

        System.out.println("Sum: " + value);

        double d1 = 0.3;
        double d2 = 0.2;
        System.out.println(d1-d2);

        Stream<Integer> stream = Stream.iterate(0, i -> i + 1);

        List<Integer> list2 = stream
                .limit(10)
                .collect(Collectors.toList());

        System.out.println(list2);

        Stream<UUID> stream1 = Stream.generate(UUID::randomUUID);

        List<UUID> random = stream1.skip(10).limit(2).collect(Collectors.toList());

        File homeDir = new File(System.getProperty("user.home"));
        File f = new File(homeDir, "app.conf");
        if (f.exists() && f.isFile() && f.canRead()) {
            File configDir = new File(homeDir, ".configdir");
            configDir.mkdir();
            f.renameTo(new File(configDir, ".config1"));
        }

        new BufferedReader(new InputStreamReader(System.in));

        List<Integer> l = new ArrayList<>(List.of(1,2,3,4,4,5,6,7,8,8,9,10,11,12,13));

        l.add(14);

        List<String> gfg = new ArrayList<>(List.of("Geeks", "for", "Geeks"));

        LinkedList ll = new LinkedList();
        ll.add(10);
        ll.add(20);
        ll.add(30);

        System.out.println(ll.get(2));

//        LinkedList2 ll2 = new LinkedList2();
//        ll2.push(10);
//        ll2.push(20);
//
//        for (Object obj : ll2) {
//            System.out.println("Object: " + obj);
//        }


        Class<?> clz = Main.class;
        System.out.println("Module: " + clz.getModule());
    }
}
