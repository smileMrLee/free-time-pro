package com.evc.free.controller.guava;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author rongli
 * @since 2019-05-22 10:34
 */
public class CommonTest {
    public static void main(String[] args) {
        getEmpty();
        hho();
    }

    public static void getEmpty() {
        Optional<Integer> possible = Optional.of(10);

        System.out.println(possible.isPresent()); // returns true

        System.out.println(possible.get()); // returns 5
    }

    public static void hho(){
        List<String> list = Lists.newArrayList("this is text one 1!","this is text two 22!", "this is text three 333!");
        String result = list.stream().filter(s -> s.length()>10).collect(Collectors.joining());
        System.out.println(result);

        String result2 = list.stream().filter(s -> s.length()>10).collect(Collectors.joining(":"));
        System.out.println(result2);

        String result3 = list.stream().filter(s -> s.length()>10).collect(Collectors.joining(":", "START", "END"));
        System.out.println(result3);

        Optional<String> result4 = list.stream().filter(s -> s.length() > 10).min(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println(result4);

        Set<String> set1 = Sets.newHashSet("good", "next", "item");
        Set<String> set2 = Sets.newHashSet("goodbak", "next", "item");

        Sets.SetView<String> set1Difs = Sets.difference(set1, set2);
        for (String str : set1Difs){
            System.out.println(str);
        }

        Sets.SetView<String> set1Difs2 = Sets.difference(set2, set1);
        for (String str : set1Difs2){
            System.out.println(str);
        }

        Sets.SetView<String> intersectionSet = Sets.intersection(set1, set2);
        for (String str : intersectionSet){
            System.out.println(str);
        }

    }
}
