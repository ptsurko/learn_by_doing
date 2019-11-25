package com.company;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;

public class Main {
    static String isValid(String s) {
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);

            map.putIfAbsent(c, 0);
            map.put(c, map.get(c) + 1);
        }

        Map<Integer, Integer> map2 = new HashMap<>();

        for (Integer num : map.values()) {
            map2.putIfAbsent(num, 0);
            map2.put(num, map2.get(num) + 1);
        }

        List<Integer> keys = Arrays.asList(map2.keySet().toArray());

        return "NO";
    }


    class Test {
        public void Test() {}
    }

    public static void main(String[] args) throws IOException {
        var classes = ClassPath.from(Main.class.getClassLoader()).getAllClasses();
        for (var clazz : classes) {
            System.out.println(clazz.getName());
        }
    }
}
