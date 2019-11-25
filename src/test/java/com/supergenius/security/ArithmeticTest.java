package com.supergenius.security;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : zuoyu
 * @project : management-security
 * @description : 算术测试
 * @date : 2019-11-25 16:07
 **/
@Slf4j
class ArithmeticTest {

    /**
     * 交集
     */
    @Test
    void intersectionTest() {
        Set<Integer> one = new HashSet<>();
        one.add(1);
        one.add(2);
        one.add(3);
        one.add(4);
        one.add(5);
        one.add(6);
        Set<Integer> two = new HashSet<>();
        two.add(3);
        two.add(4);
        two.add(5);
        two.add(6);
        two.add(7);
        two.add(8);
        one.retainAll(two);
        log.info("交集：" + one);
    }

    /**
     * 差集
     */
    @Test
    void differenceTest() {
        Set<Integer> one = new HashSet<>();
        one.add(1);
        one.add(2);
        one.add(3);
        one.add(4);
        one.add(5);
        one.add(6);
        Set<Integer> two = new HashSet<>();
        two.add(3);
        two.add(4);
        two.add(5);
        two.add(6);
        two.add(7);
        two.add(8);
        one.stream().filter(integer -> !two.contains(integer)).collect(Collectors.toSet()).forEach(System.out::println);
//        one.removeAll(two);
        log.info("差集：" + one);
    }
}
